package scalcite.example

import com.typesafe.config.ConfigFactory
import org.scalatest._
import org.slf4j.LoggerFactory
import scalcite.common.it.docker._
import scalcite.common.it.injector._
import scalcite.example.db._
import scaldi._

class AllIntegrationTests extends Suites(
  new CalciteScalaQueriesSpec
) with OneTestInjectorPerSuite
  with DockerIntegrationTestSuite
  with DockerPostgresService {

  private val logger = LoggerFactory.getLogger(getClass)

  implicit lazy val injector: Injector = {
    new DBModule {
      bind[Task1] to new Task1(inject[PostgresDatabase])
      bind[Task2] to new Task2(inject[PostgresDatabase])
      bind[Task3] to new Task3(inject[PostgresDatabase])
    } :: TypesafeConfigInjector(configure(
      ConfigFactory.load(),
      "database.port" -> postgresPort,
      "database.username" -> postgresUser,
      "database.password" -> postgresPassword,
      "database.db_name" -> "test_db"
    ))
  }

  override def beforeAll(): Unit = {
    super.beforeAll()

    initializeDb("/test_db.sql")
    initializeDb("/test_data.sql", dbName = "test_db")
    logger.info("test DB is ready")
  }
}
