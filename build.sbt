name := "scoup"

organization := "com.themillhousegroup"

scalaVersion := "3.0.0"

crossScalaVersions := Seq("3.0.0", "2.13.6", "2.12.8", "2.11.11")

libraryDependencies ++= Seq(
  "ch.qos.logback"    %   "logback-classic"       % "1.1.3",
  "org.jsoup"         %   "jsoup"                 % "1.8.3",
  "org.mockito"       %   "mockito-core"          % "2.21.0" % Test,
  ("org.specs2"        %%  "specs2-core"           % "4.8.3"  % Test).cross(CrossVersion.for3Use2_13),
  ("org.specs2"        %%  "specs2-mock"           % "4.8.3"  % Test).cross(CrossVersion.for3Use2_13)
)

//jacoco.settings

// seq(bintraySettings:_*)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/themillhousegroup/scoup"))

developers := List(
  Developer(id="themillhousegroup", name="John Marshall", email="john@themillhousegroup.com", url=url("http://www.themillhousegroup.com"))
)


//scalariformSettings

// net.virtualvoid.sbt.graph.Plugin.graphSettings

// For all Sonatype accounts created on or after February 2021
sonatypeCredentialHost := "s01.oss.sonatype.org"

// Only for non-SNAPSHOT releases
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

// sonatypeProfileName := "io.d11"

