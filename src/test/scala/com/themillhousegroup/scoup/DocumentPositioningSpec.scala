package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes.Element

class DocumentPositioningSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)

  "document positioning" should {
    "Be able to determine if a node is before another node" in {
      val item2 = doc.select("li#l2i2").head
      item2 must not beNull

      val item1 = doc.select("li#l2i1").head
      item1 must not beNull

      val item3 = doc.select("li#l2i3").head
      item3 must not beNull

      item2.isBefore(item1) must beFalse
      item2.isBefore(item3) must beTrue
    }
  }
}
