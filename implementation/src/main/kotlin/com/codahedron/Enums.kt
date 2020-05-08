package com.codahedron

import com.codahedron.util.Vector2i

enum class RelativeDirection {
   LEFT,
   RIGHT,
   FORWARD
}


enum class StaticDirection(val asVector: Vector2i){
   NORTH(Vector2i(0, -1)),
   EAST(Vector2i(1, 0)),
   SOUTH(Vector2i(0, 1)),
   WEST(Vector2i(-1, 0));


   fun turnTo(statDir:StaticDirection):RelativeDirection?{
      val myIdx = values().indexOf(this)
      val otherIdx = values().indexOf(statDir)

      if(myIdx==otherIdx){
         return RelativeDirection.FORWARD
      }else if (Math.floorMod(myIdx - 1, values().size) == otherIdx){
         return RelativeDirection.LEFT
      }else if(Math.floorMod(myIdx+1, values().size)==otherIdx){
         return RelativeDirection.RIGHT
      }else{
         return null
      }
   }

   fun turnTo(relDir:RelativeDirection):StaticDirection{
      val statDirs = values()
      val currentIdx = values().indexOf(this)

      if(relDir==RelativeDirection.LEFT){
         val nextIdx = if(currentIdx==0) statDirs.size-1 else currentIdx-1

         return statDirs[nextIdx]
      }else if(relDir==RelativeDirection.RIGHT){
         val nextIdx = if(currentIdx==statDirs.size-1) 0 else currentIdx+1

         return statDirs[nextIdx]
      }else{
         return this
      }
   }
}



fun main() {
   println(StaticDirection.WEST.turnTo(StaticDirection.NORTH))
   println(StaticDirection.SOUTH.turnTo(StaticDirection.EAST))

   println(
      StaticDirection.NORTH
         .turnTo(RelativeDirection.LEFT)
         .turnTo(RelativeDirection.LEFT)
         .turnTo(RelativeDirection.FORWARD)
         .turnTo(RelativeDirection.LEFT)
         .turnTo(RelativeDirection.LEFT).turnTo(RelativeDirection.RIGHT))
}