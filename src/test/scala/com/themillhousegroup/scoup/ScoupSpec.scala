package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.jsoup.Connection
import org.specs2.specification.Scope
import scala.concurrent.{ Await, Future }
import org.jsoup.nodes.Document
import com.themillhousegroup.scoup.options.{ ScoupOptions, Waits, UserAgents }

class ScoupSpec extends Specification with Mockito {

  class MockScoupScope extends Scope {
    val mockConnection = mock[Connection]
    mockConnection.userAgent(anyString) returns mockConnection
    mockConnection.timeout(anyInt) returns mockConnection
    mockConnection.data(any[java.util.Map[String, String]]) returns mockConnection
    val mockJsoup = mock[JSoupProvider]
    mockJsoup.connect(anyString) returns mockConnection
    val testScoup = new Scoup(mockJsoup)

    def waitFor(f: => Future[Document]) = {
      Await.result(f, Waits.fifteenSecondDuration)
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

      there was one(mockConnection).timeout(Waits.fifteenSecondsInMillis)
    }

    "Accept an overridden User Agent" in new MockScoupScope {
      waitFor(testScoup.parse("foo", ScoupOptions(timeout = Waits.sixtySecondDuration)))

      there was one(mockConnection).timeout(Waits.sixtySecondsInMillis)
    }
  }

  "Scoup GET and POST support" should {

    "Allow a GET request to be made" in new MockScoupScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).get
    }

    "Allow a POST request to be made" in new MockScoupScope {
      waitFor(testScoup.parsePost("foo", Map[String, String]()))

      there was one(mockConnection).post
    }

  }
}
