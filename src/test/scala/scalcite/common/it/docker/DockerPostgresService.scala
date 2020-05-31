package scalcite.common.it.docker

import java.sql.DriverManager

import com.whisk.docker._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Suite}
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.util.Try

trait DockerPostgresService extends Suite
  with DockerKit
  with BeforeAndAfterAll
  with ScalaFutures {

  private val logger = LoggerFactory.getLogger(getClass)

  val postgresUser = "postgres"
  val postgresPassword = "mysecretpassword"

  lazy val postgresPort: Int = {
    postgresContainer.getPorts().map(_.apply(defaultPostgresPort)).futureValue
  }

  private val defaultPostgresPort = 5432

  private val postgresContainer = DockerContainer("postgres:9.6.9")
    .withPorts(defaultPostgresPort -> None)
    .withEnv(s"POSTGRES_PASSWORD=$postgresPassword")
    .withReadyChecker(
      new PostgresReadyChecker(postgresUser, postgresPassword, defaultPostgresPort)
        .looped(15, 1.second)
    )

  abstract override def dockerContainers: List[DockerContainer] =
    postgresContainer :: super.dockerContainers

  override def beforeAll(): Unit = {
    super.beforeAll()

    logger.info(s"Postgres container is up, port: $postgresPort")
  }

  def initializeDb(
    sqlResourcePath: String,
    user: String = postgresUser,
    password: String = postgresPassword,
    dbName: String = "postgres"): Unit = {

    Class.forName("org.postgresql.Driver")
    val c = DriverManager.getConnection(s"jdbc:postgresql://localhost:$postgresPort/$dbName", user, password)
    val statement = c.createStatement()

    val sql = Source.fromInputStream(getClass.getResourceAsStream(sqlResourcePath))
      .getLines()
      .mkString("\n")
    statement.execute(sql)
    c.close()
  }
}

class PostgresReadyChecker(user: String,
                           password: String,
                           postgresPort: Int) extends DockerReadyChecker {

  override def apply(container: DockerContainerState)
                    (implicit docker: DockerCommandExecutor, ec: ExecutionContext): Future[Boolean] = {

    container.getPorts().map { ports =>
      Try {
        Class.forName("org.postgresql.Driver")
        val url = s"jdbc:postgresql://${docker.host}:${ports(postgresPort)}/"
        Option(DriverManager.getConnection(url, user, password))
          .map(_.close)
          .isDefined
      }.getOrElse(false)
    }
  }
}
