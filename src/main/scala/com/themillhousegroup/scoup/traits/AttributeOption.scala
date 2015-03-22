package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Element
import org.jsoup.nodes.Attributes
import org.jsoup.select.Elements
import scala.util.matching.Regex
import scala.collection.JavaConverters._

/** Allow the attributes of an Element to be treated like a Scala Option */
trait AttributeOption extends ElementTarget {

  def attribute(name: String): Option[String] = EmptyStringToOption(target.attr(name))

  def attributeRegex(nameRegex: Regex): Option[String] = AttributeRegexToOption(target.attributes, nameRegex)

}

trait ElementsAttributeOption extends ElementsTarget {

  def attribute(name: String): Option[String] = EmptyStringToOption(target.attr(name))

  def attributeRegex(nameRegex: Regex): Option[String] = {
    val elems = target.listIterator.asScala
    elems.collectFirst(Function.unlift(elem => AttributeRegexToOption(elem.attributes, nameRegex)))
  }
}

object EmptyStringToOption {
  def apply(ss: String): Option[String] = ss match {
    case "" => None
    case s: String => Some(s)
  }
}

object AttributeRegexToOption {
  def apply(attributes: Attributes, nameRegex: Regex): Option[String] = {
    val atts = attributes.asList.asScala
    atts.find(att => nameRegex.findFirstIn(att.getKey).isDefined).flatMap(att => EmptyStringToOption(att.getValue))
  }
}
