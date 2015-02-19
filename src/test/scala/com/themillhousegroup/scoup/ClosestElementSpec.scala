package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class ClosestElementSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)

  "Closest" should {
    "Be able to find self" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("li") must beSome[Element]
      item.closest("li").get must beEqualTo(item)
    }

    "Be able to find a direct parent" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("ul") must beSome[Element]
      item.closest("ul").get must beEqualTo(item.parents.head)
    }

    "Return a None if no match found" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("a") must beNone
    }

    "Be able to find an ancestor" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("div") must beSome[Element]
      item.closest("div").get must beEqualTo(item.parents.head.parents.head)
    }
  }
}
