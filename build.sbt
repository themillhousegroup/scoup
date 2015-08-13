name := "scoup"

// If the CI supplies a "build.version" environment variable, inject it as the rev part of the version number:
version := s"${sys.props.getOrElse("build.majorMinor", "0.1")}.${sys.props.getOrElse("build.version", "SNAPSHOT")}"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.10.4")

organization := "com.themillhousegroup"

libraryDependencies ++= Seq(
    "ch.qos.logback"              %   "logback-classic"       % "1.1.3",
    "org.jsoup"                   %   "jsoup"                 % "1.8.3",
    "org.mockito"                 %   "mockito-all"           % "1.10.19"     % "test",
    "org.specs2"                  %%  "specs2"                % "2.3.13"      % "test"
)

resolvers ++= Seq(  "oss-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
                    "oss-releases"  at "https://oss.sonatype.org/content/repositories/releases",
                    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/")

jacoco.settings

seq(bintraySettings:_*)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

scalariformSettings

net.virtualvoid.sbt.graph.Plugin.graphSettings

