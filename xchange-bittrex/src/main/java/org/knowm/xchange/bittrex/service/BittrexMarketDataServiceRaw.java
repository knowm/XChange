package org.knowm.xchange.bittrex.service;

import static org.knowm.xchange.bittrex.BittrexResilience.GET_ORDER_BOOKS_RATE_LIMITER;
import static org.knowm.xchange.bittrex.BittrexResilience.PUBLIC_ENDPOINTS_RATE_LIMITER;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.client.ResilienceRegistries;
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
  public BittrexMarketDataServiceRaw(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bittrex, resilienceRegistries);
  }

  public List<BittrexSymbol> getBittrexSymbols() throws IOException {
    return decorateApiCall(bittrexAuthenticated::getMarkets)
        .withRetry(retry("getMarkets"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public List<BittrexCurrency> getBittrexCurrencies() throws IOException {
    return decorateApiCall(bittrexAuthenticated::getCurrencies)
        .withRetry(retry("getCurrencies"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public BittrexMarketSummary getBittrexMarketSummary(String pair) throws IOException {
    return decorateApiCall(() -> bittrexAuthenticated.getMarketSummary(pair))
        .withRetry(retry("getMarkets"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public List<BittrexMarketSummary> getBittrexMarketSummaries() throws IOException {
    return decorateApiCall(bittrexAuthenticated::getMarketSummaries)
        .withRetry(retry("getMarketSummaries"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public BittrexTicker getBittrexTicker(String pair) throws IOException {
    return decorateApiCall(() -> bittrexAuthenticated.getTicker(pair))
        .withRetry(retry("getTicker"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public List<BittrexTicker> getBittrexTickers() throws IOException {
    return decorateApiCall(bittrexAuthenticated::getTickers)
        .withRetry(retry("getTickers"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  public SequencedOrderBook getBittrexSequencedOrderBook(String market, int depth)
      throws IOException {
    BittrexDepth bittrexDepth =
        decorateApiCall(() -> bittrexAuthenticated.getOrderBook(market, depth))
            .withRetry(retry("getOrderBook"))
            .withRateLimiter(rateLimiter(GET_ORDER_BOOKS_RATE_LIMITER))
            .call();

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
    return decorateApiCall(() -> bittrexAuthenticated.getTrades(pair))
        .withRetry(retry("getTrades"))
        .withRateLimiter(rateLimiter(PUBLIC_ENDPOINTS_RATE_LIMITER))
        .call();
  }

  @AllArgsConstructor
  @Getter
  public static class SequencedOrderBook {
    private final String sequence;
    private final OrderBook orderBook;
  }
}
