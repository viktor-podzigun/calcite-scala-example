package scalcite.common.it.injector

import org.scalatest.{Args, Status, Suite, SuiteMixin}

/**
 * Inspired by `org.scalatestplus.play.OneServerPerSuite`
 */
trait OneTestInjectorPerSuite extends SuiteMixin with TestInjectorProvider { this: Suite =>

  abstract override def run(testName: Option[String], args: Args): Status = {
    val newConfigMap = args.configMap + (TestInjectorProvider.injectorArg -> injector)
    val newArgs = args.copy(configMap = newConfigMap)
    super.run(testName, newArgs)
  }
}
