val iliadPaleography = "collections/paleographyd-iliad.csv"
val iliadReport = "paleography-iliad.md"




import edu.holycross.shot.cite._
import com.github.tototoshi.csv._
import java.io.File
import java.io.PrintWriter





val imgService = "http://www.homermultitext.org/hmt-digital/images?request=GetBinaryImage&w=9000&urn="

val report =  StringBuilder.newBuilder
report.append("# Paleographic verification: *Iliad* text\n\n")
val reader = CSVReader.open(new File(iliadPaleography))
val iliadEntries = reader.allWithHeaders()

for (entry <- iliadEntries) {
  val txt = CtsUrn(entry("TextUrn"))
  val reading = txt.passageNodeSubrefText
  val img = entry("Image")
  report.append(s"${reading} ![${txt}](${imgService}${img})\n\n")
}
new PrintWriter(iliadReport) { write(report.toString); close }
