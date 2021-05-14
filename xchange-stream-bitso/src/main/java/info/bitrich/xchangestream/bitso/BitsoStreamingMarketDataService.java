package info.bitrich.xchangestream.bitso;

import info.bitrich.xchangestream.bitso.dto.BitsoTrades;
import info.bitrich.xchangestream.bitso.dto.BitsoTradsTransaction;
import info.bitrich.xchangestream.bitso.dto.BitsoWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;

/** Created by luca on 4/3/17. */
public class BitsoStreamingMarketDataService implements StreamingMarketDataService {

  private static final String SNAPSHOT = "snapshot";
  private static final String L2UPDATE = "l2update";
  private static final String TICKER = "ticker";
  private static final String MATCH = "trades";

  private final BitsoStreamingService service;

  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> bids = new ConcurrentHashMap<>();
  private final Map<CurrencyPair, SortedMap<BigDecimal, BigDecimal>> asks = new ConcurrentHashMap<>();

  BitsoStreamingMarketDataService(BitsoStreamingService service) {
    this.service = service;
  }

  private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
    for (CurrencyPair item : pairs) {
      if (item.compareTo(pair) == 0) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getOrderBook(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orderbook", currencyPair));
    final int maxDepth =
        (args.length > 0 && args[0] instanceof Number) ? ((Number) args[0]).intValue() : 100;
    return getRawWebSocketTransactions(currencyPair, "orders")
        .filter(message -> ("orders").equals(message.getEventType()))
        .map(
            s -> {
              if (s.getEventType().equals("orders")) {
                bids.put(currencyPair, new TreeMap<>(java.util.Collections.reverseOrder()));
                asks.put(currencyPair, new TreeMap<>());
              } else {
                bids.computeIfAbsent(
                    currencyPair, k -> new TreeMap<>(java.util.Collections.reverseOrder()));
                asks.computeIfAbsent(currencyPair, k -> new TreeMap<>());
              }
              return s.toOrderBook(
                  bids.get(currencyPair), asks.get(currencyPair), maxDepth, currencyPair);
            });
  }

//  /**
//   * Returns an Observable of {@link BitsoProductTicker}, not converted to {@link Ticker}
//   *
//   * @param currencyPair the currency pair.
//   * @param args optional arguments.
//   * @return an Observable of {@link CoinbaseProProductTicker}.
//   */
//  public Observable<CoinbaseProProductTicker> getRawTicker(
//      CurrencyPair currencyPair, Object... args) {
//    if (!containsPair(service.getProduct().getTicker(), currencyPair))
//      throw new UnsupportedOperationException(
//          String.format("The currency pair %s is not subscribed for ticker", currencyPair));
//    return getRawWebSocketTransactions(currencyPair, true)
//        .filter(message -> (TICKER).equals(message.getType()))
//        .map(CoinbaseProWebSocketTransaction::toCoinbaseProProductTicker);
//  }

  /**
   * Returns the CoinbasePro ticker converted to the normalized XChange object. CoinbasePro does not
   * directly provide ticker data via web service. As stated by:
   * https://docs.coinbasepro.com/#get-product-ticker, we can just listen for 'match' messages.
   *
   * @param currencyPair Currency pair of the ticker
   * @param args optional parameters.
   * @return an Observable of normalized Ticker objects.
   */
  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
//    if (!containsPair(service.getProduct().getTicker(), currencyPair))
//      throw new UnsupportedOperationException(
//          String.format("The currency pair %s is not subscribed for ticker", currencyPair));
//    return getRawWebSocketTransactions(currencyPair, true)
//        .filter(message -> (TICKER).equals(message.getType()))
//        .map(
//            s ->
//                adaptTicker(
//                    s.toCoinbaseProProductTicker(), s.toCoinbaseProProductStats(), currencyPair));
    return null;
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getTrades(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for trades", currencyPair));
//    return getRawWebSocketTransactions(currencyPair, "trades")
    return getRawTradesSocketTransactions(currencyPair, "trades")
        .filter(message -> (MATCH).equals(message.getEventType()))
        .filter((BitsoTradsTransaction s) -> !ObjectUtils.isEmpty(s.getPayload()))
        .map(s -> s.toBitsoTrade())
        .map((BitsoTrades t) -> BitsoStreamingAdapters.adaptTrades(new BitsoTrades[] {t}, currencyPair))
        .map((Trades h) -> h.getTrades().get(0));
  }

  /**
   * Web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<BitsoWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, Object... args) {
    return service.getRawWebSocketTransactions(currencyPair, args);
  }

  /**
   * Web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<BitsoTradsTransaction> getRawTradesSocketTransactions(
          CurrencyPair currencyPair, Object... args) {
    return service.getRawTradesTransactions(currencyPair, args);
  }



}
