package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class AttributeOptionSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)

  "closestOption" should {
    "Be able to find self" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestOption("li") must beSome[Element]
      item.closestOption("li").get must beEqualTo(item)
    }

    "Be able to find a direct parent" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestOption("ul") must beSome[Element]
      item.closestOption("ul").get must beEqualTo(item.parents.head)
    }

    "Return a None if no match found" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestOption("a") must beNone
    }

    "Be able to find an ancestor" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestOption("div") must beSome[Element]
      item.closestOption("div").get must beEqualTo(item.parents.head.parents.head)
    }
  }

  "closest" should {
    "Be able to find self" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("li") must haveSize(1)

      item.closest("li").head must beEqualTo(item)
    }

    "Be able to find multiple parent matches" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("ul") must haveSize(3)

      item.closest("ul").head must beEqualTo(item.parents.head)
    }

    "Return an empty Elements if no match found" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("a") must beEmpty
    }

    "Be able to find an ancestor" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closest("div") must not beEmpty

      item.closest("div").head must beEqualTo(item.parents.head.parents.head)
    }

  }
}
