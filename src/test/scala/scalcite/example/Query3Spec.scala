package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query3Spec extends BaseIntegrationTest {

  it should "run postgres query" in {
    //when
    val result = BaseQuery.execute(db) { statement =>
      val resultSet = statement.executeQuery(
        """
          |select c.fullname, sum(f.unit_sales) as sum
          |  from
          |    sales_fact_1998 f,
          |    customer c
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

    //then
    result shouldBe
      """Customer 6: 1.8000
        |Customer 5: 1.6000
        |Customer 4: 1.4000
        |Customer 3: 1.2000
        |Customer 2: 1.0000
        |""".stripMargin
  }

  it should "run calcite query" in {
    //when
    val result = query3()

    //then
    result shouldBe
      """Customer 6: 1.8000
        |Customer 5: 1.6000
        |Customer 4: 1.4000
        |Customer 3: 1.2000
        |Customer 2: 1.0000
        |""".stripMargin
  }
}
