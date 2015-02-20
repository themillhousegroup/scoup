scoup
============================

Scoup (pronounced _"scoop"_) wraps the [JSoup](http://jsoup.org/) HTML parsing library with implicits for more Scala-idiomatic element operations, as well as additional methods for querying the parsed data.


### Installation

Bring in the library by adding the following to your ```build.sbt```. 

  - The release repository: 

```
   resolvers ++= Seq(
     "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
   )
```
  - The dependency itself: 

```
   libraryDependencies ++= Seq(
     "com.themillhousegroup" %% "scoup" % "0.1.5"
   )

```

### Usage

Once you have __scoup__ added to your project, you can start using it like this:

#### Fetching a Document
In keeping with modern asynchronous software, network operations are performed in a `Future` to allow other work to be done while we wait:

```
import com.themillhousegroup.scoup.Scoup

// Returns a Future[Document] - map on it to get the doc:

Scoup.parse("http://www.google.com").map { doc =>
   println(s"Got the doc: $doc")  
}
```

#### Working with Elements
Mix in the `ScoupImplicits` trait to get automatic conversion from the Jsoup `Elements` class to a Scala `Iterable[Element]`. 

From there, you can `map`, `filter` etc as you see fit. For example, here we scrape __www.somesite.com__, first pulling out all the `<h3>` elements (into an `Iterable[Element]`) before filtering out only those Elements whose `text` contains the word "foo", mapping it to an `Iterable[String]`:

```
import com.themillhousegroup.scoup.Scoup
import com.themillhousegroup.scoup.ScoupImplicits

class MyThing extends ScoupImplicits {

  Scoup.parse("http://www.somesite.com").map { doc =>
    val allHeadings = doc.select(".main h3")
    val fooHeadings = allHeadings.filter(_.ownText.contains("foo")).map(_.ownText)
  }
}
```


### Still To-Do
Lots more powerful functions like `closest()` and filtering using regexes.

### Credits
- The awesome [JSoup](http://jsoup.org/) project.
- [Filippo De Luca](https://plus.google.com/+FilippoDeLuca) wrote about [pimping JSoup](http://filippodeluca.com/programming/2012/11/02/Pimping-Jsoup/#.VORgwVOUc8Y), which became [SSoup](https://github.com/filosganga/ssoup) 
