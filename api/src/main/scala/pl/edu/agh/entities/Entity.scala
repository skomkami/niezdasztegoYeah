package pl.edu.agh.entities

import io.circe.{Decoder, Encoder}
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import shapeless.Lazy

trait Entity {
  def id: Option[Int]
}

abstract class JsonSerializable[E] {
  implicit def jsonDecoder(implicit d: Lazy[DerivedDecoder[E]]): Decoder[E] =
    deriveDecoder[E]

  implicit def jsonEncoder(implicit
      d: Lazy[DerivedAsObjectEncoder[E]]
  ): Encoder[E] =
    deriveEncoder[E]
}

trait EntityProperties[E <: Entity] extends JsonSerializable[E]
