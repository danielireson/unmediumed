package unmediumed.components

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

    // then
    val html = testSubject.build()

    html shouldBe ""
  }

  it should "build a template with parameters" in {
    // given
    val testSubject = new TemplateBuilder("{{content}}")
    val params = Map("content" -> "testing")

    // then
    val html = testSubject.build(params)

    html shouldBe "testing"
  }
}
