package com.themillhousegroup.scoup

import scala.language.postfixOps
import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class AttributeOptionSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(variousAttributes)

  "AttributeOption" should {
    "Be able to defined attributes as a Some" in {
      val item = doc.select("h1").head
      item must not beNull

      item.attribute("id") must beSome[String]
      item.attribute("id").get must beEqualTo("top")
    }

    "Consider a missing attribute as a None" in {
      val item = doc.select("h2#middle").head
      item must not beNull

      item.attribute("class") must beNone
    }

    "Consider an empty attribute as a None" in {
      val item = doc.select("h2#bottom").head
      item must not beNull

      item.attribute("class") must beNone
    }
  }

  "ElementsAttributeOption" should {
    "Be able to find the first defined attribute as a Some" in {
      val items = doc.select("h2")
      items must not beNull

      items.attribute("id") must beSome[String]
      items.attribute("id").get must beEqualTo("middle")
    }

    "Consider a missing attribute as a None" in {
      val items = doc.select("h2")
      items must not beNull

      items.attribute("class") must beNone
    }

    "Consider an empty attribute as a None" in {
      val items = doc.select("h2")
      items must not beNull

      items.attribute("class") must beNone
    }
  }
}
