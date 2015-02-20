package com.themillhousegroup.scoup.traits

import scala.concurrent.duration.Duration

trait DefaultWait {
  val waitTimeSeconds = 15

  implicit lazy val waitTime = Duration(waitTimeSeconds, "seconds")
}

