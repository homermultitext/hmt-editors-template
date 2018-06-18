import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import org.homermultitext.edmodel._
import java.io.PrintWriter


// Create a text repository.
val catalog = "editions/catalog.cex"
val citation = "editions/citation.cex"
val editions = "editions"

val textRepo = TextRepositorySource.fromFiles(catalog, citation, editions)

val corpus = Corpus(textRepo.corpus.nodes.filterNot((_.urn.toString.contains("ref"))))
val tokens = TeiReader.fromCorpus(textRepo.corpus)

case class StringCount(s: String, count: Int) {
  def cex :  String = {
    s + "#" + count
  }
}

case class StringOccurrence(urn: CtsUrn, s: String)


def profileTokens(tokens: Vector[TokenAnalysis]) {
  val tokenTypes = tokens.map(_.analysis.lexicalCategory).distinct
  println("Token types:")
  for (ttype <- tokenTypes) {
    val typeTokens = tokens.filter(_.analysis.lexicalCategory == ttype)
    println("\t" + ttype + ": " + typeTokens.size + " tokens. " + typeTokens.map(_.analysis.readWithAlternate).distinct.size + " distinct tokens.")
  }
}

def tokenHisto(tokens: Vector[TokenAnalysis]): Vector[StringCount] = {
  val strs = tokens.map(_.readWithAlternate.text)
  val grouped = strs.groupBy(w => w).toVector
  val counted =  grouped.map{ case (k,v) => StringCount(k,v.size) }
  counted.sortBy(_.count).reverse
}

def tokenIndex(tokens: Vector[TokenAnalysis]) : Vector[String] = {
  def stringSeq = tokens.map( t => StringOccurrence(t.analysis.editionUrn, t.readWithAlternate.text))
  def grouped = stringSeq.groupBy ( occ => occ.s).toVector
  val idx = for (grp <- grouped) yield {
    val str = grp._1
    val occurrences = grp._2.map(_.urn)
    val flatList = for (occurrence <- occurrences) yield {
      str + "#" + occurrence.toString
    }
    flatList
  }
  idx.flatten
}

def wordList(tokens: Vector[TokenAnalysis]): Vector[String] = {
  tokens.map(_.analysis.readWithAlternate).distinct
}


def profileCorpus (c: Corpus, subdir: String = "validation") = {
  println("Citable nodes:  " + c.size)
  val tokens = TeiReader.fromCorpus(c)
  profileTokens(tokens)
  val lexTokens = tokens.filter(_.analysis.lexicalCategory == LexicalToken)
  val words = wordList(lexTokens)
  new PrintWriter(subdir + "/wordlist.txt"){ write(words.mkString("\n")); close;}
  val idx = tokenIndex(lexTokens)
  new PrintWriter(subdir + "/wordindex.txt"){ write(idx.mkString("\n")); close;}
  val histoCex = tokenHisto(lexTokens).map(_.cex)
  new PrintWriter(subdir +  "/wordhisto.cex"){write(histoCex.mkString("\n")); close; }


  println("\n\nWrote index of all lexical tokens in file 'wordindex.txt'.")
  println("Wrote list of unique lexical token forms in file 'wordlist.txt'")
  println("Wrote histogram of lexical token forms  in .cex format in file 'wordhisto.cex'")

  val errs = tokens.filter(_.analysis.errors.nonEmpty).map(err => "\n" + err.analysis.editionUrn.toString + s" has ${err.analysis.errors.size} error(s)\n\t" + err.analysis.errors.mkString("\n\t"))
  println("\n\nWrote index of all lexical tokens in file 'wordindex.txt'.")
  println("Wrote list of unique lexical token forms in file 'wordlist.txt'")

  if (errs.nonEmpty) {
    new PrintWriter("errors.txt"){ write(errs.mkString("\n")); close;}
    println("Wrote list of errors in parsing tokens to file 'errors.txt'")
  } else {
    println("No errors in tokenization.")
  }

}
