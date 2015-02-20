package com.themillhousegroup.scoup.traits

case class UserAgent(uaString: String)

trait DefaultUserAgent {
  val macChromeUserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36"

  implicit val userAgent = UserAgent(macChromeUserAgent)

}
