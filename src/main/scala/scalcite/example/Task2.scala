package scalcite.example

import scalcite.example.db.PostgresDatabase

class Task2(postgresDatabase: PostgresDatabase) {
  
  def run(): String = {
    val conn = postgresDatabase.getConnection
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(
      """
        |select t.month_of_year, t.the_month, sum(f.unit_sales) as sum from sales_fact_1998 f, customer c, time_by_day t
        |  where
        |    f.customer_id = c.customer_id and
        |    f.time_id = t.time_id and
        |    c.city = 'Albany'
        |  group by t.month_of_year, t.the_month
        |  order by t.month_of_year;
      """.stripMargin)

    val results = new StringBuilder
    while (resultSet.next()) {
      val date = resultSet.getObject(2)
      val sum = resultSet.getObject(3)
      results ++= s"$date: $sum\n"
    }
    
    results.toString()
  }
}

object Task2 extends MainInjector {

  def main(args: Array[String]): Unit = {
    println(new Task2(postgresDatabase).run())
  }
}
