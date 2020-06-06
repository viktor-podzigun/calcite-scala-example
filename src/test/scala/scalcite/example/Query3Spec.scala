package scalcite.example

import org.scalatest._

@DoNotDiscover
class Query3Spec extends BaseIntegrationTest {

  it should "run example query 3" in {
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
