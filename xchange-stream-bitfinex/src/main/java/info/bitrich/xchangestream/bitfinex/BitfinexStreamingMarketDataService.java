package info.bitrich.xchangestream.bitfinex;

import static org.knowm.xchange.bitfinex.service.BitfinexAdapters.adaptOrderBook;
import static org.knowm.xchange.bitfinex.service.BitfinexAdapters.adaptTicker;
import static org.knowm.xchange.bitfinex.service.BitfinexAdapters.adaptTrades;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexOrderbook;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketOrderbookTransaction;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketSnapshotOrderbook;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketSnapshotTrades;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketTickerTransaction;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketTradesTransaction;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketUpdateOrderbook;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebsocketUpdateTrade;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;

/** Created by Lukas Zaoralek on 7.11.17. */
public class BitfinexStreamingMarketDataService implements StreamingMarketDataService {

  private final BitfinexStreamingService service;

  private final Map<CurrencyPair, BitfinexOrderbook> orderbooks = new HashMap<>();

  public BitfinexStreamingMarketDataService(BitfinexStreamingService service) {
    this.service = service;
  }

  private String pairToSymbol(CurrencyPair currencyPair) {
    return (currencyPair.getCounter() == Currency.USDT)
        ? ("t" + currencyPair.getBase().getCurrencyCode() + "UST")
        : ("t"
            + currencyPair.getBase().getCurrencyCode()
            + currencyPair.getCounter().getCurrencyCode());
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = "book";
    final String depth = args.length > 0 ? args[0].toString() : "100";
    String pair = pairToSymbol(currencyPair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    Observable<BitfinexWebSocketOrderbookTransaction> subscribedChannel =
        service
            .subscribeChannel(channelName, new Object[] {pair, "P0", depth})
            .map(
                s -> {
                  if (s.get(1).get(0).isArray())
                    return mapper.treeToValue(s, BitfinexWebSocketSnapshotOrderbook.class);
                  else return mapper.treeToValue(s, BitfinexWebSocketUpdateOrderbook.class);
                });

    return subscribedChannel.map(
        s -> {
          BitfinexOrderbook bitfinexOrderbook =
              s.toBitfinexOrderBook(orderbooks.getOrDefault(currencyPair, null));
          orderbooks.put(currencyPair, bitfinexOrderbook);
          return adaptOrderBook(bitfinexOrderbook.toBitfinexDepth(), currencyPair);
        });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = "ticker";

    String pair = pairToSymbol(currencyPair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    Observable<BitfinexWebSocketTickerTransaction> subscribedChannel =
        service
            .subscribeChannel(channelName, new Object[] {pair})
            .map(s -> mapper.treeToValue(s, BitfinexWebSocketTickerTransaction.class));

    return subscribedChannel.map(s -> adaptTicker(s.toBitfinexTicker(), currencyPair));
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = "trades";
    final String tradeType = args.length > 0 ? args[0].toString() : "te";

    String pair = pairToSymbol(currencyPair);
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    Observable<BitfinexWebSocketTradesTransaction> subscribedChannel =
        service
            .subscribeChannel(channelName, new Object[] {pair})
            .filter(s -> s.get(1).asText().equals(tradeType))
            .map(
                s -> {
                  if (s.get(1).asText().equals("te") || s.get(1).asText().equals("tu")) {
                    return mapper.treeToValue(s, BitfinexWebsocketUpdateTrade.class);
                  } else return mapper.treeToValue(s, BitfinexWebSocketSnapshotTrades.class);
                });

    return subscribedChannel.flatMapIterable(
        s -> {
          Trades adaptedTrades = adaptTrades(s.toBitfinexTrades(), currencyPair);
          return adaptedTrades.getTrades();
        });
  }
}
