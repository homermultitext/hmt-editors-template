import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

// Settings for building a text repository.
val catalog = "editions/catalog.cex"
val citation = "editions/citation.cex"
val editions = "editions"

val raw = TextRepositorySource.fromFiles(catalog, citation, editions).corpus


// prepare HMT-specific corpus.
// convert Iliad version from va_xml to msA
// for DSE records, merge scholia into top-level scholion URN.

val iliadXml = raw ~~ CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")
val iliadNodes = iliadXml.nodes.map( n => {
  CitableNode(n.urn.dropVersion.addVersion("msA") ,n.text)
})
val iliad = Corpus (iliadNodes)



val scholiaXml = raw ~~ CtsUrn("urn:cts:greekLit:tlg5026:")
val noReff = Corpus(scholiaXml.nodes.filterNot(_.urn.toString.contains(".ref")))
val collapsed = for (i <- 0 until (noReff.size - 1) by 2 ) yield {
  val u = noReff.nodes(i).urn.collapsePassageBy(1)
  val txt = "<div>" + noReff.nodes(i).text + " " + noReff.nodes(i+1).text + "</div>"
  CitableNode(u,txt)
}
val scholia = Corpus(collapsed.toVector.map( n => {
  CitableNode(n.urn.dropVersion.addVersion("hmt") ,n.text)
}))

val corpus = iliad ++ scholia


println("\n\n\nLoaded the following named corpora:\n")
println(s"\tiliad (${iliad.size} citable nodes)")
println(s"\tscholia (${scholia.size} citable nodes)")
println(s"\tcorpus (composite of iliad and scholia: ${corpus.size} citable nodes)")
