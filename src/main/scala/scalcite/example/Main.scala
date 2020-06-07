package scalcite.example

import scaldi.Injectable._
import scaldi._
import scaldi.util.constraints.NotNothing

import scala.reflect.runtime.universe._

abstract class Main[T <: BaseQuery: TypeTag: NotNothing] {

  def main(args: Array[String]): Unit = {
    implicit lazy val injector: Injector =
      new MainModule {} :: TypesafeConfigInjector()

    val query = inject[T]
    println(query())
  }
}
