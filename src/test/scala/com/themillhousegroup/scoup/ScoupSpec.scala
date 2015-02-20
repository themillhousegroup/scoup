package com.themillhousegroup.scoup

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.jsoup.Connection
import org.specs2.specification.Scope
import com.themillhousegroup.scoup.traits.{ UserAgents, Waits, UserAgent }
import scala.concurrent.{ Await, Future }
import org.jsoup.nodes.Document

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

  class ScoupImplicitScope extends MockScoupScope with ScoupImplicits {}

  "Scoup User-Agent support" should {

    "Connect with a reasonable User Agent by default" in new ScoupImplicitScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).userAgent(UserAgents.macChromeUserAgent)
    }

    "Accept an overridden User Agent" in new MockScoupScope {
      waitFor(testScoup.parse("foo")(Waits.fifteenSecondDuration, UserAgent("blah")))

      there was one(mockConnection).userAgent("blah")
    }
  }

  "Scoup Timeout support" should {

    "Connect with a reasonable timeout by default" in new ScoupImplicitScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).timeout(Waits.fifteenSecondsInMillis)
    }

    "Accept an overridden User Agent" in new MockScoupScope {
      waitFor(testScoup.parse("foo")(Waits.sixtySecondDuration, UserAgent("blah")))

      there was one(mockConnection).timeout(Waits.sixtySecondsInMillis)
    }
  }

  "Scoup GET and POST support" should {

    "Allow a GET request to be made" in new ScoupImplicitScope {
      waitFor(testScoup.parse("foo"))

      there was one(mockConnection).get
    }

    "Allow a POST request to be made" in new ScoupImplicitScope {
      waitFor(testScoup.parsePost("foo", Map[String, String]()))

      there was one(mockConnection).post
    }

  }
}
