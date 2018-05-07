package unmediumed.response

import unmediumed.UnitSpec

class TemplateBuilderUnitSpec extends UnitSpec {
  "TemplateBuilder" should "throw an IllegalArgumentException when base template is null" in {
    // given
    val testSubject = new TemplateBuilder(null)

    // then
    val error: Exception = the[IllegalArgumentException] thrownBy {
      testSubject.build()
    }

    error.getMessage shouldBe "Invalid base template"
  }

  it should "build a template with no parameters" in {
    // given
    val testSubject = new TemplateBuilder("")

    // when
    val html = testSubject.build()

    // then
    html shouldBe ""
  }

  it should "build a template with a single parameter" in {
    // given
    val testSubject = new TemplateBuilder("{{content}}")
    val params = Map("content" -> "testing")

    // when
    val html = testSubject.build(params)

    // then
    html shouldBe "testing"
  }

  it should "build a template with multiple parameters" in {
    // given
    val testSubject = new TemplateBuilder("{{one}},{{two}}")
    val params = Map("one" -> "hello", "two" -> "world")

    // when
    val html = testSubject.build(params)

    // then
    html shouldBe "hello,world"
  }
}
