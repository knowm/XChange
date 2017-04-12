package info.bitrich.xchangestream.gdax;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gdax.dto.GDAXWebSocketTransaction;
import io.netty.util.internal.StringUtil;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptTicker;
import static org.knowm.xchange.gdax.GDAXAdapters.adaptTrades;

/**
 * Created by luca on 4/3/17.
 */
public class GDAXStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG = LoggerFactory.getLogger(GDAXStreamingMarketDataService.class);

  private final GDAXStreamingService service;

  GDAXStreamingMarketDataService(GDAXStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Returns an Observable of {@link GDAXProductTicker}, not converted to {@link Ticker}
   *
   * @param currencyPair the currency pair.
   * @param args         optional arguments.
   * @return an Observable of {@link GDAXProductTicker}.
   */
  public Observable<GDAXProductTicker> getRawTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
      .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

    return subscribedChannel
      .filter(message -> !isNullOrEmpty(message.getType()) && message.getType().equals("match"))
      .map(GDAXWebSocketTransaction::toGDAXProductTicker);
  }

  /**
   * Returns the GDAX ticker converted to the normalized XChange object.
   * GDAX does not directly provide ticker data via web service. 
   * As stated by: https://docs.gdax.com/#get-product-ticker, we can just listen for 'match' messages.
   * 
   * @param currencyPair Currency pair of the ticker
   * @param args optional parameters.
   * @return an Observable of normalized Ticker objects.
   */
  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
      .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

    return subscribedChannel
      .filter(message -> !isNullOrEmpty(message.getType()) && message.getType().equals("match"))
      .map(s -> adaptTicker(s.toGDAXProductTicker(),
        new GDAXProductStats(null, null, null, null), currencyPair));
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = currencyPair.base.toString() + "-" + currencyPair.counter.toString();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Observable<GDAXWebSocketTransaction> subscribedChannel = service.subscribeChannel(channelName)
      .map(s -> mapper.readValue(s.toString(), GDAXWebSocketTransaction.class));

    return subscribedChannel
      .map(s -> {
          Trades adaptedTrades = adaptTrades(new GDAXTrade[]{s.toGDAXTrade()}, currencyPair);
          return adaptedTrades.getTrades().get(0);
        }
      );
  }
}
