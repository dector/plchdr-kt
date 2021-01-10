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
import org.jetbrains.skija.EncodedImageFormat
import org.jetbrains.skija.Surface


fun main() {
    val app = routes(
        "/{width}/{height}/{color:[a-f0-9]*}" bind GET to { req: Request ->
            val w = req.path("width")?.toIntOrNull() ?: 100
            val h = req.path("height")?.toIntOrNull() ?: w
            val color = (req.path("color")?.toLongOrNull(radix = 16) ?: 0xa78bfaL)
                .toInt()

            val pngData = createPngImage(w, h, color)

            Response(OK)
                .header("Content-Type", "image/png")
                .body(pngData.inputStream())
        }
    )

    val server = app.asServer(Netty())
    server.start().block()
}

private fun createPngImage(w: Int, h: Int, color: Int): ByteArray {
    val surface = Surface.makeRasterN32Premul(w, h)!!
    val canvas = surface.canvas

    val bgColor = (0xff000000 or color.toLong()).toInt()
    canvas.clear(bgColor)

    val image = surface.makeImageSnapshot()
    val pngData = image.encodeToData(EncodedImageFormat.PNG)!!

    return pngData.bytes
}
