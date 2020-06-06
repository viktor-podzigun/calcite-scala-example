package scalcite.example

import scaldi.Injectable._
import scaldi._
import scaldi.util.constraints.NotNothing

import scala.reflect.runtime.universe._

abstract class Main[T <: BaseQuery: TypeTag: NotNothing] {

  private implicit lazy val injector: Injector =
    new MainModule {} :: TypesafeConfigInjector()

  def main(args: Array[String]): Unit = {
    println(inject[T].apply())
  }
}
