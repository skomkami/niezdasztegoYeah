package pl.edu.agh.server.output

import pl.edu.agh.common.JsonSerializable

case class GeneratedQuiz(
    questions: List[Question]
)

case object GeneratedQuiz extends JsonSerializable[GeneratedQuiz]
