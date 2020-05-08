_Created by Algot Johansson and Eric Guldbrand, Group 17._

# Compile instructions
Program written in Kotlin using Intellij IDEA and Maven. To compile, you can choose one of the methods below. Compiling requires Java 13 and Maven >= 3.0.0.

## Intellij IDEA
Open the `implementation` folder next to this readme as a project in Intellij IDEA and run `Main.kt`.

## Command line with Maven
Enter the `implementation` folder. Build an executable jar file by running

`mvn compile && mvn package`

and then run the jar file using using

`java -jar target/snake-1.0-jar-with-dependencies.jar`

Project can be cleaned using `mvn clean`.

# Documentation
On starting the program, a game of human versus a Monte Carlo Tree Search agent using n=100 rollouts per decision will begin. Human is the __red__ player. Control your snake with the arrow keys.

To restart the game you have to exit and restart the program.

To run a benchmark between computer agents or otherwise change player parameters you need to change this in the `src/main/kotlin/com/codahedron/Main.kt` source file and recompile. For available players, look inside `src/main/kotlin/com/codahedron/players/`.
