package com.themillhousegroup.scoup.traits

import org.jsoup.select.Elements
import com.themillhousegroup.scoup.ScoupImplicits
import org.jsoup.nodes.Element

object ClosestFinder extends ScoupImplicits {

  def findClosestOption(selector: String, elem: Element): Option[Element] = {
    elem.select(selector).headOption.orElse {
      elem.parents.headOption.flatMap { _ =>
        findClosestOption(selector, elem.parents)
      }
    }
  }

  def findClosestOption(selector: String, elems: Elements): Option[Element] = {
    elems.select(selector).headOption.orElse {
      elems.parents.headOption.flatMap { _ =>
        findClosestOption(selector, elems.parents)
      }
    }
  }

  /** Returns an Elements - i.e. it doesn't just grab the first */
  def findClosest(selector: String, elems: Elements): Elements = {
    if (elems.isEmpty) {
      elems
    } else {
      val here = elems.select(selector)
      if (here.nonEmpty) {
        here
      } else {
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
  def closestOption(selector: String): Option[Element] = {
    ClosestFinder.findClosestOption(selector, target)
  }

  /**
   * Description: For each element in the set, get the first elements that match the selector
   * by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @see http://api.jquery.com/closest/
   */
  def closest(selector: String): Elements = {
    ClosestFinder.findClosest(selector, new Elements(target))
  }

}
