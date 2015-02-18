package com.themillhousegroup.scoup.traits

import org.jsoup.select.Elements
import com.themillhousegroup.scoup.ScoupImplicits
import org.jsoup.nodes.Element

object ClosestFinder extends ScoupImplicits {

  def findClosest(selector: String, elem: Element): Option[Element] = {
    elem.select(selector).headOption.orElse {
      elem.parents.headOption.flatMap { _ =>
        findClosest(selector, elem.parents)
      }
    }
  }

  def findClosest(selector: String, elems: Elements): Option[Element] = {
    elems.select(selector).headOption.orElse {
      elems.parents.headOption.flatMap { _ =>
        findClosest(selector, elems.parents)
      }
    }
  }

}
trait ClosestElement {
  val target: Element

  /**
   * Description: For each element in the set, get the first element that matches the selector
   * by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @see http://api.jquery.com/closest/
   */
  def closest(selector: String): Option[Element] = {
    ClosestFinder.findClosest(selector, target)
  }

}
