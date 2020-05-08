package com.codahedron.mcts

import com.codahedron.RelativeDirection
import com.codahedron.gamelogic.SnakeState
import com.codahedron.gamelogic.GameState
import com.codahedron.players.RandomPlayer

/**
 * A node in the search tree that will determine the best move to make
 * using Monte Carlo Tree Search (MCTS)
 *
 * @param state the state from which a best direction will be found
 * @param me the player we will optimize for
 * @param currentPlayer the player whose turn it is to make a move from this node
 *                THIS HAS TO BE THE SAME AS me FOR THE ROOT NODE
 * @param parent the parent of this node. Can be null for root node.
 */
class SnakeNode(val state: SnakeState, val me: Int, val currentPlayer: Int, val parent: SnakeNode?) {
   var wins: Double = 0.0
   var tries: Int = 0
   var children: MutableList<SnakeNode> = ArrayList()
   var expanded: Boolean = false
      get

   // The move that leads to the child with same index
   var childrenMoves: MutableList<RelativeDirection> = ArrayList()

   /**
    * Select the root node. It will recursively select child nodes to
    * find the best direction to go in.
    *
    * @param n how many rollouts should be made before deciding on the best move
    */
   fun findBestDirection(n: Int): RelativeDirection {
      expand() // Expand the root node

      // If root node has no children, all moves will lose
      if (children.isEmpty()) {
         return RelativeDirection.FORWARD
      }

      for (i in 0..n) {
         selectChild().select()
      }

      val bestChild: SnakeNode = children.maxBy { it.tries }!!

      //println("Max depth: ${getDeepest(0)}")

      return childrenMoves.get(children.indexOf(bestChild))
   }

   /**
    * Select this node. Will expand, simulate or select a child as appropriate
    * for Monte Carlo Tree Search.
    */
   fun select() {
      if (!expanded) {
         // If current node is not expanded
         expand()
         simulate()
      } else if (!children.all { it.expanded }) {
         // If not all children are expanded
         val child = children.random()
         child.select()
      } else if (children.isNotEmpty()) {
         // If all children are expanded
         selectChild().select()
      } else {
         // If there are no children
         simulate()
      }
   }

   /**
    * Select the best child based on its score and how many times its been
    * tried already.
    */
   private fun selectChild(): SnakeNode {
      val child = children.maxBy { it.score() + Math.sqrt(2 * Math.log(tries.toDouble()) / it.tries) }
      return child!!
   }

   /**
    * Expand this node, enumerating all states and moves the current player can
    * go to.
    */
   private fun expand() {
      expanded = true
      val dirs = state.getPossibleDirections(currentPlayer, false)

      for (dir in dirs) {
         val child = SnakeNode(
            state.update(
               dir,
               currentPlayer
            ), // Create a possible state for current player
            me,
            1 - currentPlayer, // Next node will allow next player to move
            this
         )
         children.add(child)
         childrenMoves.add(dir)
      }

   }

   /**
    * Score this node (through simulation), save that score and backpropagate.
    */
   private fun simulate() {
      val score = score(state)

      wins += score
      tries += 1

      backpropagate(score)
   }

   /**
    * Backpropagates a single simulation attempt, given what its score was
    */
   private fun backpropagate(score: Double) {
      if (parent != null) {
         parent.wins += score
         parent.tries += 1
         parent.backpropagate(score)
      }
   }

   /**
    * Get score for the current node.
    */
   private fun score(): Double {
      if (tries == 0) return Double.MAX_VALUE

      return wins / tries
   }

   /**
    * Find a score for a given game state.
    */
   private fun score(_state: SnakeState): Double {
      var state = _state

      val playerCurrent = RandomPlayer(currentPlayer)
      playerCurrent.state = state
      val playerOther = RandomPlayer(1 - currentPlayer)
      playerOther.state = state

      // Make an extra move for the player that is behind one move
      // Only current player should be able to be behind
      if (state.getSnake(currentPlayer).positions.size != state.getSnake(1 - currentPlayer).positions.size) {
         state = state.update(playerCurrent.makeMove(), currentPlayer)
         playerCurrent.state = state
         playerOther.state = state
      }

      var states: MutableList<SnakeState> = ArrayList()

      // Simulate state until game ends according to rollout policy
      while (state.hasWon() is GameState.Ongoing) {
         states.add(state)
         state = state.update(playerCurrent.makeMove(), currentPlayer)
         state = state.update(playerOther.makeMove(), 1-currentPlayer)
         playerCurrent.state = state
         playerOther.state = state
      }

      // Return 1 if we win, 0.5 if draw, 0 if we lose
      if (state.hasWon() is GameState.Winner) {
         if ((state.hasWon() as GameState.Winner).idx == me) {
            return 1.0
         } else {
            return 0.0
         }
      }
      return 0.5
   }

   /**
    * @return how many layers down the deepest child is
    */
   private fun getDeepest(sum: Int): Int {
      if (children.size > 0) {
         return children.maxBy { it.getDeepest(sum + 1) }!!.getDeepest(sum + 1)
      }

      return sum + 1
   }
}