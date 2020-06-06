package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query2Spec extends BaseIntegrationTest {
  
  it should "run example query 2" in {
    //when
    val result = query2()

    //then
    result shouldBe
      """January: 2.1000
        |February: 5.7000
        |""".stripMargin
  }
}
