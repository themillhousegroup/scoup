package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class DocumentPositioningSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)
  val top = doc.select("#wrapper").head

  val firstList = doc.select("#firstList").head
  val secondList = doc.select("#secondList").head

  val item1 = doc.select("li#l2i1").head
  val item2 = doc.select("li#l2i2").head
  val item3 = doc.select("li#l2i3").head
  val l3item3 = doc.select("li#l3i3").head

  "document positioning (basics)" should {

    "Never consider null as being before self" in {
      secondList.isBefore(null) must beFalse
    }

    "Never consider self as being before self" in {
      secondList.isBefore(secondList) must beFalse
      item3.isBefore(item3) must beFalse
    }
  }

  "document positioning (children)" should {
    "Be able to determine if a node is before another node" in {
      secondList.isBefore(item3) must beTrue
    }
  }

  "document positioning (peers)" should {
    "Be able to determine if a node is before another node" in {
      item2.isBefore(item3) must beTrue
    }

    "Be able to determine if a node is not before another node" in {
      item2.isBefore(item1) must beFalse
    }
  }

  "document positioning (same level but in different trees)" should {
    "Be able to determine if a node is before another node" in {
      item2.isBefore(l3item3) must beTrue
    }

    "Be able to determine if a node is not before another node" in {
      l3item3.isBefore(item1) must beFalse
    }
  }

  "document positioning (one degree different)" should {
    "Be able to determine if a node is before another node" in {

      firstList.isBefore(item3) must beTrue
    }

    "Be able to determine if a node is not before another node" in {
      item2.isBefore(firstList) must beFalse
    }
  }

  "document positioning (two degrees different)" should {
    "Be able to determine if a node is before another node" in {

      top.isBefore(item3) must beTrue
    }

    "Be able to determine if a node is not before another node" in {
      item2.isBefore(top) must beFalse
    }
  }
}
