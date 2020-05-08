package com.codahedron.graphics

import com.codahedron.gamelogic.SnakeState
import java.awt.Color
import java.awt.Graphics

class GraphicsGrid(
   val w: Int,
   val h: Int,
   val nrRows: Int,
   val nrCols: Int,
   val color: Color,
   private var state: SnakeState
) : Drawable {

   override fun draw(g: Graphics) {
      val widthCol = ((w.toDouble() - xOffset * 2) / nrCols).toInt()
      val heightRow = ((h.toDouble() - yOffset * 2) / nrRows).toInt()

      g.color = Color.BLACK
      for (i in 0.rangeTo(nrRows)) {
         g.drawLine(xOffset, i * heightRow + yOffset, widthCol * nrCols + xOffset, i * heightRow + yOffset)
      }

      for (i in 0.rangeTo(nrRows)) {
         g.drawLine(i * widthCol + xOffset, yOffset, i * widthCol + xOffset, heightRow * nrRows + yOffset)
      }

      for ((i, snake) in state.getAllSnakes().withIndex()) {
         g.color = if (i == 0) Color.PINK else Color.CYAN
         for ((j, it) in snake.positions.withIndex()) {
            if (j == snake.positions.size - 1) break
            g.fillRect(it.x * widthCol + xOffset, it.y * heightRow + yOffset, widthCol, heightRow)
         }

         g.color = if (i == 0) Color.RED else Color.BLUE
         val last = snake.positions.last()
         g.fillRect(last.x * widthCol + xOffset, last.y * heightRow + yOffset, widthCol, heightRow)
      }
   }

   fun setState(state: SnakeState) {
      this.state = state
   }
}