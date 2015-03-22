package com.themillhousegroup.scoup.traits

import org.jsoup.nodes.{ Element, Node }
import scala.annotation.tailrec

trait DocumentPositioning extends ElementTarget {

  /**
   * Logic:
   * - Recurse up my parents, recording the sibling index of each as an integer
   * - Reverse the order of the integer sequence. These are my "co-ordinates"
   * These can be used for before/after document position queries
   */
  lazy val documentCoordinates = coordinatesOf(Seq(), Some(target)).reverse

  @tailrec
  private def coordinatesOf(accum: Seq[Int], maybeElement: Option[Element]): Seq[Int] = {
    if (maybeElement.isEmpty) {
      accum
    } else {
      val elem = maybeElement.get
      coordinatesOf(accum :+ elem.siblingIndex, Option(elem.parent))
    }
  }

  private def compareCoordinates(maybeOther: Option[DocumentPositioning])(f: (Int, Int) => Boolean): Boolean = {
    maybeOther.fold(false) { other =>
      val zip = documentCoordinates.zipAll(other.documentCoordinates, 0, 0)

      val maybeFirstDiff = zip.dropWhile(dc => dc._1 == dc._2).headOption

      // A None here means co-ords are exactly the same
      maybeFirstDiff.fold(false) { diff =>
        f(diff._1, diff._2)
      }
    }
  }

  def isBefore(other: DocumentPositioning): Boolean =
    compareCoordinates(Option(other))((a, b) => a < b)

  def isAfter(other: DocumentPositioning): Boolean =
    compareCoordinates(Option(other))((a, b) => a > b)

}
