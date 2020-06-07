package scalcite.example.db

import scaldi.Module

trait DBModule extends Module {

  bind[PostgresDatabase] to new PostgresDatabase(
    host = inject[String](identified by "database.host"),
    port = inject[Int](identified by "database.port"),
    username = inject[String](identified by "database.username"),
    password = inject[String](identified by "database.password"),
    dbName = inject[String](identified by "database.db_name")
  )
}
