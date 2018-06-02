package unmediumed.core

import unmediumed.UnitSpec

class ConfigUnitSpec extends UnitSpec {
  "Config" should "throw an IllegalArgumentException when looking up a key with no value" in {
    // given
    val testSubject = new Config("")
    val key: String = "invalid"

    // when
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.get(key)
    }

    // then
    error.getMessage shouldBe "Invalid configuration key"
  }

  it should "load a store with a single parameter" in {
    // given
    val testSubject = new Config("a=1")
    val key: String = "a"

    // when
    val value: String = testSubject.get(key)

    // then
    value shouldBe "1"
  }

  it should "load a store with multiple parameters" in {
    // given
    val testSubject = new Config("a=1,b=2")
    val key: String = "b"

    // when
    val value: String = testSubject.get(key)

    // then
    value shouldBe "2"
  }
}
