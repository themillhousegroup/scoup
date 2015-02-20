package com.themillhousegroup.scoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import com.themillhousegroup.scoup.traits.{ UserAgent, DefaultUserAgent, DefaultWait }
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Scoup {

  private def basicJsoup(url: String, ua: UserAgent, timeout: Duration) =
    Jsoup
      .connect(url)
      .userAgent(ua.uaString)
      .timeout(timeout.toMillis.toInt)

  def parse(url: String)(implicit ua: UserAgent, timeout: Duration): Future[Document] = Future {
    basicJsoup(url, ua, timeout).get
  }

  def parsePost(url: String, data: Map[String, String])(implicit ua: UserAgent, timeout: Duration): Future[Document] = Future {
    basicJsoup(url, ua, timeout)
      .data(data.asJava)
      .post
  }

  def parseHTML(html: String)(implicit ua: UserAgent, timeout: Duration): Document = {
    Jsoup.parse(html)
  }
}
