// Build file for an HMT editorial repository.
// This file should live in the root directory of your repository.

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")

scalaVersion := "2.12.4"
libraryDependencies ++= Seq(
  "edu.holycross.shot.cite" %% "xcite" % "3.5.0",
  "edu.holycross.shot" %% "ohco2" % "10.9.0",
  "edu.holycross.shot" %% "dse" % "3.2.0",
  "edu.holycross.shot" %% "scm" % "6.1.1",
  "org.homermultitext" %% "hmt-textmodel" % "3.4.3",
  "org.homermultitext" %% "hmtcexbuilder" % "3.2.0",
  "org.homermultitext" %% "hmt-mom" % "3.5.0",
  "edu.holycross.shot" %% "greek" % "1.4.0"
)
