package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

abstract trait Target[T] {
  val target: T
}

/** A trait that expects to be pimped onto a JSoup Element */
abstract trait ElementTarget extends Target[Element] {
}

/** A trait that expects to be pimped onto a JSoup Elements */
abstract trait ElementsTarget extends Target[Elements] {
}
