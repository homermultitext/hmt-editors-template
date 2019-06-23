// Build file for an HMT editorial repository.
// This file should live in the root directory of your repository.

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")

scalaVersion := "2.12.4"
libraryDependencies ++= Seq(
  "edu.holycross.shot.cite" %% "xcite" % "4.0.2",
  "edu.holycross.shot" %% "ohco2" % "10.13.0",
  "edu.holycross.shot" %% "dse" % "4.2.3",
  "edu.holycross.shot" %% "scm" % "6.2.3",
  "org.homermultitext" %% "hmt-textmodel" % "6.0.1",
  "org.homermultitext" %% "hmtcexbuilder" % "3.3.1",
  "org.homermultitext" %% "hmt-mom" % "3.5.3",
  "edu.holycross.shot" %% "greek" % "2.4.0",
  "edu.holycross.shot" %% "kanones" % "1.0.0",

  "edu.holycross.shot" %% "cex" % "6.3.3",
  "edu.holycross.shot" %% "midvalidator" % "6.3.0"
)
