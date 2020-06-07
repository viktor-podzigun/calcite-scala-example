package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query1Spec extends BaseIntegrationTest {

  it should "run postgres query" in {
    //when
    val result = BaseQuery.execute(db) { statement =>
      val resultSet = statement.executeQuery(
        """
          |select sum(f.unit_sales) as sum
          |  from
          |    sales_fact_1998 f,
          |    customer c
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
    
    //then
    result shouldBe "sum: 7.8000"
  }
  
  it should "run calcite query" in {
    //when
    val result = query1()

    //then
    result shouldBe "sum: 7.8000"
  }
}
