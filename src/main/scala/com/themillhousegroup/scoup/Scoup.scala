package com.themillhousegroup.scoup

import org.jsoup.{ Connection, Jsoup }
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.themillhousegroup.scoup.options.ScoupOptions
import org.jsoup.Connection.{ Response, Method }
import scala.collection.mutable
import java.net.URL

object Scoup extends Scoup(new RealJsoup(), ScoupOptions()) {}

/**
 * Instantiate a Scoup of your own if you want to set custom
 * options to be applied to all of its operations
 */
class Scoup(impl: JSoupProvider = new RealJsoup(), scoupOptions: ScoupOptions = ScoupOptions()) {

  private def basicJsoup(url: String, options: ScoupOptions, withCookies: Map[String, String], method: Method, data: Map[String, String]): Connection = {
    impl
      .connect(url)
      .userAgent(options.userAgent)
      .timeout(options.timeout.toMillis.toInt)
      .cookies(withCookies.asJava)
      .ignoreContentType(options.ignoreContentType)
      .followRedirects(options.followRedirects)
      .ignoreHttpErrors(options.ignoreHttpErrors)
      .data(data.asJava)
      .method(method)
  }

  private def executeAsync(url: String, options: ScoupOptions, withCookies: Map[String, String], method: Method = Method.GET, data: Map[String, String] = Map()): Future[Response] = {
    Future(basicJsoup(url, options, withCookies, method, data).execute)
  }

  /** Perform a GET on the URL, parsing the resulting Document */
  def parse(url: String, options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[Document] = {
    executeAsync(url, options, withCookies).map(_.parse)
  }

  /** Perform a GET on the URL, parsing the resulting Document and any cookies into the returned tuple */
  def parseWithCookies(url: String, options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[(Document, Map[String, String])] = {
    executeAsync(url, options, withCookies).map { resp =>
      (resp.parse, resp.cookies.asScala.toMap)
    }
  }

  /** Perform a POST on the URL, parsing the resulting Document */
  def parsePost(url: String, data: Map[String, String], options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[Document] = {
    executeAsync(url, options, withCookies, Method.POST, data).map(_.parse)
  }

  /** Perform a POST on the URL, parsing the resulting Document and any cookies into the returned tuple */
  def parsePostWithCookies(url: String, data: Map[String, String], options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[(Document, Map[String, String])] = {
    executeAsync(url, options, withCookies, Method.POST, data).map { resp =>
      (resp.parse, resp.cookies.asScala.toMap)
    }
  }

  /** Perform a GET on the URL, and return the response body. I.E. you are just using Scoup/JSoup as a HTTP client :-) */
  def get(url: String, options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[String] = {
    val acceptNonHtmlOptions = options.copy(ignoreContentType = true)
    executeAsync(url, acceptNonHtmlOptions, withCookies).map(_.body)
  }

  /** Perform a POST on the URL, and return the response body. I.E. you are just using Scoup/JSoup as a HTTP client :-) */
  def post(url: String, data: Map[String, String], options: ScoupOptions = scoupOptions, withCookies: Map[String, String] = Map()): Future[String] = {
    val acceptNonHtmlOptions = options.copy(ignoreContentType = true)
    executeAsync(url, acceptNonHtmlOptions, withCookies, Method.POST, data).map(_.body)
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
