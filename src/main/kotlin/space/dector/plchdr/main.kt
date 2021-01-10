package space.dector.plchdr

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Netty
import org.http4k.server.asServer


fun main() {
    val app = { req: Request ->
        Response(Status.OK).body(req.toString())
    }

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
