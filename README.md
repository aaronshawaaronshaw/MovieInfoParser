# Cognitops Coding Challenge

Aaron Shaw aaronwshaw@gmail.com

## Description

#### Task 1

In Scala, write code that takes a json file and is capable of solving these requirements. The Json will be of the following format
{
    "actors": [
        {
            "id": SOME INTEGER,
            "name": "NAME",
            "birthday": "BIRTHDAY"
        }
    ],
    "movies": [
        {
            "id": SOME INTEGER,
            "name": "NAME",
            "releaseDate": "DATE"
        }
    ],
    "actorMovies": [
        {
            "actorId": SOME INTEGER,
            "movieId": SOME INTEGER
        }
    ]
}

Write the following 3 separate programs:

Program 1: The program takes 2 command-line arguments: 1) The name of the file containing the JSON and 2) the name 
(name, not id) of an actor. The program should print out the names and release dates of all the movies that the actor 
was in.

Program 2: The program takes 2 command-line arguments: 1) The name of the file containing the JSON and 2) the name 
(name, not id) of a movie. The program should print out the names and birthdays of all actors that were in the movie.

Program 3: The program takes 2 command-line arguments: 1) The name of the file containing the JSON and 2) the name 
(name, not id) of an actor. The program should print out the unique names of all actors who were in any movie with 
the specified actor.

For simplicity, just read the birthdays and release dates in as strings. Don’t worry about converting them into 
date structures.

Hint: You should use a 3rd-party library for parsing the JSON. You can use whatever 3rd-party library you like. 
We use Circe, but if there’s a different library you’re familiar with, feel free to use that.



#### Task 2

Now, think about how you’d store this actor and movie information in a database. Pick a relational database of your 
choice (we use Postgres, but any relational database is fine). Email us the SQL for creating the tables needed to store 
the actors, movies, and their relationships. In addition, email us the SQL for the following queries:

Query 1: Given an actor name (name, not id), find the actor’s movies and return their names and release dates.

Query 2: Given a movie name (name, not id), find the movie’s actors and return their names and birthdays.

## Design Decisions

I chose to use the [Circe library](https://circe.github.io/circe/) to assist with parsing Json. Circe is one of the more 
robust json parsing tools available in Scala and provided methods to automatically parse Json into case classes. This 
feature greatly abstracts away complexity of the solution, allowing the majority of the code to focus on manipulation of 
the case classes rather nitty gritty details of indexing into Json.

The automatic json converter in Circe takes in Json as a string and generates case classes from it. Because of this feature, 
I chose to open the Json file as a string in its entirety using _scala.io.Source_. This approach is limited because the
entire file must be read into memory. For the purpose of this coding challenge, I thought that acceptable, however, in 
production this may need to be modified to account for large Json files that do not fit into memory.

I attempted to modularize my code to reduce redundancy and to follow coding best practices. Each function is responsible
for one task, allowing the reuse of functions. A secondary benefit to this is increased ease of debugging by allowing 
errors in testing to be more quickly trace back to a point of failure.

## Future Work

This project could be expanded in many ways. For starters, there are many additional functions or pieces of data related
to actors/movies that could be included here. Movie info such as _rating, gross revenue, net revenue, awards, etc_ could
be added. Actor info such as _nationality_ or _net worth_ could be added. As additional information becomes present in the Json,
additional methods can be written.

This project could be made more robust by attaching it to a database. A full set of CRUD methods could be added to access
or modify movie data. Attaching this to a DB also provides permanence to the data, allowing us to more easily aggregate
large amounts of movie information. One possible DB to use for this is [Postgres](https://www.postgresql.org/). 
Postgres has many services to assist with interfacing with Scala. One popular example is 
[Slick](https://scala-slick.org/doc/3.1.0/index.html).

## Dependenices

This project is written in _Scala_, and requires _sbt_.
_sbt_ can be located [here](https://www.scala-sbt.org/download.html) (https://www.scala-sbt.org/download.html).
SBT's [Getting Started page](https://www.scala-sbt.org/1.x/docs/Setup.html) may be useful (https://www.scala-sbt.org/1.x/docs/Setup.html).

This projects dependencies include _scalatest_ and _circe_. The needed files are managed by sbt build file and 
will be downloaded from their respective repositories before attempting to run the project. You can alternatively use
_sbt update_  to update the dependencies.

