package com.themillhousegroup.scoup

import org.jsoup.{ Connection, Jsoup }
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.themillhousegroup.scoup.options.ScoupOptions
import org.jsoup.Connection.{ Response, Method }
import scala.collection.mutable

object Scoup extends Scoup(new RealJsoup(), ScoupOptions()) {}

/** Instantiate a Scoup of your own if you want to set custom
  * options to be applied to all of its operations
  */
class Scoup(impl: JSoupProvider = new RealJsoup(), scoupOptions: ScoupOptions = ScoupOptions()) {

  // TODO: remove this mutability...
  val retainedCookies = mutable.Map[String, String]()

  private def extractCookies(opts: ScoupOptions, response: Response) = {
    if (opts.retainAllCookies) {
      response.cookies.asScala.map {
        case (k, v) =>
          retainedCookies.put(k, v)
      }
    }
    response
  }

  private def basicJsoup(url: String, options: ScoupOptions): Connection = {
    impl
      .connect(url)
      .userAgent(options.userAgent)
      .timeout(options.timeout.toMillis.toInt)
      .cookies(retainedCookies.asJava)
  }

  def parse(url: String, options: ScoupOptions = scoupOptions): Future[Document] = {
    Future {
      basicJsoup(url, options)
        .method(Method.GET)
        .execute
    }.map { resp =>
      extractCookies(options, resp).parse
    }
  }

  def parsePost(url: String, data: Map[String, String], options: ScoupOptions = scoupOptions): Future[Document] = {
    Future {
      basicJsoup(url, options)
        .data(data.asJava)
        .method(Method.POST)
        .execute
    }.map { resp =>
      extractCookies(options, resp).parse
    }
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
