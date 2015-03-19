package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/** Allow the attributes of an Element to be treated like a Scala Option */
trait AttributeOption {
  val target: Element

  def attribute(name: String): Option[String] = EmptyStringToOption(target.attr(name))
}

trait ElementsAttributeOption {
  val target: Elements

  def attribute(name: String): Option[String] = EmptyStringToOption(target.attr(name))
}

object EmptyStringToOption {
  def apply(ss: String): Option[String] = ss match {
    case "" => None
    case s: String => Some(s)
  }
}
