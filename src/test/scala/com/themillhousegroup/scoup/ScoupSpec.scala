package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.jsoup.Connection
import org.specs2.specification.Scope
import scala.concurrent.{ Await, Future }
import org.jsoup.nodes.Document
import com.themillhousegroup.scoup.options.{ ScoupOptions, Waits, UserAgents }
import org.jsoup.Connection.Response
import scala.collection.JavaConverters._

class ScoupSpec extends Specification with Mockito {

  class MockScoupScope extends Scope {
    val mockResponse = mock[Response]
    mockResponse.cookies returns Map("foo" -> "bar").asJava
    val mockConnection = mock[Connection]
    mockConnection.userAgent(anyString) returns mockConnection
    mockConnection.timeout(anyInt) returns mockConnection
    mockConnection.method(any[Connection.Method]) returns mockConnection
    mockConnection.cookies(any[java.util.Map[String, String]]) returns mockConnection
    mockConnection.ignoreContentType(anyBoolean) returns mockConnection
    mockConnection.followRedirects(anyBoolean) returns mockConnection
    mockConnection.ignoreHttpErrors(anyBoolean) returns mockConnection
    mockConnection.execute returns mockResponse
    mockConnection.data(any[java.util.Map[String, String]]) returns mockConnection
    val mockJsoup = mock[JSoupProvider]
    mockJsoup.connect(anyString) returns mockConnection
    val testScoup = new Scoup(mockJsoup)

    def waitFor[X](f: => Future[X]): X = {
      Await.result(f, Waits.FifteenSeconds.duration)
    }
  }

  "Scoup User-Agent support" should {

    "Connect with a reasonable User Agent by default" in new MockScoupScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).userAgent(UserAgents.macChromeUserAgentString)
    }

    "Accept an overridden User Agent" in new MockScoupScope {
      waitFor(testScoup.parse("foo", ScoupOptions(userAgent = "blah")))

      there was one(mockConnection).userAgent("blah")
    }
  }

  "Scoup Timeout support" should {

    "Connect with a reasonable timeout by default" in new MockScoupScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).timeout(Waits.FifteenSeconds.inMillis)
    }

    "Accept an overridden User Agent" in new MockScoupScope {
      waitFor(testScoup.parse("foo", ScoupOptions(timeout = Waits.SixtySeconds.duration)))

      there was one(mockConnection).timeout(Waits.SixtySeconds.inMillis)
    }
  }

  "Scoup GET and POST support" should {

    "Allow a GET request to be made" in new MockScoupScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).method(Connection.Method.GET)
    }

    "Allow a POST request to be made" in new MockScoupScope {
      waitFor(testScoup.parsePost("foo", Map[String, String]()))

      there was one(mockConnection).method(Connection.Method.POST)
    }

  }

  "Scoup cookie support" should {

    "Allow cookies to be retrieved in a Scala collection" in new MockScoupScope {
      val tuple = waitFor(testScoup.parseWithCookies("foo"))

      val theCookies = tuple._2
      theCookies must not beEmpty

      theCookies("foo") must beEqualTo("bar")
    }

    "Allow cookies to be submitted in any GET request" in new MockScoupScope {
      val testMap = Map("x" -> "y")
      val javaMap = testMap.asJava

      waitFor(testScoup.parse("foo", ScoupOptions(), testMap))

      there was one(mockConnection).method(Connection.Method.GET)

      there was one(mockConnection).cookies(javaMap)
    }

    "Allow cookies to be submitted in any POST request" in new MockScoupScope {
      val testMap = Map("x" -> "y")
      val javaMap = testMap.asJava

      waitFor(testScoup.parsePost("foo", Map(), ScoupOptions(), testMap))

      there was one(mockConnection).method(Connection.Method.POST)

      there was one(mockConnection).cookies(javaMap)
    }

    "Allow cookies to be submitted and retrieved in any POST request" in new MockScoupScope {
      val testMap = Map("x" -> "y")
      val javaMap = testMap.asJava

      val tuple = waitFor(testScoup.parsePostWithCookies("foo", Map(), ScoupOptions(), testMap))

      there was one(mockConnection).method(Connection.Method.POST)

      there was one(mockConnection).cookies(javaMap)

      val theCookies = tuple._2
      theCookies must not beEmpty

      theCookies("foo") must beEqualTo("bar")
    }

  }
}
