package scalcite.common.it.injector

import org.scalatest.{Args, Status, Suite, SuiteMixin}
import scalcite.common.it.injector.TestInjectorProvider.injectorArg
import scaldi.Injector

/**
 * Inspired by `org.scalatestplus.play.ConfiguredServer`
 */
trait ConfiguredTestInjector extends SuiteMixin with TestInjectorProvider { this: Suite =>

  private var configuredInjector: Injector = _

  /**
   * The "configured" `Injector` instance that was passed into `run` via the `ConfigMap`.
   *
   * @return the configured `Injector`
   */
  implicit final def injector: Injector = synchronized { configuredInjector }

  abstract override def run(testName: Option[String], args: Args): Status = {
    args.configMap.getOptional[Injector](injectorArg) match {
      case Some(c) => synchronized { configuredInjector = c }
      case None => throw new IllegalStateException(
        s"Trait ConfiguredTestInjector needs an Injector value associated with key '$injectorArg'" +
          " in the config map. Did you forget to annotate a nested suite with @DoNotDiscover?")
    }

    super.run(testName, args)
  }
}
