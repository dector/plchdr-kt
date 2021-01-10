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


fun main() {
    val app = routes(
        "/{width}/{height}" bind GET to { req: Request ->
            val w = req.path("width")?.toIntOrNull() ?: 100
            val h = req.path("height")?.toIntOrNull() ?: w

            Response(OK).body("$w-$h")
        }
    )

    val server = app.asServer(Netty())
    server.start().block()

/*
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
*/
}
