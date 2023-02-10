package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.GateioExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** Author: Max Gao (gaamox@tutanota.com) Created: 05-05-2021 */
public class GateioStreamingExchange extends GateioExchange implements StreamingExchange {
  private static final Logger LOG = LoggerFactory.getLogger(GateioStreamingExchange.class);

  private static final String V4_URL = "wss://api.gateio.ws/ws/v4/";

  private GateioStreamingService streamingService;
  private StreamingMarketDataService streamingMarketDataService;

  public GateioStreamingExchange() {}

  @Override
  public Completable connect(ProductSubscription... args) {
    if (args == null || args.length == 0)
      throw new UnsupportedOperationException("The ProductSubscription must be defined!");

    this.streamingService = new GateioStreamingService(V4_URL, exchangeSpecification);
    applyStreamingSpecification(getExchangeSpecification(), streamingService);
    List<CurrencyPair> tickerPairs = Arrays.stream(args)
            .map(ProductSubscription::getTicker)
            .flatMap(List::stream)
            .filter(instrument -> instrument instanceof CurrencyPair)
            .map(instrument -> (CurrencyPair) instrument)
            .collect(Collectors.toList());
    this.streamingMarketDataService = new GateioStreamingMarketDataService(streamingService, tickerPairs);

    streamingService.subscribeMultipleCurrencyPairs(args);
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    GateioStreamingService service = streamingService;
    streamingService = null;
    streamingMarketDataService = null;
    return service.disconnect();
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public boolean isAlive() {
    return streamingService != null && streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }
}
