package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebSocketTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.List;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBookData;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class CoinmateStreamingMarketDataService implements StreamingMarketDataService {
  private final CoinmateStreamingServiceFactory serviceFactory;

  CoinmateStreamingMarketDataService(CoinmateStreamingServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "channel/order-book/" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper().readerFor(CoinmateOrderBookData.class);

    return serviceFactory
        .createConnection(channelName, false)
        .map(
            s -> {
              CoinmateOrderBookData orderBookData = reader.readValue(s);
              CoinmateOrderBook coinmateOrderBook =
                  new CoinmateOrderBook(false, null, orderBookData);

              return CoinmateAdapters.adaptOrderBook(coinmateOrderBook, currencyPair);
            });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    // No live ticker
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "channel/trades/" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper()
            .readerFor(new TypeReference<List<CoinmateWebSocketTrade>>() {});

    return serviceFactory
        .createConnection(channelName, false)
        .map(s -> reader.<List<CoinmateWebSocketTrade>>readValue(s))
        .flatMapIterable(coinmateWebSocketTrades -> coinmateWebSocketTrades)
        .map(
            coinmateWebSocketTrade ->
                CoinmateStreamingAdapter.adaptTrade(coinmateWebSocketTrade, currencyPair));
  }
}
