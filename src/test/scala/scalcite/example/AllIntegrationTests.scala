package scalcite.example

import com.typesafe.config.ConfigFactory
import org.scalatest._
import org.slf4j.LoggerFactory
import scalcite.common.it.docker._
import scalcite.common.it.injector._
import scaldi._

class AllIntegrationTests extends Suites(
  new Query1Spec,
  new Query2Spec,
  new Query3Spec
) with OneTestInjectorPerSuite
  with DockerIntegrationTestSuite
  with DockerPostgresService {

  private val logger = LoggerFactory.getLogger(getClass)

  implicit lazy val injector: Injector = {
    new MainModule {} :: TypesafeConfigInjector(configure(
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
