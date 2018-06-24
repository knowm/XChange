package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements MarketDataService {

  public HuobiMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return HuobiAdapters.adaptTicker(getHuobiTicker(currencyPair), currencyPair);
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
        depth
            .getBids()
            .entrySet()
            .stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.BID, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        depth
            .getAsks()
            .entrySet()
            .stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.ASK, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }
}
