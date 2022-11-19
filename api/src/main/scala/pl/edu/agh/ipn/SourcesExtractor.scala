package pl.edu.agh.ipn

/** Extracts source pages from response returned by ipn.service/search
  */
object SourcesExtractor {
  def extractPages(html: String): List[String] = {
    val result = extractResultElements(html)
    result
  }

  private def extractResultElements(
      html: String
  ): List[String] = {
    val regex = "<div.*class='res-item'[^>]*>\\s*<a href='([^'&]*.html)".r
    val links = for {
      patternMatch <- regex.findAllMatchIn(html)
      link = patternMatch.group(1)
      if link.endsWith(".html")
    } yield link
    links.toList
  }
}
