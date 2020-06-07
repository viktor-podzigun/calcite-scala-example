package scalcite.example.db

import java.sql.{Connection, DriverManager}

case class PostgresDatabase(host: String,
                            port: Int,
                            username: String,
                            password: String,
                            dbName: String) {
  
  def getDriver: String = "org.postgresql.Driver"
  
  def getUrl: String = s"jdbc:postgresql://localhost:$port/$dbName"
  
  def getConnection: Connection = {
    // make sure driver is loaded
    Class.forName(getDriver)
    
    DriverManager.getConnection(getUrl, username, password)
  }
}
