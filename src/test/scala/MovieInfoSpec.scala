import MovieInfo.{Actor, Movie}
import org.scalatest.FlatSpec
import org.scalatest.matchers.should.Matchers

class MovieInfoSpec extends FlatSpec with Matchers {

  val validJsonPath = "src/test/json/validJson.json"
  val garbageJsonPath = "src/test/json/garbageJson.json"
  val emptyJsonPath = "src/test/json/emptyJson.json"

  "MovieInfo" should "get all the movies an actor has been in" in {
    val knownPacinoMovies = Seq(
      Movie(1, "Godfather Part 2", "December 20, 1974"),
      Movie(2, "Heat", "December 15, 1995"),
      Movie(3, "Righteous Kill", "September 12, 2008"),
      Movie(4, "Scarface", "December 1, 1983")
    )
    val pacinoMovies = MovieInfo.getMoviesOfActor(validJsonPath, "Al Pacino")
    pacinoMovies.length shouldBe knownPacinoMovies.length
    pacinoMovies should contain theSameElementsAs knownPacinoMovies
  }

  it should "get no movies if an actor has been in no movies" in {
    MovieInfo.getMoviesOfActor(validJsonPath, "Frank Sinatra").length shouldBe 0
  }

  it should "get all the actors in a movie" in {
    val realFockers = Seq(
      Actor(1, "Robert Deniro", "August 17, 1943"),
      Actor(3, "Dustin Hoffman", "August 8, 1937"),
      Actor(4, "Ben Stiller", "November 30, 1965")
    )
    val allTheFockers =
      MovieInfo.getActorsFromMovie(validJsonPath, "Meet the Fockers")
    allTheFockers.length shouldBe realFockers.length
    allTheFockers should contain theSameElementsAs realFockers
  }

  it should "get no actors if a movie does not exist" in {
    MovieInfo.getActorsFromMovie(validJsonPath, "Die Hard").length shouldBe 0
  }

  it should "get all the costars of an actor" in {
    val stillerCoStars = Seq(
      Actor(1, "Robert Deniro", "August 17, 1943"),
      Actor(3, "Dustin Hoffman", "August 8, 1937")
    )
    var coStars = MovieInfo.getCoStars(validJsonPath, "Ben Stiller")
    coStars.length shouldBe stillerCoStars.length
    coStars should contain theSameElementsAs stillerCoStars

    val deniroCoStars = Seq(
      Actor(2, "Al Pacino", "April 25, 1940"),
      Actor(3, "Dustin Hoffman", "August 8, 1937"),
      Actor(4, "Ben Stiller", "November 30, 1965")
    )
    coStars = MovieInfo.getCoStars(validJsonPath, "Robert Deniro")
    coStars.length shouldBe deniroCoStars.length
    coStars should contain theSameElementsAs deniroCoStars
  }

  it should "handle invalid json by returning nothing" in {
    val temp = MovieInfo.getActorsFromMovie(garbageJsonPath, "Robert Deniro")
    temp.length shouldBe 0
  }

  it should "handle empty json by returning nothing" in {
    val temp = MovieInfo.getActorsFromMovie(emptyJsonPath, "Robert Deniro")
    temp.length shouldBe 0
  }

  it should "handle cases where the file path to json was incorrect" in {
    val temp =
      MovieInfo.getActorsFromMovie("notARealFile.json", "Robert Deniro")
    temp.length shouldBe 0
  }

}
