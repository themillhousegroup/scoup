package com.themillhousegroup.scoup

import scala.language.postfixOps
import org.specs2.mutable.Specification
import com.themillhousegroup.scoup.test.HtmlFixtures
import org.jsoup.nodes._

class NodeListSpec extends Specification with ScoupImplicits with HtmlFixtures {

  val doc = Scoup.parseHTML(nestedList)
  val fullDoc = Scoup.parseHTML(fullHtml)

  "Node list conversion implicit" should {
    "Be able to use Scala collection functions on JSoup methods that return Java lists of Node" in {
      val items = doc.childNodes
      items must not beNull

      items.find(_.nodeName == "html") must beSome[Node]
    }

    "Be able to use Scala collection functions on JSoup methods that return Java lists of TextNode" in {
      val items = doc.select("#l3i2").first.textNodes

      items must not beNull

      items.find(_.asInstanceOf[TextNode].text == "Two") must beSome[Node]
    }
  }
}
