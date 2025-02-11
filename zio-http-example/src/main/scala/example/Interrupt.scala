package example

import java.util.concurrent.TimeoutException

import scala.annotation.nowarn

import zio._

import zio.http._
import zio.http.model.Method
import zio.http.netty.client.NettyClientDriver

object MyServer extends ZIOAppDefault {

  val app = Http.collectZIO[Request] { case Method.GET -> Path.root =>
    ZIO.sleep(10.seconds).map(_ => Response.text("done")).onExit { e =>
      Console.printLine(e).exit
    }
  }

  def run =
    Server.serve(app).provide(Server.default)
}

object MyClient extends ZIOAppDefault {

  @nowarn def run = {
    val req = for {
      resp <- Client.request("http://localhost:8080")
      body <- resp.body.asString
    } yield body

    val reqWithTimeout = // req.timeoutFail(new TimeoutException())(5.seconds)
      for {
        fib <- req.fork
        _   <- ZIO.sleep(5.seconds)
        _   <- fib.interrupt
        _   <- ZIO.fail(new TimeoutException())
      } yield ()

    reqWithTimeout.exit
      .debug("Client finished")
//      .provide(Client.default)
      .provide(Client.fromConfig, ClientConfig.live(ClientConfig().withFixedConnectionPool(2)))
      // .provide(Client.live, NettyClientDriver.fromConfig, ClientConfig.live(ClientConfig().withFixedConnectionPool(2)))
      .debug("EXIT")
  }
}
