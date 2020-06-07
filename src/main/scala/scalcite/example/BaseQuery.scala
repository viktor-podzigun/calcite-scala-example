package scalcite.example

import java.sql.Statement

import scalcite.example.db.PostgresDatabase

abstract class BaseQuery(db: PostgresDatabase) {

  def apply(): String = BaseQuery.execute(db)(run)
  
  protected def run(statement: Statement): String
}

object BaseQuery {

  def execute(db: PostgresDatabase)(q: Statement => String): String = {
    val conn = db.getConnection
    try {
      q(conn.createStatement())
    }
    finally {
      //TODO: use proper resource closing:
      //  https://medium.com/@dkomanov/scala-try-with-resources-735baad0fd7d
      conn.close()
    }
  }
}
