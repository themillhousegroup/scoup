package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Node

trait DocumentPositioning extends ElementTarget {

  def isBefore(other: Node): Boolean = {
    true
  }
}
