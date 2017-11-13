package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingService.class);
  private final ObjectMapper mapper = new ObjectMapper();

  public BitmexStreamingService(String apiUrl) {
    super(apiUrl, Integer.MAX_VALUE);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    if (message.has("info") || message.has("success")) {
      return;
    }

    super.handleMessage(message);
  }

  public Observable<BitmexWebSocketTransaction> subscribeBitmexChannel(String channelName) {
    return subscribeChannel(channelName).map(s-> {
      BitmexWebSocketTransaction transaction = mapper.readValue(s.toString(), BitmexWebSocketTransaction.class);
      return transaction;})
            .share();
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String instrument = message.get("data").get(0).get("symbol").asText();
    String table = message.get("table").asText();
    return String.format("%s:%s", table, instrument);
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("subscribe", new String[]{channelName});
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    BitmexWebSocketSubscriptionMessage subscribeMessage = new BitmexWebSocketSubscriptionMessage("unsubscribe", new String[]{});
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(subscribeMessage);
  }
}
