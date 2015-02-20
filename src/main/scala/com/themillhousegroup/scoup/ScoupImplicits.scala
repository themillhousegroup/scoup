package com.themillhousegroup.scoup

import org.jsoup.select.Elements
import org.jsoup.nodes.Element
import scala.collection.JavaConverters._
import com.themillhousegroup.scoup.traits.{ DefaultWait, DefaultUserAgent, ClosestElement }

trait ScoupImplicits extends DefaultUserAgent with DefaultWait {
  implicit def enrichElements(xs: Elements) = new RichElements(xs)
  implicit def enrichElement(el: Element) = new RichElement(el)
}

/**
 * TODO: Additional methods over and above those offered by JSoup, inspired by JQuery.
 * namely closest(), closestBefore() and closestAfter()
 *
 * TODO: Filtering by regex on ownText
 * @param target
 */
class RichElements(val target: Elements) extends Iterable[Element] {

  def iterator: Iterator[Element] = {
    target.asScala.iterator
  }
}

class RichElement(val target: Element) extends ClosestElement {

}
