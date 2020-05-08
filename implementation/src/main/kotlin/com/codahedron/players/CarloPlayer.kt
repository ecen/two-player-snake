package com.codahedron.players

import com.codahedron.*
import com.codahedron.gamelogic.SnakeState
import com.codahedron.mcts.SnakeNode

/**
 * Player utilizing Monte Carlo Tree Search to find the best move.
 */
class CarloPlayer(override val me: Int, val n: Int): Player {
   override lateinit var state: SnakeState

   override fun makeMove() : RelativeDirection {
      val node = SnakeNode(state, me, me, null)

      return node.findBestDirection(n)
   }

}

