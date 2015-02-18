package com.themillhousegroup.scoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import com.themillhousegroup.scoup.traits.DefaultWait
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Scoup extends DefaultWait {
  val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36"

  private def basicJsoup(url: String) =
    Jsoup
      .connect(url)
      .userAgent(userAgent)
      .timeout(waitTimeMillis)

  def parse(url: String): Future[Document] = Future {
    basicJsoup(url).get
  }

  def parsePost(url: String, data: Map[String, String]): Future[Document] = Future {
    basicJsoup(url)
      .data(data.asJava)
      .post
  }

  def parseHTML(html:String):Document = {
    Jsoup.parse(html)
  }
}
