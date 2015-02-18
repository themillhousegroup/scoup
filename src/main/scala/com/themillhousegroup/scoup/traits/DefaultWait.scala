package com.themillhousegroup.scoup.traits

import scala.concurrent.duration.Duration

trait DefaultWait {
  val waitTimeSeconds = 15

  lazy val waitTime = Duration(waitTimeSeconds, "seconds")
  lazy val waitTimeMillis: Int = waitTimeSeconds * 1000
}

