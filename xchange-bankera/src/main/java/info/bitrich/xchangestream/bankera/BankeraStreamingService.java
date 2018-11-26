package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankeraStreamingService extends JsonNettyStreamingService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public BankeraStreamingService(String uri) {
		super(uri, Integer.MAX_VALUE);
	}

	@Override
	protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
		return null;
	}

}
