package com.themillhousegroup.scoup.options

import scala.concurrent.duration.Duration

final case class ScoupOptions(
  val userAgent: String = UserAgents.macChromeUserAgentString, // Supply a custom User-Agent string if needed
  val timeout: Duration = Waits.FifteenSeconds.duration, // Nominate a different timeout as a scala.concurrent.duration.Duration
  val ignoreContentType: Boolean = false, // If true, the content type of the response will be ignored. Otherwise, JSoup expects HTML / XML
  val followRedirects: Boolean = true, // If false, redirects will NOT be followed
  val ignoreHttpErrors: Boolean = false // If false, an exception will be thrown on HTTP errors. Else, the body of the response is populated with the error.
  )
