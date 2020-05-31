package scalcite.example

import org.scalatest._
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}
import scalcite.common.it.injector.ConfiguredTestInjector
import scalcite.example.db.PostgresDatabase
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

  protected lazy val dbPostgresInfo: PostgresDatabase = inject[PostgresDatabase]
  protected lazy val task1: Task1 = inject[Task1]
  
  //////////////////////////////////////////////////////////////////////////////
  // helpers

}
