import edu.holycross.shot.cite._
import edu.holycross.shot.dse._
import edu.holycross.shot.ohco2._
import org.homermultitext.hmtcexbuilder._
import org.homermultitext.edmodel._


import java.io.PrintWriter

// Header file with collection info
// we need for DSE work.
val libDir = "header"
// Directory with DSE data:
val dseDir = "dse"
// URL for ICT tool:
val ict = "http://www.homermultitext.org/ict2/"


val libHeader = DataCollector.compositeFiles(libDir, "cex")
val dseCex = DataCollector.compositeFiles(dseDir, "cex")
val records = dseCex.split("\n").filterNot(_.contains("passage#")).toVector

// This value must agree with header data in header/1.dse-prolog.cex.
val baseUrn = "urn:cite2:validate:tempDse.temp:"
val dseRecords = for ((record, count) <- records.zipWithIndex) yield {
  s"${baseUrn}validate_${count}#Temporary DSE record ${count}#${record}"
}

val srcAll = libHeader + dseRecords.mkString("\n")
val dse = DseVector(srcAll)

def plural[T](v: Set[T]): String = {
  if (v.size == 1) { "s" } else {""}
}

def mdForPage(u: Cite2Urn, dse: DseVector, c: Corpus): String = {
  val md = StringBuilder.newBuilder
  val errors = StringBuilder.newBuilder
  md.append(s"# DSE report for `${u}`\n\n")
  md.append("## Validation of DSE relations\n\n")
  md.append("Validation for **consistency** of references:\n\n")

  val  tbsTxts = dse.textsForTbs(u)

  val imgs = dse.imagesForTbs(u)
  if (imgs.size != 1) {
    errors.append(s"- Error in indexing:  ${imgs.size} image${plural(imgs)} indexed to page ${u}\n")
  } else {
    md.append(s"-  `${u.objectComponent}` is indexed to reference image `${imgs.head}`\n")
    val imgTxts = dse.textsForImage(imgs.head)
    if (imgTxts.size == tbsTxts.size) {
        md.append(s"- **${imgTxts.size}** text passages are indexed to ${imgs.head.objectComponent}\n")
        md.append(s"-  **${tbsTxts.size}** text passages are indexed to ${u.objectComponent}\n")
    } else {
      errors.append(s"- Error in indexing: ${imgTxts.size} text passages indexed top image ${imgs.head.objectComponent}, but ${tbsTxts.size} passages indexed to page ${u.objectComponent}\n")
    }

    if (errors.nonEmpty) {
      md.append("\n##Errors\n\n" + errors.toString + "\n\n")
    } else { md.append("\nResults are consistent: no errors found.\n\n")}
    md
  }
  md.toString
}

def passageView(dse: DseVector, corpus: Corpus, surface: Cite2Urn) : String = {
  val indexedPassages = dse.textsForTbs(surface).toVector
  val viewMd = StringBuilder.newBuilder
  val rows = for (psg <- indexedPassages) yield {
    val matches = corpus ~~ psg
    matches.size match {
      case 0 => "**Error**:  no text in corpus matching " + psg
""      case 1 => {
        val textReading = TeiReader.fromString(matches.nodes(0).cex("#")).map(_.analysis.readWithDiplomatic).mkString(" ")
        val binaryImgBase = "http://www.homermultitext.org/iipsrv?OBJ=IIP,1.0&FIF=/project/homer/pyramidal/deepzoom/"

        val imgWRoI = dse.imagesWRoiForText(psg).head

        val imgPath = List(imgWRoI.namespace, imgWRoI.collection, imgWRoI.version, imgWRoI.dropExtensions.objectComponent).mkString("/") + ".tif&RGN=" + imgWRoI.objectExtension + "&WID=5000&CVT=JPEG"

        val imgs = "![image]("  + binaryImgBase + imgPath  + ")"
        textReading + " " + imgs
      }
      case _ => "**Error**: multiple matches in corpus for " + psg
    }
  }

  //"\n\n| Text | Image    |\n" +   "| :------------- | :------------- |\n" +
  rows.mkString("\n\n")
}

