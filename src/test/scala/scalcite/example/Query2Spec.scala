package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query2Spec extends BaseIntegrationTest {
  
  it should "run postgres query" in {
    //when
    val result = BaseQuery.execute(db) { statement =>
      val resultSet = statement.executeQuery(
        """
          |select t.month_of_year, t.the_month, sum(f.unit_sales) as sum
          |  from
          |    sales_fact_1998 f,
          |    customer c,
          |    time_by_day t
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

    //then
    result shouldBe
      """January: 2.1000
        |February: 5.7000
        |""".stripMargin
  }

  it should "run calcite query" in {
    //when
    val result = query2()

    //then
    result shouldBe
      """January: 2.1000
        |February: 5.7000
        |""".stripMargin
  }
}
