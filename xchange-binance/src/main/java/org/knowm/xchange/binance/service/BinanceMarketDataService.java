package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRates;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw
    implements MarketDataService {

  public BinanceMarketDataService(
      BinanceExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      if (getSystemStatus().getStatus().equals("0")) {
        return ExchangeHealth.ONLINE;
      }
    } catch (IOException e) {
      return ExchangeHealth.OFFLINE;
    }
    return ExchangeHealth.OFFLINE;
  }

  /**
   * optional parameters provided in the args array:
   *
   * <ul>
   *   <li>0: Long fromId optional, ID to get aggregate trades from INCLUSIVE.
   *   <li>1: Long startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   *   <li>2: Long endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   *   <li>3: Integer limit optional, Default 500; max 500.
   * </ul>
   *
   * <p>
   */
  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return getBinanceTrades(instrument, args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      return BinanceAdapters.toTicker(
          ticker24hAllProducts(instrument), instrument instanceof FuturesContract);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      boolean isFutures = exchange.isFuturesEnabled();

      return ticker24hAllProducts(isFutures).stream()
          .filter(BinanceTicker24h::isValid)
          .map(binanceTicker24h -> BinanceAdapters.toTicker(binanceTicker24h, isFutures))
          .collect(Collectors.toList());
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return getBinanceOrderBook(instrument, args);
  }

  @Override
  public FundingRates getFundingRates() throws IOException {
    return BinanceAdapters.adaptFundingRates(getBinanceFundingRates());
  }

  @Override
  public FundingRate getFundingRate(Instrument instrument) throws IOException {
    return BinanceAdapters.adaptFundingRate(getBinanceFundingRate(instrument));
  }

  private Trades getBinanceTrades(Instrument instrument, Object... args) throws IOException {
    try {
      Long fromId = tradesArgument(args, 0, Long::valueOf);
      Long startTime = tradesArgument(args, 1, Long::valueOf);
      Long endTime = tradesArgument(args, 2, Long::valueOf);
      Integer limit = tradesArgument(args, 3, Integer::valueOf);

      return BinanceAdapters.adaptTrades(
          aggTradesAllProducts(instrument, fromId, startTime, endTime, limit), instrument);

    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  private OrderBook getBinanceOrderBook(Instrument instrument, Object... args) throws IOException {
    try {
      int limitDepth = 100;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Argument 0 must be an Integer!");
        } else {
          limitDepth = (Integer) arg0;
        }
      }
      BinanceOrderbook binanceOrderbook = getBinanceOrderbookAllProducts(instrument, limitDepth);
      return convertOrderBook(binanceOrderbook, instrument);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public static OrderBook convertOrderBook(BinanceOrderbook ob, Instrument pair) {
    List<LimitOrder> bids =
        ob.bids.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.asks.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(Date.from(Instant.now()), asks, bids);
  }

  private <T extends Number> T tradesArgument(
      Object[] args, int index, Function<String, T> converter) {
    if (index >= args.length) {
      return null;
    }
    Object arg = args[index];
    if (arg == null) {
      return null;
    }
    String argStr = arg.toString();
    try {
      return converter.apply(argStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Argument on index " + index + " is not a number: " + argStr, e);
    }
  }
}
