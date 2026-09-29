package info.bitrich.xchangestream.cryptocom;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.cryptocom.CryptoComRequestIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the Crypto.com Exchange v1 WebSocket envelope shared by the public market-data feed and
 * the private user feed: {@code {"id","method","params":{"channels":[...]}}} subscribe/unsubscribe
 * requests, {@code {"id","method","code","result":{"channel","subscription","data":[...]}}} push
 * messages, and the {@code public/heartbeat} / {@code public/respond-heartbeat} keepalive that the
 * server requires every ~30 seconds or it closes the connection.
 */
public class CryptoComStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(CryptoComStreamingService.class);
  private static final String HEARTBEAT_METHOD = "public/heartbeat";
  private static final String HEARTBEAT_RESPONSE_METHOD = "public/respond-heartbeat";
  private static final Map<Class<?>, JavaType> LIST_TYPES = new ConcurrentHashMap<>();

  private final CryptoComRequestIdGenerator requestIdGenerator = new CryptoComRequestIdGenerator();

  public CryptoComStreamingService(String apiUrl) {
    super(apiUrl);
  }

  protected long nextRequestId() {
    return requestIdGenerator.next();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return buildSubscriptionMessage("subscribe", channelName);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return buildSubscriptionMessage("unsubscribe", channelName);
  }

  private String buildSubscriptionMessage(String method, String channelName) throws IOException {
    ObjectNode message = objectMapper.createObjectNode();
    message.put("id", nextRequestId());
    message.put("method", method);
    message.putObject("params").putArray("channels").add(channelName);
    return objectMapper.writeValueAsString(message);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    JsonNode subscription = message.at("/result/subscription");
    if (subscription.isMissingNode()) {
      throw new IOException("Message has no subscription channel: " + message);
    }
    return subscription.asText();
  }

  /** Converts the {@code result.data} array of a push message envelope, caching the list type. */
  public <T> List<T> extractData(JsonNode envelope, Class<T> elementType) {
    JsonNode data = envelope.at("/result/data");
    if (data.isMissingNode() || data.isNull()) {
      return Collections.emptyList();
    }
    JavaType listType =
        LIST_TYPES.computeIfAbsent(
            elementType,
            type -> objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    return objectMapper.convertValue(data, listType);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    String method = message.path("method").asText("");
    if (HEARTBEAT_METHOD.equals(method)) {
      respondToHeartbeat(message.path("id").asLong());
      return;
    }
    if (!message.has("result")) {
      int code = message.path("code").asInt(0);
      if (code != 0) {
        LOG.warn("Crypto.com WebSocket error response: {}", message);
      }
      // Otherwise a plain ack for subscribe/unsubscribe (and, in the private service,
      // public/auth) - no channel data to route.
      return;
    }
    super.handleMessage(message);
  }

  private void respondToHeartbeat(long id) {
    ObjectNode response = objectMapper.createObjectNode();
    response.put("id", id);
    response.put("method", HEARTBEAT_RESPONSE_METHOD);
    sendObjectMessage(response);
  }
}
