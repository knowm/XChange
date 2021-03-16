package info.bitrich.xchangestream.poloniex2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.ConnectionStateModel.State;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

/** Created by Lukas Zaoralek on 10.11.17. */
public class PoloniexStreamingExchange extends PoloniexExchange implements StreamingExchange {
  private static final String API_URI = "wss://api2.poloniex.com";
  private static final String TICKER_URL = "https://poloniex.com/public?command=returnTicker";

  private final PoloniexStreamingService streamingService;
  private PoloniexStreamingMarketDataService streamingMarketDataService;

  public PoloniexStreamingExchange() {
    this.streamingService = new PoloniexStreamingService(API_URI);
  }

  @Override
  protected void initServices() {
    applyStreamingSpecification(getExchangeSpecification(), streamingService);
    super.initServices();
    Map<Integer, CurrencyPair> currencyPairMap = getCurrencyPairMap();
    streamingMarketDataService =
        new PoloniexStreamingMarketDataService(streamingService, currencyPairMap);
  }

  private Map<Integer, CurrencyPair> getCurrencyPairMap() {
    Map<Integer, CurrencyPair> currencyPairMap = new HashMap<>();
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    try {
      URL tickerUrl = new URL(TICKER_URL);
      JsonNode jsonRootTickers = mapper.readTree(tickerUrl);
      Iterator<String> pairSymbols = jsonRootTickers.fieldNames();
      pairSymbols.forEachRemaining(
          pairSymbol -> {
            String id = jsonRootTickers.get(pairSymbol).get("id").toString();
            String[] currencies = pairSymbol.split("_");
            CurrencyPair currencyPair =
                new CurrencyPair(new Currency(currencies[1]), new Currency(currencies[0]));
            currencyPairMap.put(Integer.valueOf(id), currencyPair);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }

    return currencyPairMap;
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return streamingService.connect();
  }

  @Override
  public Completable disconnect() {
    return streamingService.disconnect();
  }

  @Override
  public Flowable<Object> connectionIdle() {
    return streamingService.subscribeIdle();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = super.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(false);

    return spec;
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public boolean isAlive() {
    return streamingService.isSocketOpen();
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    streamingService.useCompressedMessages(compressedMessages);
  }

  @Override
  public Flowable<Object> connectionSuccess() {
    return streamingService.subscribeConnectionSuccess();
  }

  @Override
  public Flowable<Throwable> reconnectFailure() {
    return streamingService.subscribeReconnectFailure();
  }

  @Override
  public Flowable<State> connectionStateFlowable() {
    return streamingService.subscribeConnectionState();
  }
}
