package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.Asks;
import org.knowm.xchange.bitbns.dto.PdaxOrderBooks;
import org.knowm.xchange.bitbns.dto.PdaxTickerData;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class PdaxMarketDataService extends BitbnsMarketDataServiceRaw
    implements MarketDataService {

  public PdaxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    PdaxOrderBooks orderBook = getBitbnsOrderBook(currencyPair, args);
    List<LimitOrder> asks = new ArrayList<>();
    for (Asks e : orderBook.getData().getAsks()) {
    	asks.add(new LimitOrder(OrderType.BID, e.getAmount(), currencyPair, null, null, e.getPrice()));
    }

    List<LimitOrder> bids = new ArrayList<>();
    for (Asks e : orderBook.getData().getBids()) {
    	bids.add(new LimitOrder(OrderType.ASK, e.getAmount(), currencyPair, null, null, e.getPrice()));
    }

    return new OrderBook(new Date(orderBook.getData().getNow()), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

	PdaxTickerData bitbnsTicker = getBitbnsTicker(currencyPair, args);
    return new Ticker.Builder()
        .instrument(currencyPair)
        .high(bitbnsTicker.getData().getHigh().getValue())
        .low(bitbnsTicker.getData().getLow().getValue())
        .last(bitbnsTicker.getData().getLast().getValue())
        .open(bitbnsTicker.getData().getBuy().getValue())
        .volume(bitbnsTicker.getData().getVol().getValue())
        .build();
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
