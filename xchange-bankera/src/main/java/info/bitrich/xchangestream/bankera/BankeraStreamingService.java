package info.bitrich.xchangestream.bankera;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.bankera.dto.BankeraWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;

import java.io.IOException;

public class BankeraStreamingService extends JsonNettyStreamingService {

	public BankeraStreamingService(String uri) {
		super(uri, Integer.MAX_VALUE);
	}

	@Override
	protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return message.get("type").asText();
	}

	@Override
	public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    if (args.length != 1) throw new IOException("SubscribeMessage: Insufficient arguments");
    BankeraWebSocketSubscriptionMessage subscribeMessage =
        new BankeraWebSocketSubscriptionMessage(String.valueOf(args[0]));
    return objectMapper.writeValueAsString(subscribeMessage);
	}

	@Override
	public String getUnsubscribeMessage(String channelName) throws IOException {
		return null;
	}

	@Override
	protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
		return null;
	}

	@Override
	protected WebSocketClientHandler getWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketClientHandler.WebSocketMessageHandler handler) {
		return new BankeraExchangeNettyWebSocketClientHandler(handshaker, handler);
	}

	protected class BankeraExchangeNettyWebSocketClientHandler extends NettyWebSocketClientHandler {

		protected BankeraExchangeNettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
			super(handshaker, handler);
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) {
			super.channelInactive(ctx);
		}
	}
}
