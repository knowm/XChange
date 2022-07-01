package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebSocketTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.List;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBookData;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTradeStatistics;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class CoinmateStreamingMarketDataService implements StreamingMarketDataService {
  private final CoinmateStreamingService coinmateStreamingService;

  CoinmateStreamingMarketDataService(CoinmateStreamingService coinmateStreamingService) {
    this.coinmateStreamingService = coinmateStreamingService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
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
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = "statistics-" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper().readerFor(CoinmateTradeStatistics.class);
    return coinmateStreamingService
        .subscribeChannel(channelName)
        .map(
            s -> {
              CoinmateTradeStatistics tradeStatistics = reader.readValue(s.get("payload"));
              return CoinmateAdapters.adaptTradeStatistics(tradeStatistics, currencyPair);
            });
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
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
