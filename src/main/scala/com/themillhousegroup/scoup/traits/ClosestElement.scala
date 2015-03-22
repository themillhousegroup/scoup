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
    elems.headOption.fold(elems) { _ =>
      val here = elems.select(selector)
      here.headOption.fold(findClosest(selector, elems.parents))(_ => here)
    }
  }

  def findClosestBeforeOption(selector: String, elem: Element): Option[Element] =
    findClosest(selector, new Elements(elem)).find(_.isBefore(elem))

  def findClosestAfterOption(selector: String, elem: Element): Option[Element] =
    findClosest(selector, new Elements(elem)).find(_.isAfter(elem))

}
trait ClosestElement extends ElementTarget {

  /**
   * For each element in the set, get the first element that matches the selector
   * by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @return a Some containing the first one found, or None
   *
   * @see http://api.jquery.com/closest/
   */
  def closestOption(selector: String): Option[Element] =
    ClosestFinder.findClosestOption(selector, target)

  /**
   * For each element in the set, get the first elements that match the selector
   * by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @return an Elements (which may be empty)
   *
   * @see http://api.jquery.com/closest/
   */
  def closest(selector: String): Elements =
    ClosestFinder.findClosest(selector, new Elements(target))

  /**
   * Finds the first element that matches the selector by testing the element
   * itself and then its ancestors, but ensuring that the element is located
   * "before" (i.e. closer to the top of the document) than "this" Element.
   * @param selector
   */
  def closestBeforeOption(selector: String): Option[Element] =
    ClosestFinder.findClosestBeforeOption(selector, target)

  /**
   * Finds the first element that matches the selector by testing the element
   * itself and then its ancestors, but ensuring that the element is located
   * "after" (i.e. closer to the bottom of the document) than "this" Element.
   * @param selector
   */
  def closestAfterOption(selector: String): Option[Element] =
    ClosestFinder.findClosestAfterOption(selector, target)

}

trait ClosestElements extends ElementsTarget {

  /**
   * For each element in the set, get the first element that matches the selector
   * by testing the elements themselves and traversing up through their ancestors in the DOM tree.
   *
   * @return a Some containing the first one found, or None
   *
   * @see http://api.jquery.com/closest/
   */
  def closestOption(selector: String): Option[Element] = {
    ClosestFinder.findClosestOption(selector, target)
  }

  /**
   * For each element in the set, get the first elements that match the selector
   * by testing the elements themselves and traversing up through their ancestors in the DOM tree.
   *
   * @return an Elements (which may be empty)
   *
   * @see http://api.jquery.com/closest/
   */
  def closest(selector: String): Elements = {
    ClosestFinder.findClosest(selector, target)
  }

}
