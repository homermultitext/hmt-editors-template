
object Validator {

  def dse = {
    println("Run DSE validation.")
  }
}

object Paleography {
val iliadDataFile = "collections/paleographyd-iliad.csv"
val iliadReportFile = "paleography-iliad.md"

val imgService = "http://www.homermultitext.org/hmt-digital/images?request=GetBinaryImage"




import edu.holycross.shot.cite._
import com.github.tototoshi.csv._
import java.io.File
import java.io.PrintWriter





def iliad(imageSize: Int = 100) = {
  val urlBase = s"${imgService}&w=${imageSize}&urn="
  val report =  StringBuilder.newBuilder
  report.append("# Paleographic verification: *Iliad* text\n\n")
  report .append("| Reading     | Image     |\n| :------------- | :------------- |\n")

  val reader = CSVReader.open(new File(iliadDataFile))
  val iliadEntries = reader.allWithHeaders()

  for (entry <- iliadEntries) {
    val img = entry("Image")
    try {
      val txt = CtsUrn(entry("TextUrn"))
      val reading = txt.passageNodeSubrefText

      report.append(s"| ${reading} | ![${txt}](${urlBase}${img}) |\n")
    } catch {
      case t: Throwable => report.append(s"| ${t.getMessage()} | Text reference: " + entry("TextUrn") + " |\n")
    }
  }
  report.append("\n\n")
  new PrintWriter(iliadReportFile) { write(report.toString); close }
  }
}
