package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexMarketDataServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexMarketDataServiceRaw(BittrexExchange exchange) {
    super(exchange);
  }

  public List<BittrexSymbol> getBittrexSymbols() throws IOException {
    return bittrexAuthenticated.getMarkets();
  }

  public BittrexMarketSummary getBittrexMarketSummary(String pair) throws IOException {
    return bittrexAuthenticated.getMarketSummary(pair);
  }

  public List<BittrexMarketSummary> getBittrexMarketSummaries() throws IOException {
    return bittrexAuthenticated.getMarketSummaries();
  }

  public BittrexTicker getBittrexTicker(String pair) throws IOException {
    return bittrexAuthenticated.getTicker(pair);
  }

  public List<BittrexTicker> getBittrexTickers() throws IOException {
    return bittrexAuthenticated.getTickers();
  }

  public SequencedOrderBook getBittrexSequencedOrderBook(String market, int depth)
      throws IOException {
    BittrexDepth bittrexDepth = bittrexAuthenticated.getOrderBook(market, depth);

    CurrencyPair currencyPair = BittrexUtils.toCurrencyPair(market);
    List<LimitOrder> asks =
        BittrexAdapters.adaptOrders(
            bittrexDepth.getAsks(), currencyPair, Order.OrderType.ASK, depth);
    List<LimitOrder> bids =
        BittrexAdapters.adaptOrders(
            bittrexDepth.getBids(), currencyPair, Order.OrderType.BID, depth);

    OrderBook orderBook = new OrderBook(null, asks, bids);
    return new SequencedOrderBook(bittrexDepth.getSequence(), orderBook);
  }

  public List<BittrexTrade> getBittrexTrades(String pair) throws IOException {
    return bittrexAuthenticated.getTrades(pair);
  }

  @AllArgsConstructor
  @Getter
  public static class SequencedOrderBook {
    private final String sequence;
    private final OrderBook orderBook;
  }
}
