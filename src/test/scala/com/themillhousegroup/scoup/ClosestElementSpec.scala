package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class ClosestElementSpec extends Specification with ScoupImplicits with HtmlFixtures {

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

  "closestBeforeOption" should {
    "Be able to find self" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestBeforeOption("li") must beSome[Element]
      item.closestBeforeOption("li").get must beEqualTo(item)
    }

    "Be able to find a direct parent that is before - test 1" in {
      val item = doc.select("li#l1i2").head
      item must not beNull

      item.closestBeforeOption("ul") must beSome[Element]
      item.closestBeforeOption("ul").get must beEqualTo(doc.select("#firstList").head)
    }

    "Be able to find a direct parent that is before - test 2" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestBeforeOption("ul") must beSome[Element]
      item.closestBeforeOption("ul").get must beEqualTo(doc.select("#secondList").head)
    }

    "Be able to find a direct parent that is before - test 3" in {
      val item = doc.select("li#l3i2").head
      item must not beNull

      item.closestBeforeOption("ul") must beSome[Element]
      item.closestBeforeOption("ul").get must beEqualTo(doc.select("#thirdList").head)
    }

    "Return a None if no match found before the node, but one exists after it" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestBeforeOption("#thirdHeading") must beNone
    }

    "Return a None if no match found" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestBeforeOption("a") must beNone
    }

    "Be able to find an ancestor" in {
      val item = doc.select("li#l2i2").head
      item must not beNull

      item.closestBeforeOption("div") must beSome[Element]
      item.closestBeforeOption("div").get must beEqualTo(item.parents.head.parents.head)
    }
  }
}
