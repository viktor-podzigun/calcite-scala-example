package scalcite.example

import org.scalatest._
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}
import scalcite.common.it.injector.ConfiguredTestInjector
import scaldi.Injectable.inject

import scala.concurrent.ExecutionContext

trait BaseIntegrationTest extends FlatSpec
  with Matchers
  with ConfiguredTestInjector
  with ScalaFutures
  with Inside
  with Eventually {

  implicit val defaultPatience: PatienceConfig = PatienceConfig(
    timeout = Span(20, Seconds),
    interval = Span(10, Millis)
  )

  implicit lazy val ec: ExecutionContext = ExecutionContext.Implicits.global

  protected lazy val query1: Query1 = inject[Query1]
  protected lazy val query2: Query2 = inject[Query2]
  protected lazy val query3: Query3 = inject[Query3]
  
  //////////////////////////////////////////////////////////////////////////////
  // helpers

}
