package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.core.StreamingExchange.WS_CONNECTION_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_IDLE_TIMEOUT;
import static info.bitrich.xchangestream.core.StreamingExchange.WS_RETRY_DURATION;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.rxjava3.core.Observable;
import java.time.Duration;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceUserDataStreamingService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceUserDataStreamingService.class);

  public static BinanceUserDataStreamingService create(
      String baseUri, String listenKey, ExchangeSpecification exchangeSpecification) {

    return new BinanceUserDataStreamingService(baseUri + "ws/" + listenKey, exchangeSpecification);
  }

  private BinanceUserDataStreamingService(String url, ExchangeSpecification exchangeSpecification) {
    super(
        url,
        65536,
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT),
        (Duration) exchangeSpecification.getExchangeSpecificParametersItem(WS_RETRY_DURATION),
        (Integer) exchangeSpecification.getExchangeSpecificParametersItem(WS_IDLE_TIMEOUT));
  }

  public Observable<JsonNode> subscribeChannel(BinanceWebSocketTypes eventType) {
    return super.subscribeChannel(eventType.getSerializedValue());
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    super.messageHandler(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    try {
      super.handleMessage(message);
    } catch (Exception e) {
      LOG.error("Error handling message: " + message, e);
    }
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    return message.get("e").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  public void sendMessage(String message) {
    // Subscriptions are made upon connection - no messages are sent.
  }
}
