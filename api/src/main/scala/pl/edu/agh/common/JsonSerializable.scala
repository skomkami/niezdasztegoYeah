package pl.edu.agh.common

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import shapeless.Lazy

abstract class JsonSerializable[E] {
  implicit def jsonDecoder(implicit d: Lazy[DerivedDecoder[E]]): Decoder[E] =
    deriveDecoder[E]

  implicit def jsonEncoder(implicit
      d: Lazy[DerivedAsObjectEncoder[E]]
  ): Encoder[E] =
    deriveEncoder[E]
}
