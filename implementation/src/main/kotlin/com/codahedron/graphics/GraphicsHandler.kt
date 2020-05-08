package com.codahedron.graphics

import com.codahedron.util.Observer
import java.awt.Color
import java.awt.Frame
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

//The offset variable of how far into the window to start drawing
// (Since the AWT windows require this to start drawing on the screen)
const val xOffset = 5
const val yOffset = 50


/**
 * A frontend to javas AWT graphics library
 */
class GraphicsHandler:KeyListener {
   //Some default values
   private var title = ""
   var width = 400
   var height = 300
   //The actual frame
   private var theFrame: TheFrame? = null
   @Volatile
   private var currentlyDrawing = false

   //Store the drawable shapes here.
   private val drawables = ArrayList<Drawable>()

   //The observer for the keys
   val keyObserver: Observer<KeyEvent> = Observer()


   fun setTitle(title: String): GraphicsHandler {
      this.title = title
      return this
   }

   fun setSize(width: Int, height: Int): GraphicsHandler {
      this.width = width
      this.height = height
      return this
   }

   /**
    * @param d a drawable to be added.
    */
   fun add(d: Drawable): GraphicsHandler {
      //Wait for the drawing to be done
      while (currentlyDrawing) {}
      drawables.add(d)
      return this
   }

   /**
    * Clear all the objects from the screen.
    */
   fun clear() {
      while (currentlyDrawing) {}
      currentlyDrawing = true
      drawables.removeIf { true }
      currentlyDrawing = false
   }

   //Draw the current state to the screen
   fun draw() {
      //Wait for a previous drawing call to be done
      while (currentlyDrawing) {}

      currentlyDrawing = true

      if (theFrame != null) {
         //The frame exists
         theFrame!!.repaint()
      } else {
         //The frame does not exist yet. Create it.
         theFrame =
            TheFrame(
               title,
               this
            ) { currentlyDrawing = false }

         theFrame!!.addKeyListener(this)
      }
   }


   override fun keyTyped(p0: KeyEvent?) {}

   override fun keyPressed(p0: KeyEvent?) {
      if(p0!=null) keyObserver.update(p0)
   }

   override fun keyReleased(p0: KeyEvent?) {}

   private class TheFrame(
      title: String,
      val handler: GraphicsHandler,
      val reportDonePainting: () -> Unit
   ) : Frame(title) {

      override fun paint(g: Graphics) {
         for (drawable in handler.drawables) {
            drawable.draw(g)
         }

         reportDonePainting()
      }

      init {
         setSize(handler.width, handler.height)

         isVisible = true

         addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
               dispose()
               exitProcess(0)
            }
         }
         )
      }
   }
}

/**
 * An interface for thing which can be drawn by this class
 */
interface Drawable {
   fun draw(g: Graphics)
}