package com.codahedron.graphics

import java.awt.Color
import java.awt.Graphics

class GraphicsRect(
   private val x: Int,
   private val y: Int,
   private val w: Int,
   private val h: Int,
   val color: Color
) : Drawable {

   override fun draw(g: Graphics) {
      g.color = color
      g.drawRect(x + xOffset, y + yOffset, w, h)
   }
}