package scalcite.common.it.injector

import scaldi.Injector

/**
 * Inspired by `org.scalatestplus.play.ServerProvider`
 */
trait TestInjectorProvider {

  implicit def injector: Injector
}

object TestInjectorProvider {

  private[injector] val injectorArg = "scalcite.common.it.injector"
}
