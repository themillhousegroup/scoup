package com.themillhousegroup.scoup

import scala.language.implicitConversions
import org.jsoup.select.Elements
import org.jsoup.nodes._
import scala.collection.JavaConverters._
import com.themillhousegroup.scoup.traits._

/** Mix in to get enriched Element / Elements */
trait ScoupImplicits {
  implicit def enrichElements(xs: Elements):RichElements = new RichElements(xs)
  implicit def enrichElement(el: Element):RichElement = new RichElement(el)

  implicit def enrichNodeList[N <: Node](l: java.util.List[N]):RichNodeList[N] = new RichNodeList(l)
}

/** Import ScoupImplicits._ into scope to get enriched Element / Elements */
object ScoupImplicits extends ScoupImplicits


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

class RichNodeList[N <: Node](val target: java.util.List[N]) extends Iterable[Node] {

  def iterator: Iterator[Node] = {
    target.asScala.iterator
  }
}
