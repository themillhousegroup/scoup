package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class ClosestElementsSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)

  "closestOption (on Elements)" should {
    "Be able to find self" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closestOption("li") must beSome[Element]
      items.closestOption("li").get must beEqualTo(items.head)
    }

    "Be able to find a direct parent" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closestOption("ul") must beSome[Element]
      items.closestOption("ul").get must beEqualTo(items.head.parent)
    }

    "Return a None if no match found" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closestOption("a") must beNone
    }

    "Be able to find an ancestor" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closestOption("div") must beSome[Element]
      items.closestOption("div").get must beEqualTo(items.head.parent.parent)
    }
  }

  "closest (on Elements)" should {
    "Be able to find self" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closest("li") must haveSize(1)

      items.closest("li").head must beEqualTo(items.head)
    }

    "Be able to find multiple parent matches" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      val uls = doc.select("ul")

      items.closest("ul") must haveSize(3)

      items.closest("ul").toSet must beEqualTo(uls.toSet)
    }

    "Return an empty Elements if no match found" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closest("a") must beEmpty
    }

    "Be able to find an ancestor" in {
      val items = doc.select("li#l2i2")
      items must not beNull

      items.closest("div") must not beEmpty

      items.closest("div").head must beEqualTo(doc.select("#wrapper").head)
    }

  }
}
