package scalcite.example.db

import java.sql.{Connection, DriverManager}

class PostgresDatabase(host: String,
                       port: Int,
                       username: String,
                       password: String,
                       dbName: String) {
  
  def getConnection: Connection = {
    // make sure driver is loaded
    Class.forName("org.postgresql.Driver")
    
    DriverManager.getConnection(s"jdbc:postgresql://localhost:$port/$dbName", username, password)
  }
}
