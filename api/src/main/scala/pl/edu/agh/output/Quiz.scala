package pl.edu.agh.output

import pl.edu.agh.entities.JsonSerializable

case class Quiz(
    id: Int,
    name: String,
    description: Option[String],
    level: String //TODO enum
)

case object Quiz extends JsonSerializable[Quiz]
