package unmediumed

class MediumParserUnitSpec extends UnitSpec {
  trait MediumParserFixture {
    val validHtml: String =
      """
        |<!DOCTYPE html>
        |<html>
        |<title>This is the title</title>
        |<meta name="description" content="This is the description">
        |<link rel="canonical" href="http://example.com">
        |<body></body>
        |</html>
      """.stripMargin
  }

  "MediumParser" should "throw an IllegalArgumentException when html is null" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = null

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no doctype tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<!DOCTYPE html>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening html tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<html>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no closing html tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("</html>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening body tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<body>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no closing body tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("</body>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening title tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<title>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no closing title tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("</title>", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no opening description meta tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<meta name=\"description\"", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "throw an IllegalArgumentException when there's no canonical link tag" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser
    val html: String = validHtml.replaceFirst("<link rel=\"canonical\"", "")

    // then
    intercept[IllegalArgumentException] {
      testSubject.parse(html)
    }
  }

  it should "return a MediumPost" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post shouldBe a[MediumPost]
  }

  it should "extract the title" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("title") shouldBe Some("This is the title")
  }

  it should "extract the description" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("description") shouldBe Some("This is the description")
  }

  it should "extract the canonical link" in new MediumParserFixture {
    // given
    val testSubject = new MediumParser

    // when
    val post: MediumPost = testSubject.parse(validHtml)

    // then
    post.meta.get("canonical") shouldBe Some("http://example.com")
  }
}
