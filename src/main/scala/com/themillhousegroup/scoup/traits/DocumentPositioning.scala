package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.Node
import scala.annotation.tailrec

trait DocumentPositioning extends ElementTarget {

  def isBefore(other: Node): Boolean =
    isBefore(Option(target), Option(other))

  /**
   * Logic:
   * - If other is my peer, compare indices (negative means I'm in front)
   * - If other is my child, then I must be before it
   * - If I am the child of other, then I must be after it
   *
   * - Otherwise, perform "isBefore" on our parents recursively
   *
   * Args are Options to make null-safe operation easier to read
   */
  @tailrec
  private def isBefore(me: Option[Node], other: Option[Node]): Boolean = {

    if (other.isEmpty) {
      false
    } else {
      if (arePeers(me, other)) {
        (comparePeerIndices(me, other) < 0)
      } else {
        if (isAChildOf(me, other)) {
          true
        } else if (isAChildOf(other, me)) {
          false
        } else {
          println(s"REC Comparing ${parentOf(me)} and ${parentOf(other)}")
          isBefore(me, parentOf(other))
        }
      }
    }
  }

  private def isAChildOf(me: Option[Node], other: Option[Node]): Boolean = {
    val r = for {
      m <- me
      o <- other
      c = (m.childNodes != null) && m.childNodes.contains(o)
    } yield c

    r.getOrElse(false)
  }

  private def arePeers(me: Option[Node], other: Option[Node]) = {
    val r = for {
      m <- me
      o <- other
      p = (m.parent == o.parent)
    } yield p
    r.getOrElse(false)
  }

  private def comparePeerIndices(me: Option[Node], other: Option[Node]): Int = {
    val r = for {
      m <- me
      o <- other
      i = m.siblingIndex - o.siblingIndex
    } yield i
    r.getOrElse(0)
  }

  /** Find my parent. If I don't have one, return self */
  private def parentOf(n: Option[Node]): Option[Node] = {
    n.map { node =>
      Option(node.parent).getOrElse(node)
    }
  }
}
