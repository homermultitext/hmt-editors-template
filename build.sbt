


scalaVersion := "2.12.10"
name := "HMT editing template"

licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html"))
resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",

  "edu.holycross.shot.cite" %% "xcite" % "4.3.0",
  "edu.holycross.shot" %% "scm" % "7.3.2",
  "edu.holycross.shot" %% "ohco2" % "10.20.3",
  "edu.holycross.shot" %% "citeobj" % "7.5.0",
  "edu.holycross.shot" %% "cex" % "6.5.0",
  "edu.holycross.shot" %% "midvalidator" % "13.3.1",
  "edu.holycross.shot" %% "citevalidator" % "1.2.4",

  "org.homermultitext" %% "hmt-textmodel" % "8.1.1",
  "edu.holycross.shot" %% "greek" % "5.5.1",



)
