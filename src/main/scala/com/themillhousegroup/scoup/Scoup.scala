package com.themillhousegroup.scoup

import org.jsoup.{ Connection, Jsoup }
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.themillhousegroup.scoup.options.ScoupOptions

object Scoup extends Scoup(new RealJsoup(), ScoupOptions()) {}

class Scoup(impl: JSoupProvider = new RealJsoup(), scoupOptions: ScoupOptions = ScoupOptions()) {
  private def basicJsoup(url: String, options: ScoupOptions): Connection =
    impl
      .connect(url)
      .userAgent(options.userAgent)
      .timeout(options.timeout.toMillis.toInt)

  def parse(url: String, options: ScoupOptions = scoupOptions): Future[Document] = Future {
    basicJsoup(url, options).get
  }

  def parsePost(url: String, data: Map[String, String], options: ScoupOptions = scoupOptions): Future[Document] = Future {
    basicJsoup(url, options)
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
