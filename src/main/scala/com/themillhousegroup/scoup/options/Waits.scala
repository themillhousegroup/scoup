package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

object Waits {
  //
  //  object FifteenSeconds extends TimeUnit(15){
  //
  //  }

  val fifteenSeconds = 15
  lazy val fifteenSecondDuration = Duration(fifteenSeconds, "seconds")
  lazy val fifteenSecondsInMillis: Int = fifteenSeconds * 1000

  val thirtySeconds = 30
  lazy val thirtySecondDuration = Duration(thirtySeconds, "seconds")
  lazy val thirtySecondsInMillis: Int = thirtySeconds * 1000

  val sixtySeconds = 60
  lazy val sixtySecondDuration = Duration(sixtySeconds, "seconds")
  lazy val sixtySecondsInMillis: Int = sixtySeconds * 1000
}

