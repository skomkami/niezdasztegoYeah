package pl.edu.agh.output

import pl.edu.agh.entities.JsonSerializable

case class GeneratedQuiz(
    questions: List[Question]
)

case object GeneratedQuiz extends JsonSerializable[GeneratedQuiz]
