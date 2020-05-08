package com.codahedron.util

class Vector2i(val x:Int, val y:Int){
   fun add(vec: Vector2i): Vector2i {
      return Vector2i(x + vec.x, y + vec.y)
   }

   override fun toString(): String {
      return "($x, $y)"
   }

   fun mod(size: Vector2i): Vector2i {
      return Vector2i(Math.floorMod(x, size.x), Math.floorMod(y, size.y))
   }

   override fun equals(other: Any?): Boolean {
      if(other is Vector2i){
         return x==other.x && y==other.y
      }
      return false
   }
}