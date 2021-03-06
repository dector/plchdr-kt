package space.dector.plchdr

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.jetbrains.skija.*
import java.io.InputStream


fun main() {
    val handler = { req: Request ->
        val w = req.path("width")
            ?.toIntOrNull()
            ?.takeIf { it > 0 }
            ?: 100
        val h = req.path("height")
            ?.toIntOrNull()
            ?.takeIf { it > 0 }
            ?: w
        val color = parseColor(req.path("color"))
        val text = req.query("text")

        Response(OK)
            .header("Content-Type", "image/png")
            .body(
                createImageStream(
                    width = w,
                    height = h,
                    color = color,
                    text = text,
                )
            )
    }

    val app = routes(
        "/{width:[0-9]+}" bind GET to handler,
        "/{width:[0-9]+}/{color:[a-f0-9]*}" bind GET to handler,
        "/{width:[0-9]+}x{height:[0-9]+}" bind GET to handler,
        "/{width:[0-9]+}x{height:[0-9]+}/{color:[a-f0-9]*}" bind GET to handler,
    )

    val server = app.asServer(Netty())
    server.start().block()
}

private fun createPngImage(
    w: Int,
    h: Int,
    color: Int,
    action: ((Canvas) -> Unit)? = null,
): ByteArray {
    val surface = Surface.makeRasterN32Premul(w, h)
    val canvas = surface.canvas

    val bgColor = (0xff000000 or color.toLong()).toInt()
    canvas.clear(bgColor)

    action?.invoke(canvas)

    val image = surface.makeImageSnapshot()
    val pngData = image.encodeToData(EncodedImageFormat.PNG)!!

    return pngData.bytes
}


const val DefaultColor = 0xa78bfa

private fun parseColor(str: String?): Int {
    str ?: return DefaultColor

    val colorString = when (str.length) {
        1 -> str.repeat(6)
        2 -> str.repeat(3)
        3 -> str.map { "$it$it" }.joinToString("")
        6 -> str
        else -> ""
    }

    return colorString.toIntOrNull(radix = 16) ?: DefaultColor
}

private val font = Font(Typeface.makeDefault(), 32f)
private val paint = Paint().setColor(0xff000000.toInt())

fun createImageStream(
    width: Int,
    height: Int,
    color: Int,
    text: String? = null,
): InputStream {
    val pngData = createPngImage(width, height, color) { canvas ->
        if (text != null) {
            val measures = font.measureText(text, paint)
            val x = (width - measures.width) / 2
            val y = (height + measures.height / 2) / 2

            canvas.drawString(text, x, y, font, paint)
        }
    }

    return pngData.inputStream()
}
