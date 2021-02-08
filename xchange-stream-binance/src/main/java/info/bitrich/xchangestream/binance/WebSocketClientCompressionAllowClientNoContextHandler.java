package info.bitrich.xchangestream.binance;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateFrameClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateClientExtensionHandshaker;

import static io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateServerExtensionHandshaker.MAX_WINDOW_SIZE;

/**
 * author: @paolo-rendano
 * quick fix introduced for binance websocket broken on 2021-02-08 with "invalid websocket extension handshake"
 */
@ChannelHandler.Sharable
public final class WebSocketClientCompressionAllowClientNoContextHandler
    extends WebSocketClientExtensionHandler {

  public static final WebSocketClientCompressionAllowClientNoContextHandler INSTANCE =
      new WebSocketClientCompressionAllowClientNoContextHandler();

  private WebSocketClientCompressionAllowClientNoContextHandler() {
    super(
        new PerMessageDeflateClientExtensionHandshaker(
            6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), MAX_WINDOW_SIZE, true, true),
        new DeflateFrameClientExtensionHandshaker(false),
        new DeflateFrameClientExtensionHandshaker(true));
  }
}
