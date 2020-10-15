package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.BItbnsOrderBooks;
import org.knowm.xchange.bitbns.dto.BitbnsOrderBook;
import org.knowm.xchange.bitbns.dto.BitbnsTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitbnsMarketDataService extends BitbnsMarketDataServiceRaw
    implements MarketDataService {

  public BitbnsMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    BItbnsOrderBooks bitbnsOrderBookBuy = getBitbnsOrderBookBuy(currencyPair, args);
    List<LimitOrder> bids = new ArrayList<>();
    for (BitbnsOrderBook e : bitbnsOrderBookBuy.getData()) {
      bids.add(
          new LimitOrder(
              OrderType.BID, new BigDecimal(e.getBtc()), currencyPair, null, null, e.getRate()));
    }

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e1) {

    }
    BItbnsOrderBooks bitbnsOrderBookSell = getBitbnsOrderBookSell(currencyPair, args);
    List<LimitOrder> asks = new ArrayList<>();
    for (BitbnsOrderBook e : bitbnsOrderBookSell.getData()) {
      asks.add(
          new LimitOrder(
              OrderType.ASK, new BigDecimal(e.getBtc()), currencyPair, null, null, e.getRate()));
    }

    return new OrderBook(new Date(), asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
    }

    BitbnsTicker bitbnsTicker = getBitbnsTicker(currencyPair, args);
    return new Ticker.Builder()
        .instrument(currencyPair)
        .high(bitbnsTicker.getHighest_buy_bid())
        .low(bitbnsTicker.getLowest_sell_bid())
        .last(bitbnsTicker.getLast_traded_price())
        .open(bitbnsTicker.getYes_price())
        .volume(bitbnsTicker.getVolume().getVolume())
        .build();
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
