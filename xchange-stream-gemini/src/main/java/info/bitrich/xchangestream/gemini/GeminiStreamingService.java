package info.bitrich.xchangestream.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.BehaviorProcessor;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(GeminiStreamingService.class);

  private final String baseUri;

  private final Map<CurrencyPair, GeminiProductStreamingService> productStreamingServices =
      new ConcurrentHashMap<>();
  private final Map<CurrencyPair, Flowable<JsonNode>> productSubscriptions =
      new ConcurrentHashMap<>();

  private final FlowableProcessor<State> stateSubject = BehaviorProcessor.create();

  public GeminiStreamingService(String baseUri) {
    this.baseUri = baseUri;
  }

  public Flowable<JsonNode> subscribeChannel(CurrencyPair currencyPair, Object... args) {
    if (!productStreamingServices.containsKey(currencyPair)) {
      String symbolUri = baseUri + currencyPair.base.toString() + currencyPair.counter.toString();
      GeminiProductStreamingService productStreamingService =
          new GeminiProductStreamingService(symbolUri, currencyPair);
      productStreamingService.connect().blockingAwait();
      Flowable<JsonNode> productSubscription =
          productStreamingService.subscribeChannel(currencyPair.toString(), args);
      productStreamingServices.put(currencyPair, productStreamingService);
      productSubscriptions.put(currencyPair, productSubscription);

      productStreamingService.subscribeConnectionState().subscribe(stateSubject);
    }

    return productSubscriptions.get(currencyPair);
  }

  public boolean isAlive() {
    return productStreamingServices.values().stream().allMatch(ps -> ps.isSocketOpen());
  }

  public Flowable<State> connectionStateFlowable() {
    return stateSubject.publish(1).refCount();
  }
}
