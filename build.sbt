name := "scoup"

// If the CI supplies a "build.version" environment variable, inject it as the rev part of the version number:
version := s"${sys.props.getOrElse("build.majorMinor", "0.4")}.${sys.props.getOrElse("build.version", "SNAPSHOT")}"

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.13.1", "2.12.8", "2.11.11")

organization := "com.themillhousegroup"

libraryDependencies ++= Seq(
  "ch.qos.logback"    %   "logback-classic"       % "1.1.3",
  "org.jsoup"         %   "jsoup"                 % "1.8.3",
  "org.mockito"       %   "mockito-core"          % "2.21.0" % Test,
  "org.specs2"        %%  "specs2-core"           % "4.8.3"  % Test,
  "org.specs2"        %%  "specs2-mock"           % "4.8.3"  % Test
)

resolvers ++= Seq(  "oss-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "oss-releases"  at "https://oss.sonatype.org/content/repositories/releases",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/")

//jacoco.settings

// seq(bintraySettings:_*)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

//scalariformSettings

// net.virtualvoid.sbt.graph.Plugin.graphSettings

