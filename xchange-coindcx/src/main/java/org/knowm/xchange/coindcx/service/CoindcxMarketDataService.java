package org.knowm.xchange.coindcx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.CoindcxAdapters;
import org.knowm.xchange.coindcx.dto.CoindcxOrderBook;
import org.knowm.xchange.coindcx.dto.CoindcxTickersResponse;
import org.knowm.xchange.coindcx.dto.CoindcxTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class CoindcxMarketDataService extends CoindcxMarketDataServiceRaw
    implements MarketDataService {

  public CoindcxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    CoindcxOrderBook coindcxOrderBook = getCoindcxOrderBook(currencyPair, args);

    List<LimitOrder> bids =
        coindcxOrderBook.getBids().entrySet().stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.BID, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        coindcxOrderBook.getAsks().entrySet().stream()
            .map(
                e ->
                    new LimitOrder(
                        OrderType.ASK, e.getValue(), currencyPair, null, null, e.getKey()))
            .collect(Collectors.toList());

    return new OrderBook(new Date(), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    List<CoindcxTrade> coindcxTrades = getCoindcxTrades(currencyPair, args);
    List<Trade> trades = new ArrayList<>();

    for (CoindcxTrade coindcxTrade : coindcxTrades) {
      if (coindcxTrade.isMaker()) {

        trades.add(
            new Trade.Builder()
                .type(null)
                .originalAmount(coindcxTrade.getQuantity())
                .currencyPair(currencyPair)
                .price(coindcxTrade.getPrice())
                .timestamp(new Date(coindcxTrade.getTimestamp()))
                .id(null)
                .build());
      }
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    List<CoindcxTickersResponse> coindcxTickersResponses = getCoindcxTicker();
    return CoindcxAdapters.adaptTickerBasedOnCurencyPair(coindcxTickersResponses,currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
