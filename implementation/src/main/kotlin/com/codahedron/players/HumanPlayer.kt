package com.codahedron.players

import com.codahedron.*
import com.codahedron.gamelogic.SnakeState
import com.codahedron.util.Observer
import java.awt.event.KeyEvent

/**
 * @param me The index of the snake associated with this player.
 * @param observer the observer of the key events
 */
class HumanPlayer(override val me: Int, observer: Observer<KeyEvent>): Player {
   override lateinit var state: SnakeState
   var currentDir = RelativeDirection.FORWARD

   init {
      //When a key event is registered.
      observer.addListener {k ->
         //Depending on the key pressed, find the turn direction required to go to that direction.
         // Ex. if the snake is facing east and up is pressed, then turn left.
         var turnDir:RelativeDirection? = null
         when (k.keyCode) {
            KeyEvent.VK_UP -> {
               turnDir = state.getSnake(me).currentDir.turnTo(StaticDirection.NORTH)
            }
            KeyEvent.VK_DOWN -> {
               turnDir = state.getSnake(me).currentDir.turnTo(StaticDirection.SOUTH)
            }
            KeyEvent.VK_LEFT -> {
               turnDir = state.getSnake(me).currentDir.turnTo(StaticDirection.WEST)
            }
            KeyEvent.VK_RIGHT -> {
               turnDir = state.getSnake(me).currentDir.turnTo(StaticDirection.EAST)
            }
         }

         //If the turn dir is still null then no valid button has been pressed. Ignore
         if(turnDir != null){
            currentDir = turnDir
         }
      }
   }

   override fun makeMove() : RelativeDirection {
      //Make a move to the current direction and reset it to forward.
      val retVal = currentDir
      currentDir = RelativeDirection.FORWARD
      return retVal
   }
}

