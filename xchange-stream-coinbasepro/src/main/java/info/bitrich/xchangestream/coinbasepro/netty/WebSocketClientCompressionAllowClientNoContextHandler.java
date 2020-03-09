package info.bitrich.xchangestream.coinbasepro.netty;

import static io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateServerExtensionHandshaker.MAX_WINDOW_SIZE;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.DeflateFrameClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateClientExtensionHandshaker;

/**
 * Custom WebSocket client extension handler. GDAX needs very specific WebSocket extensions enabled.
 */
@ChannelHandler.Sharable
public final class WebSocketClientCompressionAllowClientNoContextHandler
    extends WebSocketClientExtensionHandler {

  public static final WebSocketClientCompressionAllowClientNoContextHandler INSTANCE =
      new WebSocketClientCompressionAllowClientNoContextHandler();

  private WebSocketClientCompressionAllowClientNoContextHandler() {
    super(
        new PerMessageDeflateClientExtensionHandshaker(
            6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), MAX_WINDOW_SIZE, true, false),
        new DeflateFrameClientExtensionHandshaker(false),
        new DeflateFrameClientExtensionHandshaker(true));
  }
}
