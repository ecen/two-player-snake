package com.codahedron.gamelogic

open class GameState {
   class Ongoing : GameState(){
      override fun toString(): String {
         return "Ongoing"
      }
   }
   class Draw : GameState(){
      override fun toString(): String {
         return "Draw"
      }
   }
   class Winner(val idx: Int) : GameState(){
      override fun toString(): String {
         return "Winner: $idx"
      }
   }
}