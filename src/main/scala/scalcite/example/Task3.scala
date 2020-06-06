package scalcite.example

import scalcite.example.db.PostgresDatabase

class Task3(postgresDatabase: PostgresDatabase) {
  
  def run(): String = {
    val conn = postgresDatabase.getConnection
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(
      """
        |select c.fullname, sum(f.unit_sales) as sum from sales_fact_1998 f, customer c
        |  where
        |    f.customer_id = c.customer_id and
        |    c.city = 'Albany'
        |  group by c.fullname
        |  order by sum desc
        |  limit 5;
      """.stripMargin)

    val results = new StringBuilder
    while (resultSet.next()) {
      val date = resultSet.getObject(1)
      val sum = resultSet.getObject(2)
      results ++= s"$date: $sum\n"
    }
    
    results.toString()
  }
}

object Task3 extends MainInjector {

  def main(args: Array[String]): Unit = {
    println(new Task3(postgresDatabase).run())
  }
}
