package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebSocketTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
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
  private final CoinmateStreamingService coinmateStreamingService;

  CoinmateStreamingMarketDataService(CoinmateStreamingService coinmateStreamingService) {
    this.coinmateStreamingService = coinmateStreamingService;
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = "order_book-" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper().readerFor(CoinmateOrderBookData.class);

    return coinmateStreamingService
        .subscribeChannel(channelName)
        .map(
            s -> {
              CoinmateOrderBookData orderBookData = reader.readValue(s.get("payload"));
              CoinmateOrderBook coinmateOrderBook =
                  new CoinmateOrderBook(false, null, orderBookData);

              return CoinmateAdapters.adaptOrderBook(coinmateOrderBook, currencyPair);
            });
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    // No live ticker
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = "trades-" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper()
            .readerFor(new TypeReference<List<CoinmateWebSocketTrade>>() {});

    return coinmateStreamingService
        .subscribeChannel(channelName)
        .map(s -> reader.<List<CoinmateWebSocketTrade>>readValue(s.get("payload")))
        .flatMapIterable(coinmateWebSocketTrades -> coinmateWebSocketTrades)
        .map(
            coinmateWebSocketTrade ->
                CoinmateStreamingAdapter.adaptTrade(coinmateWebSocketTrade, currencyPair));
  }
}
