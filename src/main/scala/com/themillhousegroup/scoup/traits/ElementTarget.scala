package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/** A trait that expects to be pimped onto a JSoup Element */
abstract trait ElementTarget {
  val target: Element
}

/** A trait that expects to be pimped onto a JSoup Elements */
abstract trait ElementsTarget {
  val target: Elements
}
