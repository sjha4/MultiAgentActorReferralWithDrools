name := """referral"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies += guice

resolvers += "public-jboss" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
libraryDependencies ++= Seq(
"org.drools" % "drools-core" % "7.3.0.Final",
"org.drools" % "drools-compiler" % "7.3.0.Final",
"org.kie" % "kie-api" % "6.0.1.Final",
"org.apache.logging.log4j" % "log4j-api" % "2.4",
"org.apache.logging.log4j" % "log4j-core" % "2.4",

"ch.qos.logback" % "logback-classic" % "1.2.3"


)
