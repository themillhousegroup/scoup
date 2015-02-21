package com.themillhousegroup.scoup.options

case class UserAgent(uaString: String)

object UserAgents {
  val macChromeUserAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36"
  val macChromeUserAgent = UserAgent(macChromeUserAgentString)
}
