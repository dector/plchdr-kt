package space.dector.plchdr

import org.jetbrains.skija.Canvas
import org.jetbrains.skiko.SkiaRenderer
import org.jetbrains.skiko.SkiaWindow


fun main() {
    val window = SkiaWindow().apply {
        layer.renderer = object : SkiaRenderer {
            override fun onRender(canvas: Canvas, width: Int, height: Int) {
                canvas.clear(0xffa78bfa.toInt())
            }

            override fun onInit() {
            }

            override fun onReshape(width: Int, height: Int) {
            }

            override fun onDispose() {
            }
        }
        setSize(500, 500)
    }

    window.isVisible = true
    println("It works!")
}
