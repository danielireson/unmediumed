package unmediumed

class ConfigUnitSpec extends UnitSpec {
  "Config" should "throw an IllegalArgumentException when looking up a key with no value" in {
    // given
    val store: Map[String, Any] = Map()
    val testSubject: ConfigLocal = new Config(store)
    val key: String = "invalid"

    // then
    val error = the[IllegalArgumentException] thrownBy {
      testSubject.get(key)
    }

    error.getMessage shouldBe "Invalid configuration key"
  }
}
