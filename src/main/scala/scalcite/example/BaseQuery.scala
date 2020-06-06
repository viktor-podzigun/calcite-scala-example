package scalcite.example

import java.sql.Statement

import scalcite.example.db.PostgresDatabase

abstract class BaseQuery(db: PostgresDatabase) {

  def apply(): String = {
    val conn = db.getConnection
    try {
      run(conn.createStatement())
    }
    finally {
      //TODO: use proper resource closing:
      //  https://medium.com/@dkomanov/scala-try-with-resources-735baad0fd7d
      conn.close()
    }
  }
  
  protected def run(statement: Statement): String
}
