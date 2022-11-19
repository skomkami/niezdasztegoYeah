package pl.edu.agh.entities

case class User(id: Option[Int], name: String) extends Entity

case object User extends EntityProperties[User] {
  def apply(id: Int, name: String): User = User(Some(id), name)
}
