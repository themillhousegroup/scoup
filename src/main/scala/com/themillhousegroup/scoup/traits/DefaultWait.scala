package com.themillhousegroup.scoup.traits

import scala.concurrent.duration.Duration

object Waits {
  val fifteenSeconds = 15
  lazy val fifteenSecondDuration = Duration(fifteenSeconds, "seconds")
  lazy val fifteenSecondsInMillis: Int = fifteenSeconds * 1000

  val sixtySeconds = 60
  lazy val sixtySecondDuration = Duration(sixtySeconds, "seconds")
  lazy val sixtySecondsInMillis: Int = sixtySeconds * 1000
}

trait DefaultWait {
  import Waits._
  implicit lazy val waitTime = fifteenSecondDuration
}

