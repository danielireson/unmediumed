package unmediumed.core

import unmediumed.UnitSpec

class ConfigUnitSpec extends UnitSpec {
  "Config" should "throw an IllegalArgumentException when looking up a key with no value" in {
    // given
    val store: Map[String, Any] = Map()
    val testSubject = new Config(store)
    val key: String = "invalid"

    // when
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.get(key)
    }

    // then
    error.getMessage shouldBe "Invalid configuration key"
  }
}
