package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.ftx.dto.FtxAuthenticationMessage;
import info.bitrich.xchangestream.ftx.dto.FtxStreamRequest;
import info.bitrich.xchangestream.ftx.dto.FtxWebsocketCredential;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import javax.crypto.Mac;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ftx.service.FtxDigest;
import org.knowm.xchange.utils.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtxStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(FtxStreamingService.class);
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private boolean isLoggedIn = false;
  private final Supplier<FtxWebsocketCredential> authData;

  public FtxStreamingService(String apiUrl) {
    super(apiUrl);
    this.authData = null;
  }

  public FtxStreamingService(String apiUrl, final Supplier<FtxWebsocketCredential> authData) {
    super(apiUrl);
    this.authData = authData;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  private FtxAuthenticationMessage getAuthMessage() {
    Mac mac = FtxDigest.createInstance(authData.get().getSecretKey()).getMac();

    try {
      Long nonce = System.currentTimeMillis();
      String message = nonce + "websocket_login";

      mac.update(message.getBytes(StandardCharsets.UTF_8));

      return new FtxAuthenticationMessage(
          new FtxAuthenticationMessage.FtxAuthenticationArgs(
              authData.get().getApiKey(),
              DigestUtils.bytesToHex(mac.doFinal()).toLowerCase(),
              nonce,
              authData.get().getUserName()));
    } catch (Exception e) {
      throw new ExchangeException("Digest encoding exception", e);
    }
  }

  @Override
  protected void handleMessage(JsonNode message) {
    if (message.hasNonNull("type")) {
      if ("error".equals(message.get("type").asText())) {
        setLoggedInToFalse();
      }
    }
    super.handleMessage(message);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    String channelName = "";
    String channel = message.get("channel") == null ? null : message.get("channel").asText();

    if ("fills".equals(channel) || "orders".equals(channel)) {
      channelName = channel;
    } else if (message.hasNonNull("market")) {
      channelName = channel + ":" + message.get("market").asText();
    }

    LOG.trace("GetChannelNameFromMessage: " + channelName);

    return channelName;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    String channel = "";
    String market = null;

    if (authData != null && !isLoggedIn) {
      FtxAuthenticationMessage message = getAuthMessage();
      LOG.info("Sending authentication message: " + message);
      sendObjectMessage(message);
      isLoggedIn = true;
    }

    if (channelName.contains(":")) {
      channel = channelName.substring(0, channelName.indexOf(":"));
      market = channelName.substring(channelName.indexOf(":") + 1);
    } else {
      channel = channelName;
    }

    LOG.debug("GetSubscribeMessage channel: " + channel);
    LOG.debug("GetSubscribeMessage market: " + market);

    return mapper.writeValueAsString(new FtxStreamRequest(channel, market, "subscribe"));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    String channel = "";
    String market = null;

    if (channelName.contains("orderbook")) {
      channel = channelName.substring(0, channelName.indexOf(":"));
      market = channelName.substring(channelName.indexOf(":") + 1);
    } else if (channelName.contains("orders")) {
      channel = "orders";
    } else if (channelName.contains("fills")) {
      channel = "fills";
    }

    setLoggedInToFalse();

    LOG.debug("GetUnSubscribeMessage channel: " + channel);
    LOG.debug("GetUnSubscribeMessage market: " + market);

    return objectMapper.writeValueAsString(new FtxStreamRequest(channel, market, "unsubscribe"));
  }

  @Override
  public void resubscribeChannels() {
    setLoggedInToFalse();
    super.resubscribeChannels();
  }

  private void setLoggedInToFalse() {
    if (authData != null && isLoggedIn) {
      isLoggedIn = false;
      LOG.info("IsLoggedIn is " + false);
    }
  }
}
