package zhttp

import zhttp.core._
import zio.Has

package object service {
  private[service] val AUTO_RELEASE_REQUEST   = false
  private[service] val SERVER_CODEC_HANDLER   = "SERVER_CODEC"
  private[service] val SERVER_ENCODER         = "ENCODER"
  private[service] val FLUSH_CONSOLIDATOR     = "FLUSH_CONSOLIDATOR"
  private[service] val SERVER_DECODER         = "DECODER"
  private[service] val OBJECT_AGGREGATOR      = "OBJECT_AGGREGATOR"
  private[service] val HTTP_REQUEST_HANDLER   = "HTTP_REQUEST"
  private[service] val HTTP_KEEPALIVE_HANDLER = "HTTP_KEEPALIVE"
  private[service] val WEB_SOCKET_HANDLER     = "WEB_SOCKET_HANDLER"

  type ChannelFactory       = Has[JChannelFactory[JChannel]]
  type EventLoopGroup       = Has[JEventLoopGroup]
  type ServerChannelFactory = Has[JChannelFactory[JServerChannel]]
  type UServer              = Server[Any, Nothing]
}
