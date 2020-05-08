package com.codahedron.gamelogic

import com.codahedron.RelativeDirection
import com.codahedron.StaticDirection
import com.codahedron.util.Vector2i
import java.lang.StringBuilder

class SnakeState private constructor(
   private val size: Vector2i,
   private val snakes:List<Snake>) {

   fun update(dirs:List<RelativeDirection>): SnakeState {
      if(dirs.size!=snakes.size) throw Exception("Nr dirs != nr snakes")
      val updSnakes = snakes.withIndex().map { (i,s) -> s.move(dirs[i], size) }
      return SnakeState(
         size,
         updSnakes
      )
   }

   /**
    * Updates the state by moving only a single player.
    */
   fun update(dir: RelativeDirection, player: Int): SnakeState {
      val updSnake = snakes.get(player).move(dir, size)

      // Copy current snakes, but replace the player snake
      val updSnakes = snakes.withIndex().map { (i, s) -> if (i == player) updSnake else s}
      return SnakeState(
         size,
         updSnakes
      )
   }

   companion object{
      fun emptyState(rows:Int, cols:Int): SnakeState {
         if(rows<5 || cols<5){
            throw Exception("To small grid")
         }

         return SnakeState(
            Vector2i(
               rows,
               cols
            ),
            listOf(
               Snake(
                  Vector2i(
                     ((1.0 / 4) * rows).toInt(),
                     ((3.0 / 4) * rows).toInt()
                  ),
                  StaticDirection.EAST
               ),
               Snake(
                  Vector2i(cols - 2, 1),
                  StaticDirection.WEST
               )
            )
         )
      }
   }

   /**
    * Get the winner
    *
    * @return The index of the winner, null if game is active
    */
   fun hasWon(): GameState {
      if (!snakes.all { snake -> snake.positions.size == snakes.get(0).positions.size }) {
         throw Exception("Not all snakes had the same length when hasWon was called!")
      }
      val losers = ArrayList<Int>()

      for(currentSnake in snakes){
         // Check if the current snakes head is in any other snakes body

         val currentSnakeHead = currentSnake.positions.last()

         for(otherSnake in snakes){
            val otherSnakeBody =
               if(currentSnake == otherSnake)
                  otherSnake.positions.dropLast(1)
               else
                  otherSnake.positions

            if(currentSnakeHead in otherSnakeBody){
               losers.add(snakes.indexOf(currentSnake))
            }
         }
      }

      return if(losers.size==0){
         GameState.Ongoing()
      }else{
         val winners = 0.until(snakes.size).filter { !losers.contains(it) }

         if(winners.size==1){
            GameState.Winner(winners[0])
         }else{
            GameState.Draw()
         }
      }
   }

   fun getPossibleDirections():List<List<RelativeDirection>>{
      val returnList = ArrayList<List<RelativeDirection>>()

      for (snake in snakes){
         val possibleMoves = ArrayList<RelativeDirection>()

         val head = snake.positions.last()

         for(relDir in RelativeDirection.values()){
            if(!isOccupied(head.add(snake.currentDir.turnTo(relDir).asVector))){
               possibleMoves.add(relDir)
            }
         }

         returnList.add(possibleMoves)
      }

      return returnList
   }

   /**
    * Returns a direction that the player can move to without immediately losing.
    * @idx ID of player
    * @alwaysSomething if true, when there are no possible moves, a move will be
    *                   returned anyways.
    */
   fun getPossibleDirections(idx:Int, alwaysSomething: Boolean):List<RelativeDirection>{
      val dirs: List<RelativeDirection> = getPossibleDirections()[idx]
      if (dirs.size != 0) return dirs
      if (alwaysSomething) return listOf(RelativeDirection.FORWARD)

      return listOf()
   }

   fun isOccupied(pos: Vector2i):Boolean{
      for(snake in snakes){
         if(pos in snake.positions){
            return true
         }
      }

      return false
   }

   fun getSnake(idx:Int): Snake {
      return snakes[idx]
   }

   fun getAllSnakes():List<Snake>{
      return snakes
   }

   override fun toString(): String {
      val returnText = StringBuilder()

      for(y in 0.until(size.y)){
         for(x in 0.until(size.x)){
            if(isOccupied(Vector2i(x, y))){
               returnText.append('x')
            }else{
               returnText.append(' ')
            }
         }
         returnText.append('\n')
      }

      return returnText.toString()
   }
}


fun main() {
   print(
      SnakeState.emptyState(
         5,
         5
      )
         .update(listOf(
            RelativeDirection.FORWARD,
            RelativeDirection.LEFT
         ))
         .update(listOf(
            RelativeDirection.FORWARD,
            RelativeDirection.LEFT
         ))
         .update(listOf(
            RelativeDirection.FORWARD,
            RelativeDirection.LEFT
         ))
         .update(listOf(
            RelativeDirection.FORWARD,
            RelativeDirection.LEFT
         ))
         .hasWon()
   )
}