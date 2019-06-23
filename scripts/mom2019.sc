import edu.holycross.shot.mid.validator._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cex._
import scala.io.Source
import java.io.PrintWriter

import org.homermultitext.edmodel._
import edu.holycross.shot.greek._

val readerMap =   Map(
  "DiplomaticReader" ->   Vector(DiplomaticReader)
)

val orthoMap = Map(
  "LiteraryGreekString" -> LiteraryGreekString
)

def readersForString(readerName: String): Vector[MidMarkupReader] = {
  if (readerMap.keySet.contains(readerName)){
    readerMap(readerName)
  } else {
    throw (new Exception(s"${readerName} is not a recognized MidReader in this project."))
  }
}

def orthoForString(orthoName: String): MidOrthography = {
  if (orthoMap.keySet.contains(orthoName)){
    orthoMap(orthoName)
  } else {
    throw (new Exception(s"${orthoName} is not a recognized Orthography in this project."))
  }
}


def readerMappings(csvSource : String = "editions/readers.csv") = {
  // drop header row:
  val csvRows = Source.fromFile(csvSource).getLines.toVector.tail
  val pairs = for (row <- csvRows) yield {
    val parts = row.split(",").toVector
    ReadersPairing(CtsUrn(parts(0)), readersForString(parts(1)))
  }
  pairs.toVector
}

def orthoMappings(csvSource : String = "editions/orthographies.csv") = {
  val csvRows = Source.fromFile(csvSource).getLines.toVector.tail
  val pairs = for (row <- csvRows) yield {
    val parts = row.split(",").toVector
    OrthoPairing(CtsUrn(parts(0)), orthoForString(parts(1)))
  }
  pairs.toVector
}


val repo = EditorsRepo(".")
val midValidator = Validator(repo, readerMappings(), orthoMappings())
val reporter = ValidationReporter(midValidator)


def twinScholia(e3String: String, msBString: String) = {
  val e3urn = Cite2Urn(e3String)
  val msBurn = Cite2Urn(msBString)

  val e3DseReporter =  DseReporter(e3urn, midValidator.dse, reporter.corpusForPage(e3urn), midValidator.readers)
  val msBDseReporter =  DseReporter(msBurn, midValidator.dse, reporter.corpusForPage(msBurn), midValidator.readers)


  val pairings=  DataCollector.compositeFiles("relations", "cex", 1).split("\n").filter(_.nonEmpty)
  val rows = for (pr <- pairings) yield {
    val urns = pr.split("#").toVector

    val e3Str = if (urns(0).isEmpty) {
      ""
    } else {
      e3DseReporter.passageMarkdown(CtsUrn(urns(0)))
    }
    val msB = if (urns.size == 1) {
      ""
    } else {
      msBDseReporter.passageMarkdown(CtsUrn(urns(1)))
    }
    "| " + e3Str + " | " + msB + " |"
  }
  val hdr = "| Upsilon 1.1 | Venetus B |\n|:-----------|:-----------|\n"
  val md = hdr + rows.mkString("\n")
  new PrintWriter("parallel-scholia.md"){write(md);close;}
}



def validate(uString : String) = {
  reporter.validate(uString)
}


def validatePair(e3String : String, msBString: String) = {
  reporter.validate(e3String)
  reporter.validate(msBString)
  twinScholia(e3String,msBString)
}


println("\n\nValidate editorial work for a given page:")
println("\n\tvalidate(\"PAGEURN\")\n\n")
println("\n\nValidate editorial work for a related pair of pages\nin Upsilon 1.1 and Venetus B:")
println("\n\tvalidatePair(\"UPSILON-URN\", \"VB-URN\")\n\n")
