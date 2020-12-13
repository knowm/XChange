package info.bitrich.xchangestream.bitflyer;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitflyer.dto.*;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.pubnub.PubnubStreamingService;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 14.11.17. */
public class BitflyerStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG =
      LoggerFactory.getLogger(BitflyerStreamingMarketDataService.class);

  private final PubnubStreamingService streamingService;

  private final Map<CurrencyPair, BitflyerOrderbook> orderbooks = new HashMap<>();
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BitflyerStreamingMarketDataService(PubnubStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelOrderbookSnapshotName =
        "lightning_board_snapshot_"
            + currencyPair.base.toString()
            + "_"
            + currencyPair.counter.toString();
    String channelOrderbookUpdatesName =
        "lightning_board_" + currencyPair.base.toString() + "_" + currencyPair.counter.toString();

    Observable<BitflyerOrderbook> snapshotTransactions =
        streamingService
            .subscribeChannel(channelOrderbookSnapshotName)
            .map(
                s -> {
                  BitflyerPubNubOrderbookTransaction transaction =
                      mapper.treeToValue(s, BitflyerPubNubOrderbookTransaction.class);
                  BitflyerOrderbook bitflyerOrderbook =
                      transaction.toBitflyerOrderbook(currencyPair);
                  orderbooks.put(currencyPair, bitflyerOrderbook);
                  return bitflyerOrderbook;
                });

    Observable<BitflyerOrderbook> updateTransactions =
        streamingService
            .subscribeChannel(channelOrderbookUpdatesName)
            .filter(s -> orderbooks.containsKey(currencyPair))
            .map(
                s -> {
                  BitflyerOrderbook bitflyerOrderbook = orderbooks.get(currencyPair);
                  BitflyerPubNubOrderbookTransaction transaction =
                      mapper.treeToValue(s, BitflyerPubNubOrderbookTransaction.class);
                  BitflyerLimitOrder[] asks = transaction.getAsks();
                  BitflyerLimitOrder[] bids = transaction.getBids();
                  bitflyerOrderbook.updateLevels(asks, Order.OrderType.ASK);
                  bitflyerOrderbook.updateLevels(bids, Order.OrderType.BID);
                  return bitflyerOrderbook;
                });

    return updateTransactions.mergeWith(snapshotTransactions).map(BitflyerOrderbook::toOrderBook);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "lightning_ticker_" + currencyPair.base.toString() + "_" + currencyPair.counter.toString();
    Observable<BitflyerTicker> tickerTransactions =
        streamingService
            .subscribeChannel(channelName)
            .map(
                s -> {
                  BitflyerPubNubTickerTransaction transaction =
                      mapper.treeToValue(s, BitflyerPubNubTickerTransaction.class);
                  return transaction.toBitflyerTicker();
                });

    return tickerTransactions.map(BitflyerTicker::toTicker);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "lightning_executions_"
            + currencyPair.base.toString()
            + "_"
            + currencyPair.counter.toString();
    Observable<BitflyerTrade> tradeTransactions =
        streamingService
            .subscribeChannel(channelName)
            .flatMapIterable(
                s -> {
                  BitflyerPubNubTradesTransaction transaction =
                      new BitflyerPubNubTradesTransaction(s);
                  return transaction.toBitflyerTrades();
                });

    return tradeTransactions.map(s -> s.toTrade(currencyPair));
  }
}
