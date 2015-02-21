package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

case class ScoupOptions(
  userAgent: String = UserAgents.macChromeUserAgentString,
  timeout: Duration = Waits.FifteenSeconds.duration)