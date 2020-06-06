package scalcite.example

import java.sql.Statement

import scalcite.example.db.PostgresDatabase

class Query1(db: PostgresDatabase) extends BaseQuery(db) {
  
  protected def run(statement: Statement): String = {
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

object Query1 extends Main[Query1]
