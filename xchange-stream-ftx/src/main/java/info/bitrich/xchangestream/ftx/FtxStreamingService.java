package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  public FtxStreamingService(String apiUrl) {
    super(apiUrl);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String channelName = message.get("channel").asText() + ":" + message.get("market").asText();
    LOG.trace("GetChannelNameFromMessage: " + channelName);

    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    String channel = channelName.substring(0, channelName.indexOf(":"));
    String market = channelName.substring(channelName.indexOf(":") + 1);
    LOG.trace("GetSubscribeMessage channel: " + channel);
    LOG.trace("GetSubscribeMessage market: " + market);

    return mapper.writeValueAsString(new FtxStreamRequest(channel, market, "subscribe"));
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    String channel = channelName.substring(0, channelName.indexOf(":"));
    String market = channelName.substring(channelName.indexOf(":") + 1);

    LOG.trace("GetUnsubscribeMessage channel: " + channel);
    LOG.trace("GetUnsubscribeMessage market: " + market);

    return objectMapper.writeValueAsString(new FtxStreamRequest(channel, market, "unsubscribe"));
  }
}
