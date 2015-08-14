package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

case class ScoupOptions(
  val userAgent: String = UserAgents.macChromeUserAgentString, // Supply a custom User-Agent string if needed
  val timeout: Duration = Waits.FifteenSeconds.duration // Nominate a different timeout as a scala.concurrent.duration.Duration
  )
