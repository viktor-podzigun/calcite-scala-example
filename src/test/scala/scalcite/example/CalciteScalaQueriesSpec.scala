package scalcite.example

import org.scalatest._

@DoNotDiscover
class CalciteScalaQueriesSpec extends BaseIntegrationTest {

  it should "run example query 1" in {
    //when
    val result = task1.run()

    //then
    result shouldBe "sum: 7.8000"
  }
  
  it should "run example query 2" in {
    //when
    val result = task2.run()

    //then
    result shouldBe
      """January: 2.1000
        |February: 5.7000
        |""".stripMargin
  }
  
  it should "run example query 3" in {
    //when
    val result = task3.run()

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
