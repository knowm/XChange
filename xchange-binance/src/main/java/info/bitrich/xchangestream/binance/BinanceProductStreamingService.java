package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class BinanceProductStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(BinanceProductStreamingService.class);

  private final CurrencyPair currencyPair;

  public BinanceProductStreamingService(String symbolUrl, CurrencyPair currencyPair) {
    super(symbolUrl, Integer.MAX_VALUE);
    this.currencyPair = currencyPair;
  }

  @Override
  public void messageHandler(String message) {
    LOG.debug("Received message: {}", message);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;

    // Parse incoming message to JSON
    try {
      jsonNode = objectMapper.readTree(message);
    } catch (IOException e) {
      LOG.error("Error parsing incoming message to JSON: {}", message);
      return;
    }

    handleMessage(jsonNode);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return currencyPair.toString();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return "Subscribed to channel: " + channelName;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    return "Unsubscribed from channel: " + channelName;
  }
}
