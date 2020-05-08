package com.codahedron.players

import com.codahedron.*
import com.codahedron.gamelogic.SnakeState

class RandomPlayer(override val me: Int): Player {
   override lateinit var state: SnakeState

   override fun makeMove() : RelativeDirection {
      val dirs: List<RelativeDirection> = state.getPossibleDirections(me, true)
      return dirs[(Math.random() * dirs.size).toInt()]
   }

}

