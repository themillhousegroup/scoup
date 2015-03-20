package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class AttributeRegexSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(variousAttributes)

  "AttributeRegex" should {
    "Be able to defined attributes as a Some" in {
      val item = doc.select("h1").head
      item must not beNull

      item.attributeRegex("id".r) must beSome[String]
      item.attributeRegex("id".r).get must beEqualTo("top")
    }

    "Consider a missing attribute as a None" in {
      val item = doc.select("h2#middle").head
      item must not beNull

      item.attributeRegex("class".r) must beNone
    }

    "Consider an empty attribute as a None" in {
      val item = doc.select("h2#bottom").head
      item must not beNull

      item.attributeRegex("class".r) must beNone
    }
  }

  "ElementsAttributeRegex" should {
    "Be able to find the first defined attribute as a Some" in {
      val items = doc.select("h2")
      items must not beNull

      items.attributeRegex("id".r) must beSome[String]
      items.attributeRegex("id".r).get must beEqualTo("middle")
    }

    "Consider a missing attribute as a None" in {
      val items = doc.select("h2")
      items must not beNull

      items.attributeRegex("class".r) must beNone
    }

    "Consider an empty attribute as a None" in {
      val items = doc.select("h2")
      items must not beNull

      items.attributeRegex("class".r) must beNone
    }
  }
}
