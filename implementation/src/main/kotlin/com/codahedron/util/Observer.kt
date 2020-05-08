package com.codahedron.util

class Observer<T>(){
   private val listeners = ArrayList<(T)->Unit>()

   fun addListener(l:(T)->Unit){
      listeners.add(l)
   }

   fun update(value:T){
      for(listener in listeners){
         listener(value)
      }
   }
}