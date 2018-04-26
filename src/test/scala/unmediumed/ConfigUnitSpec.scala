package unmediumed

class ConfigUnitSpec extends UnitSpec {
  "Config" should "throw an IllegalArgumentException when looking up a key with no value" in {
    // given
    val store: Map[String, Any] = Map()
    val testSubject: ConfigLocal = new Config(store)
    val key: String = "invalid"

    // then
    intercept[IllegalArgumentException] {
      testSubject.get(key)
    }
  }
}
