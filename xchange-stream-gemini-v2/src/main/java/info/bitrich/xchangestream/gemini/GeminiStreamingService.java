package info.bitrich.xchangestream.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.gemini.dto.GeminiWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.gemini.dto.GeminiWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.netty.util.internal.StringUtil;
import io.reactivex.rxjava3.core.Flowable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Adapted from V1 by Max Gao on 01-09-2021 */
public class GeminiStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiStreamingService.class);
  private static final String SHARE_CHANNEL_NAME = "ALL";
  private static final String SUBSCRIBE = "subscribe";
  private static final String UNSUBSCRIBE = "unsubscribe";

  private final Map<String, Flowable<JsonNode>> subscriptions = new ConcurrentHashMap<>();
  private ProductSubscription product = null;

  public GeminiStreamingService(String baseUri) {
    super(baseUri, Integer.MAX_VALUE);
  }

  public ProductSubscription getProduct() {
    return this.product;
  }

  public Flowable<GeminiWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, boolean filterChannelName) {
    String channelName = currencyPair.base.toString() + currencyPair.counter.toString();
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return subscribeChannel(channelName)
        .map(s -> mapper.readValue(s.toString(), GeminiWebSocketTransaction.class))
        .filter(t -> channelName.equals(t.getSymbol()))
        .filter(t -> !StringUtil.isNullOrEmpty(t.getType()));
  }

  public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
    this.product = products[0];
  }

  @Override
  public Flowable<JsonNode> subscribeChannel(String channelName, Object... args) {
    channelName = SHARE_CHANNEL_NAME;

    if (!channels.containsKey(channelName) && !subscriptions.containsKey(channelName)) {
      subscriptions.put(channelName, super.subscribeChannel(channelName, args));
    }

    return subscriptions.get(channelName);
  }

  @Override
  public boolean processArrayMessageSeparately() {
    return false;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return SHARE_CHANNEL_NAME;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new GeminiWebSocketSubscriptionMessage(SUBSCRIBE, product));
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    return objectMapper.writeValueAsString(
        new GeminiWebSocketSubscriptionMessage(UNSUBSCRIBE, product));
  }
}
