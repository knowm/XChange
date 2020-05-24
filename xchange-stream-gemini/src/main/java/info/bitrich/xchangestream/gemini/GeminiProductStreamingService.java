package info.bitrich.xchangestream.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiProductStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiProductStreamingService.class);

  private final CurrencyPair currencyPair;

  public GeminiProductStreamingService(String symbolUrl, CurrencyPair currencyPair) {
    super(
        symbolUrl,
        Integer.MAX_VALUE,
        DEFAULT_CONNECTION_TIMEOUT,
        DEFAULT_RETRY_DURATION,
        DEFAULT_IDLE_TIMEOUT);
    this.currencyPair = currencyPair;
  }

  @Override
  public boolean processArrayMassageSeparately() {
    return false;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    return currencyPair.toString();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return null;
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    return null;
  }
}
