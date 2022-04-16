package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTradeWrapper;
import org.knowm.xchange.huobi.dto.marketdata.KlineInterval;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements MarketDataService {

  private static final int MAX_NUMBER_OF_KLINE_RETURNS = 2000;

  public HuobiMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return HuobiAdapters.adaptTicker(getHuobiTicker(currencyPair), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return HuobiAdapters.adaptAllTickers(getHuobiAllTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String depthType = "step0";

    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof String)) {
        throw new ExchangeException("Argument 0 must be an String!");
      } else {
        depthType = (String) arg0;
      }
    }
    HuobiDepth depth = getHuobiDepth(currencyPair, depthType);
    List<LimitOrder> bids =
        depth.getBids().entrySet().stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.BID, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        depth.getAsks().entrySet().stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.ASK, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(depth.getTs(), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    int size = 100;

    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer) || arg0 == null || (int) arg0 < 1 || (int) arg0 > 2000) {
        throw new ExchangeException("Argument 0 must be an Integer in the range [1, 2000]!");
      } else {
        size = (int) arg0;
      }
    }

    HuobiTradeWrapper[] huobiTrades = getHuobiTrades(currencyPair, size);
    List<Trade> trades =
        Arrays.asList(huobiTrades).stream()
            .map(t -> t.getData()[0])
            .map(
                t ->
                    new Trade.Builder()
                        .type(HuobiAdapters.adaptOrderType(t.getDirection()))
                        .originalAmount(t.getAmount())
                        .currencyPair(currencyPair)
                        .price(t.getPrice())
                        .timestamp(t.getTs())
                        .id(t.getId())
                        .build())
            .collect(Collectors.toList());

    return new Trades(trades);
  }

  @Override
  public CandleStickData getCandleStickData(CurrencyPair currencyPair, Date startDate, Date endDate, Object... args) throws IOException {
    String period = null;

    if (args != null) {
      if (args.length > 0) {
        if (args[0] != null && args[0] instanceof String) {
          period = (String) args[0];
        }
      }
    }

    long timeDifferenceInMilis = Math.abs(new Date().getTime() - startDate.getTime());
    int size = Math.min((int) (timeDifferenceInMilis / KlineInterval.valueOf(period).getMillis()), MAX_NUMBER_OF_KLINE_RETURNS);

    return HuobiAdapters.adaptCandleStickData(getKlines(currencyPair, KlineInterval.valueOf(period), size), currencyPair, endDate);
  }
}
