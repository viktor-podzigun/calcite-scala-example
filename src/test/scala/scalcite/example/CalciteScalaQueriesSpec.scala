package scalcite.example

import org.scalatest._

@DoNotDiscover
class CalciteScalaQueriesSpec extends BaseIntegrationTest {

  it should "run example query 1" in {
    //when
    val result = task1.run()

    //then
    result shouldBe "sum: 1.0000"
  }
}
