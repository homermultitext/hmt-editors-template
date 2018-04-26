
resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")

scalaVersion := "2.12.4"
libraryDependencies ++= Seq(
  "edu.holycross.shot.cite" %% "xcite" % "3.3.0",
  "edu.holycross.shot" %% "ohco2" % "10.6.0",
  "edu.holycross.shot" %% "dse" % "2.2.1",
  "edu.holycross.shot" %% "scm" % "6.0.0",
  "org.homermultitext" %% "hmt-textmodel" % "2.2.1",
  "org.homermultitext" %% "hmtcexbuilder" % "3.0.1"
)
