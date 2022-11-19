package pl.edu.agh.output

import pl.edu.agh.entities.JsonSerializable

case class Question(
    question: String,
    answers: List[String],
    correctAnswerIndex: Integer,
    sourceQuote: String,
    sourceUrl: String
)

case object Question extends JsonSerializable[Question]
