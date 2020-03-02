package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebSocketTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import java.util.List;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.CoinmateUtils;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBook;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateOrderBookData;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class CoinmateStreamingMarketDataService implements StreamingMarketDataService {
  private final PusherStreamingService service;

  CoinmateStreamingMarketDataService(PusherStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = "order_book-" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    return service
        .subscribeChannel(channelName, "order_book")
        .map(
            s -> {
              ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
              CoinmateOrderBookData orderBookData =
                  mapper.readValue(s, CoinmateOrderBookData.class);
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
    String channelName = "trades-" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    return service
        .subscribeChannel(channelName, "new_trades")
        .map(
            s -> {
              ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
              List<CoinmateWebSocketTrade> list =
                  mapper.readValue(s, new TypeReference<List<CoinmateWebSocketTrade>>() {});
              return list;
            })
        .flatMapIterable(coinmateWebSocketTrades -> coinmateWebSocketTrades)
        .map(
            coinmateWebSocketTrade ->
                CoinmateAdapters.adaptTrade(
                    coinmateWebSocketTrade.toTransactionEntry(
                        CoinmateUtils.getPair(currencyPair))));
  }
}
