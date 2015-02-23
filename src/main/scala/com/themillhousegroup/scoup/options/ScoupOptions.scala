package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

case class ScoupOptions(
  userAgent: String = UserAgents.macChromeUserAgentString, // Supply a custom User-Agent string if needed
  timeout: Duration = Waits.FifteenSeconds.duration, // Nominate a different timeout as a scala.concurrent.duration.Duration
  retainAllCookies: Boolean = false // Set true to grab all cookies in responses and feed into subsequent requests
  //retainCookiesNamed: Seq[String] = Seq()                 // Supply a list of cookie names to retain and re-send
  )