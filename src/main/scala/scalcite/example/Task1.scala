package scalcite.example

import scalcite.example.db.PostgresDatabase

class Task1(postgresDatabase: PostgresDatabase){
  
  def run(): String = {
    val conn = postgresDatabase.getConnection
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(
      """
        |select sum(f.unit_sales) as sum from sales_fact_1998 f, customer c
        |  where
        |    f.customer_id = c.customer_id and
        |    c.city = 'Albany'
      """.stripMargin)

    val results = new StringBuilder
    while (resultSet.next()) {
      val sum = resultSet.getObject("sum")
      results ++= s"sum: $sum"
    }
    
    results.toString()
  }
}

object Task1 extends MainInjector {

  def main(args: Array[String]): Unit = {
    println(new Task1(postgresDatabase).run())
  }
}
