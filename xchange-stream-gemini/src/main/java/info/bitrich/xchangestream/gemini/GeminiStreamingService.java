package info.bitrich.xchangestream.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiStreamingService.class);

  private final String baseUri;

  private Map<CurrencyPair, GeminiProductStreamingService> productStreamingServices;
  private Map<CurrencyPair, Observable<JsonNode>> productSubscriptions;

  public GeminiStreamingService(String baseUri) {
    this.baseUri = baseUri;
    productStreamingServices = new HashMap<>();
    productSubscriptions = new HashMap<>();
  }

  public Observable<JsonNode> subscribeChannel(CurrencyPair currencyPair, Object... args) {
    if (!productStreamingServices.containsKey(currencyPair)) {
      String symbolUri = baseUri + currencyPair.base.toString() + currencyPair.counter.toString();
      GeminiProductStreamingService productStreamingService =
          new GeminiProductStreamingService(symbolUri, currencyPair);
      productStreamingService.connect().blockingAwait();
      Observable<JsonNode> productSubscription =
          productStreamingService.subscribeChannel(currencyPair.toString(), args);
      productStreamingServices.put(currencyPair, productStreamingService);
      productSubscriptions.put(currencyPair, productSubscription);
    }

    return productSubscriptions.get(currencyPair);
  }

  public boolean isAlive() {
    return productStreamingServices.values().stream().allMatch(ps -> ps.isSocketOpen());
  }
}
