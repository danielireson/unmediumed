package unmediumed

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import unmediumed.core.TestComponentRegistry

trait UnitSpec extends FlatSpec with Matchers with MockitoSugar with TestComponentRegistry
