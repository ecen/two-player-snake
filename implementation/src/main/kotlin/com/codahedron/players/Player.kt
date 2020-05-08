package com.codahedron.players

import com.codahedron.RelativeDirection
import com.codahedron.gamelogic.SnakeState

interface Player {
   val me: Int
   var state: SnakeState

   fun makeMove(): RelativeDirection
}