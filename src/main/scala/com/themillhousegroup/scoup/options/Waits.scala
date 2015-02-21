package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

object Waits {

  val FifteenSeconds = new Wait(15)
  val ThirtySeconds = new Wait(30)
  val SixtySeconds = new Wait(60)
}

class Wait(val inSeconds: Int) {
  lazy val duration = Duration(inSeconds, "seconds")
  lazy val inMillis: Int = inSeconds * 1000
}

