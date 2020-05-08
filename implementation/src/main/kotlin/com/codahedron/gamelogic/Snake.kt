package com.codahedron.gamelogic

import com.codahedron.RelativeDirection
import com.codahedron.StaticDirection
import com.codahedron.util.Vector2i

class Snake(
   val positions:List<Vector2i>,
   val currentDir: StaticDirection
){

   constructor(position: Vector2i, currentDir: StaticDirection) :
           this(listOf(position), currentDir)


   fun move(dir: RelativeDirection, boardSize: Vector2i): Snake {
      val currentPositions = ArrayList(positions)
      val newDir = currentDir.turnTo(dir)
      val head = positions.last()
      val nextHead = head.add(newDir.asVector).mod(boardSize)

      currentPositions.add(nextHead)
      return Snake(currentPositions, newDir)
   }

   override fun toString(): String {
      return "$positions facing $currentDir"
   }
}