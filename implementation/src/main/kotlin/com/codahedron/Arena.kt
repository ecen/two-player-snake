package com.codahedron

import com.codahedron.gamelogic.GameState
import com.codahedron.gamelogic.SnakeState
import com.codahedron.graphics.GraphicsGrid
import com.codahedron.graphics.GraphicsHandler
import com.codahedron.players.Player
import java.awt.Color

class Arena (val player0 : Player, val player1: Player, val boardWidth: Int, val boardHeight: Int, val window:GraphicsHandler) {
   private var state : SnakeState = SnakeState.emptyState(boardWidth, boardHeight)
   private val grid = GraphicsGrid(window.width, window.height, boardWidth, boardHeight, Color.BLACK, state)

   fun start(msDelay: Int): GameState {
      if (msDelay > 0) {
         window.add(grid)
         window.draw()
      }

      player0.state = state
      player1.state = state

      var t0 = System.currentTimeMillis()
      while (state.hasWon() is GameState.Ongoing) {
         var t1 = System.currentTimeMillis()
         if (t1 >= t0 + msDelay) {
            t0 = t1
            //window.clear()
            state = state.update(listOf(player0.makeMove(), player1.makeMove()))
            grid.setState(state)
            player0.state = state
            player1.state = state

            if (msDelay > 0) window.draw()
            //println(state.getSnake(0).positions)
         }
      }

      if (msDelay > 0) {
         if (state.hasWon() is GameState.Draw) {
            println("Draw!")
         } else if ((state.hasWon() as GameState.Winner).idx == 0) {
            println("Red player won!")
         } else if ((state.hasWon() as GameState.Winner).idx == 1) {
            println("Blue player won!")
         }
      }

      return state.hasWon()
   }

   fun reset() {
      state = SnakeState.emptyState(boardWidth, boardHeight)
   }

   fun benchmark(times: Int) {
      val results: MutableList<GameState> = ArrayList()
      for (i in 0..times) {
         results.add(start(0))
         reset()
         print("${i} / ${times}\r")
      }
      println("\n")
      println("Player 0 won: ${results.filter { state -> state is GameState.Winner && state.idx == 0 }.size}")
      println("Player 1 won: ${results.filter { state -> state is GameState.Winner && state.idx == 1 }.size}")
      println("        Draw: ${results.filter { state -> state is GameState.Draw }.size}")
   }
}