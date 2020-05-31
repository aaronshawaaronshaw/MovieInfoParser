import io.circe.generic.auto._
import io.circe.parser._

object MovieInfo {

  case class Movie(id: Int = -1, name: String = "", releaseDate: String = "")
  case class Actor(id: Int = -1, name: String = "", birthday: String = "")
  case class ActorToMovie(actorId: Int = -1, movieId: Int = -1)

  case class MovieData(actors: Seq[Actor] = Seq.empty[Actor],
                       movies: Seq[Movie] = Seq.empty[Movie],
                       actorMovies: Seq[ActorToMovie] = Seq.empty[ActorToMovie])

  private def parseRawJson(pathToJson: String): MovieData = {
    try {
      val file = scala.io.Source.fromFile(pathToJson)
      val fileAsString = file.mkString
      file.close()
      decode[MovieData](fileAsString).getOrElse(MovieData())
    } catch {
      case e: Exception =>
        System.err.println(
          s"Encountered error $e while trying to parse file $pathToJson"
        )
        MovieData()
    }
  }

  private def getMoviesOfActorHelper(movieData: MovieData,
                                     actor: String): Seq[Movie] = {
    val actorId = movieData.actors.find(_.name == actor).getOrElse(Actor()).id
    val movieIds =
      movieData.actorMovies.filter(_.actorId == actorId).map(_.movieId)
    movieData.movies.filter(movie => movieIds.contains(movie.id))
  }

  private def getActorsFromMovieHelper(movieData: MovieData,
                                       movie: String): Seq[Actor] = {
    val movieId = movieData.movies.find(_.name == movie).getOrElse(Movie()).id
    val actorIds =
      movieData.actorMovies.filter(_.movieId == movieId).map(_.actorId)
    movieData.actors.filter(actor => actorIds.contains(actor.id))
  }

  def getMoviesOfActor(jsonFilePath: String, actor: String): Seq[Movie] = {
    val movieData = parseRawJson(jsonFilePath)
    val movies = getMoviesOfActorHelper(movieData, actor)

    val output = movies.foldLeft("")(
      (acc, movie) => acc + s"${movie.name}: ${movie.releaseDate}\n"
    )
    System.out.println(output)
    movies
  }

  def getActorsFromMovie(jsonFilePath: String, movie: String): Seq[Actor] = {
    val movieData = parseRawJson(jsonFilePath)
    val actors = getActorsFromMovieHelper(movieData, movie)

    val output = actors.foldLeft("")(
      (acc, actor) => acc + s"${actor.name}: ${actor.birthday}\n"
    )
    System.out.println(output)
    actors
  }

  def getCoStars(jsonFilePath: String, actor: String): Seq[Actor] = {
    val movieData = parseRawJson(jsonFilePath)
    val movies = getMoviesOfActorHelper(movieData, actor)
    val coStars = movies
      .flatMap(movie => getActorsFromMovieHelper(movieData, movie.name))
      .filterNot(_.name == actor)
      .toSet

    val output = coStars.foldLeft("")(
      (acc, coStar) => acc + s"${coStar.name}: ${coStar.birthday}\n"
    )
    System.out.println(output)
    coStars.toSeq
  }

}