def dseCoherenceReport(dse: DseVector, corpus: Corpus, surface: Cite2Urn) : String = {
  val bldr = StringBuilder.newBuilder

  val imgs = dse.imagesForTbs(surface)
  if (imgs.size != 1) {
    bldr.append(s"Could not analyze page ${surface}:  matched ${imgs.size} images (" + imgs.mkString(" ") + ")")
  } else {
    bldr.append("## Coherence of DSE relations to text editions\n\n")
    val dsePassages = dse.textsForImage(imgs.head)
    println("Checking on " + dsePassages.size + " passages in DSE records...")
    val accountedFor = for (psg <- dsePassages) yield {
      print("\t" + psg + " ... ")
      val matches = corpus ~~ psg
      val inCorpus = (matches.size > 0)
      println(inCorpus)
      (psg, inCorpus)
    }
    val errors = accountedFor.filterNot(_._2).map{ case (urn, success) => "-  " + urn.toString}
    if (errors.nonEmpty) {
      bldr.append("The following passages in DSE records do not appear in the text corpus:\n\n" + errors.mkString("\n") + "\n\n")
    } else {
      bldr.append("All passages in DSE records appear in the text corpus.")
    }
  }
  bldr.toString
}
/** Writes a markdown file with a link to ICT2
* view of a requested page.  The output file is named
* "dse-COLLECTION-OBJEct.md".
*
* @param pageUrn URN of page
*/
def pageView(pg: Cite2Urn, dse: DseVector, c: Corpus) : Unit= {
  val bldr = StringBuilder.newBuilder
  bldr.append(mdForPage(pg, dse, c))
  bldr.append(dseCoherenceReport(dse,c, pg))


  bldr.append("\n\n## Human verification\n\n###  Completeness\n\n")
  bldr.append(s"To check for **completeness** of coverage, please review these visualizations of DSE relations in ICT2:\n\n")


  bldr.append("- [**all** DSE relations of page ${pg.objectComponent} ](${dse.ictForSurface(pg)}).\n\n")

  bldr.append("Visualizations for individual documents:\n\n")
  val texts =  dse.textsForTbs(pg).map(_.dropPassage).toVector
  val listItems = for (txt <- texts) yield {
    println("Create view for " + txt + " ...")
    val oneDocDse = DseVector(dse.passages.filter(_.passage ~~ txt))
    "-  all [passages in " + txt + "](" + oneDocDse.ictForSurface(pg) + ")."
  }
  bldr.append(listItems.mkString("\n"))
  bldr.append("\n\n### Correctness\n\n")
  bldr.append("To check for **correctness** of indexing, please verify that text transcriptions and images agree:\n\n")

  bldr.append(passageView(dse, c, pg))

  new PrintWriter("validation/dse-" + pg.collection + "-" + pg.objectComponent + ".md"){ write (bldr.toString); close}
  println("Markdown report is in validation directory: dse-" + pg.collection + "-" + pg.objectComponent + ".md")
}

def validate(pageUrn: String, corpus: Corpus) : Unit = {
  val u = Cite2Urn(pageUrn)
  println("Validating page " + u + "...")
  pageView(u, dse, corpus)
}

println("\n\nValidate DSE relations for a given page:")
println("\n\tvalidate(PAGEURN, CORPUS)\n\n")




/*
val dseAll = {
  for (c <- codexUrns) yield {
    val hdr = s"## DSE relations for manuscript ${c}\n\n"
    val pgSeq = codexRepo.data ~~ c

    val linkList = for (pg <- pgSeq.data) yield {
      val dseLinks = dse.ictForSurface(pg.urn, ict)
      if (dseLinks == ict) {
        "NEED IMAGE FOR " + pg.urn
      } else {
        dseLinks
      }
    }
    hdr + linkList.mkString("\n")
  }
}*/
