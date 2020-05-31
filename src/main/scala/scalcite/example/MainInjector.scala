package scalcite.example

import scalcite.example.db._
import scaldi.Injectable._
import scaldi._

trait MainInjector {

  private implicit lazy val injector: Injector =
    new DBModule {} :: TypesafeConfigInjector()

  lazy val postgresDatabase: PostgresDatabase = inject[PostgresDatabase]

}
