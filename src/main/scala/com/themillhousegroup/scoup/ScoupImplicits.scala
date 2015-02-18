package com.themillhousegroup.scoup

import org.jsoup.select.Elements
import org.jsoup.nodes.Element
import scala.collection.JavaConverters._

trait ScoupImplicits {
  implicit def enrichElements(xs: Elements) = new RichElements(xs)

  /**
   * TODO: Additional methods over and above those offered by JSoup, inspired by JQuery.
   * namely closest(), closestBefore() and closestAfter()
   *
   * TODO: Filtering by regex on ownText
   * @param target
   */
  class RichElements(target: Elements) extends Iterable[Element] {

    def iterator: Iterator[Element] = {
      target.asScala.iterator
    }
  }
}
