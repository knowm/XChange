package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.ftx.dto.FtxAuthenticationMessage;
import info.bitrich.xchangestream.ftx.dto.FtxStreamRequest;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtxStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(FtxStreamingService.class);
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private boolean isLoggedIn = false;
  private FtxAuthenticationMessage authenticationMessage = null;

  public FtxStreamingService(String apiUrl) {
    super(apiUrl);
  }

  public FtxStreamingService(String apiUrl, FtxAuthenticationMessage authenticationMessage) {
    super(apiUrl);
    this.authenticationMessage = authenticationMessage;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String channelName = "";

    if ("fills".equals(message.get("channel").asText())) {
      channelName = message.get("channel").asText();
    } else if ("orderbook".equals(message.get("channel").asText())) {
      channelName = message.get("channel").asText() + ":" + message.get("market").asText();
    }

    LOG.trace("GetChannelNameFromMessage: " + channelName);

    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    String channel = "";
    String market = "";

    if (authenticationMessage != null && !isLoggedIn) {
      sendObjectMessage(authenticationMessage);
      isLoggedIn = true;
    }

    if (channelName.contains("orderbook")) {
      channel = channelName.substring(0, channelName.indexOf(":"));
      market = channelName.substring(channelName.indexOf(":") + 1);
    } else if (channelName.contains("fills")) {
      channel = "fills";
      market = null;
    }

    LOG.trace("GetSubscribeMessage channel: " + channel);
    LOG.trace("GetSubscribeMessage market: " + market);

    return mapper.writeValueAsString(new FtxStreamRequest(channel, market, "subscribe"));
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    String channel = "";
    String market = "";

    if (channelName.contains("orderbook")) {
      channel = channelName.substring(0, channelName.indexOf(":"));
      market = channelName.substring(channelName.indexOf(":") + 1);
    } else if (channelName.contains("fills")) {
      channel = "fills";
      market = null;
    }

    if (authenticationMessage != null && isLoggedIn) {
      isLoggedIn = false;
    }

    LOG.trace("GetSubscribeMessage channel: " + channel);
    LOG.trace("GetSubscribeMessage market: " + market);

    return objectMapper.writeValueAsString(new FtxStreamRequest(channel, market, "unsubscribe"));
  }
}
