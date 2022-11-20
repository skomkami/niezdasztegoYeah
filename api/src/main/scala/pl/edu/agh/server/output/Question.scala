package pl.edu.agh.server.output

import pl.edu.agh.common.JsonSerializable

case class Question(
    question: String,
    answers: List[String],
    correctAnswerIndex: Integer,
    sourceQuote: String,
    sourceUrl: String
)

case object Question extends JsonSerializable[Question]
