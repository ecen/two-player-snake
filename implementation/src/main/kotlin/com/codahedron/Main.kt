package com.codahedron

import com.codahedron.graphics.GraphicsHandler
import com.codahedron.players.CarloPlayer
import com.codahedron.players.HumanPlayer
import com.codahedron.players.RandomPlayer
import com.codahedron.players.ScriptedPlayer

fun main() {
   val window = GraphicsHandler().setSize(800, 800)
   val arena = Arena(HumanPlayer(0, window.keyObserver), CarloPlayer(1, 100), 10, 10, window)
   arena.start(500)
   //arena.benchmark(1000)
}