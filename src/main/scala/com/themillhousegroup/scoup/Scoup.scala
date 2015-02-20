package com.themillhousegroup.scoup

import org.jsoup.{ Connection, Jsoup }
import org.jsoup.nodes.Document
import com.themillhousegroup.scoup.traits.UserAgent
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Scoup extends Scoup(new RealJsoup()) {}

class Scoup(impl: JSoupProvider) {
  private def basicJsoup(url: String, ua: UserAgent, timeout: Duration): Connection =
    impl
      .connect(url)
      .userAgent(ua.uaString)
      .timeout(timeout.toMillis.toInt)

  def parse(url: String)(implicit timeout: Duration, ua: UserAgent): Future[Document] = Future {
    basicJsoup(url, ua, timeout).get
  }

  def parsePost(url: String, data: Map[String, String])(implicit timeout: Duration, ua: UserAgent): Future[Document] = Future {
    basicJsoup(url, ua, timeout)
      .data(data.asJava)
      .post
  }

  def parseHTML(html: String): Document = {
    impl.parse(html)
  }
}

/** Indirection to allow JSoup's static API to be mocked */
private[scoup] trait JSoupProvider {
  def connect(url: String): Connection
  def parse(html: String): Document
}

private[scoup] class RealJsoup extends JSoupProvider {
  def connect(url: String): Connection = Jsoup.connect(url)
  def parse(html: String): Document = Jsoup.parse(html)
}
