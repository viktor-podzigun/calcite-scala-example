package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query1Spec extends BaseIntegrationTest {

  it should "run example query 1" in {
    //when
    val result = query1()

    //then
    result shouldBe "sum: 7.8000"
  }
}
