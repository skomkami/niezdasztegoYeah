package pl.edu.agh.ipn

object ParagraphsExtractor {

//  def extractParagraphFromSource(htmlSource: String): List[String] = {
//    htmlSource //TODO sanitization
//      .split("\\.")
//      .toList
//      .flatMap { maybeSentence =>
//        val words = maybeSentence.split(" ").size
//        if (words > 10) maybeSentence :: Nil else Nil
//      }
//  }
  def extractParagraphFromSource(htmlSource: String): List[String] = {
    val regex = "<p>(.*)</p>".r
    val paragraphs = for {
      patternMatch <- regex.findAllMatchIn(htmlSource)
      paragraph = patternMatch.group(1)
    } yield paragraph
    paragraphs.toList
  }
}
