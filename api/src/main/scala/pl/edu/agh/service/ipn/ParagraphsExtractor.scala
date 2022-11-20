package pl.edu.agh.service.ipn

object ParagraphsExtractor {

  def extractParagraphFromSource(htmlSource: String): List[String] = {
    val regex = "<p.*>(.*?)<\\/p>".r
    lazy val sanitizeRegex = "<\\/?[^>]*>"
    val paragraphs = for {
      patternMatch <- regex.findAllMatchIn(htmlSource)
      paragraph = patternMatch.group(1)
      if paragraph.length > 20
      sanitizedParagraph = paragraph.replaceAll(sanitizeRegex, "")
    } yield sanitizedParagraph
    paragraphs.toList
  }
}
