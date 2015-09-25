package com.themillhousegroup.scoup

import org.jsoup.select.Elements
import org.jsoup.nodes._
import scala.collection.JavaConverters._
import com.themillhousegroup.scoup.traits._

trait ScoupImplicits {
  implicit def enrichElements(xs: Elements) = new RichElements(xs)
  implicit def enrichElement(el: Element) = new RichElement(el)

  implicit def enrichNodeList[N <: Node](l: java.util.List[N]) = new RichNodeList(l)
}

/**
 * TODO: Additional methods over and above those offered by JSoup, inspired by JQuery.
 * namely closest(), closestBefore() and closestAfter()
 *
 * TODO: Filtering by regex on ownText
 * @param target
 */
class RichElements(val target: Elements)
    extends Iterable[Element]
    with ClosestElements
    with ElementsAttributeOption {

  def iterator: Iterator[Element] = {
    target.asScala.iterator
  }
}

class RichElement(val target: Element)
    extends ClosestElement
    with DocumentPositioning
    with AttributeOption {
}

class RichNodeList[N <: Node](val target: java.util.List[N]) {

  def iterator: Iterator[Node] = {
    target.asScala.iterator
  }
}
