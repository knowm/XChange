package org.knowm.xchange.stream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import org.knowm.xchange.stream.core.ProductSubscription;
import org.knowm.xchange.stream.service.netty.JsonNettyStreamingService;
import java.io.IOException;

public class BinanceStreamingService extends JsonNettyStreamingService {

  private final ProductSubscription productSubscription;

  public BinanceStreamingService(String baseUri, ProductSubscription productSubscription) {
    super(baseUri, Integer.MAX_VALUE);
    this.productSubscription = productSubscription;
  }

  @Override
  public void messageHandler(String message) {
    super.messageHandler(message);
  }

  @Override
  protected void handleMessage(JsonNode message) {
    super.handleMessage(message);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return message.get("stream").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    // No op. Disconnecting from the web socket will cancel subscriptions.
    return null;
  }

  @Override
  public void sendMessage(String message) {
    // Subscriptions are made upon connection - no messages are sent.
  }

  /**
   * The available subscriptions for this streaming service.
   *
   * @return The subscriptions for the currently open connection.
   */
  public ProductSubscription getProductSubscription() {
    return productSubscription;
  }
}
