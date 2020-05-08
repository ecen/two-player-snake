package com.codahedron.players

import com.codahedron.*
import com.codahedron.gamelogic.SnakeState

class ScriptedPlayer(override val me: Int): Player {
   override lateinit var state: SnakeState

   override fun makeMove() : RelativeDirection {
      val dirs: List<RelativeDirection> = state.getPossibleDirections(me, true)
      if (dirs.contains(RelativeDirection.FORWARD)) {
         return RelativeDirection.FORWARD
      }
      if (dirs.size != 0) return dirs[(Math.random() * dirs.size).toInt()]

      return RelativeDirection.FORWARD
   }

}

