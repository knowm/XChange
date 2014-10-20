package com.xeiam.xchange.poloniex.service.polling;

/**
 * @author Zach Holmes
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexUserTrade;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class PoloniexTradeService extends PoloniexTradeServiceRaw implements PollingTradeService {

  public PoloniexTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HashMap<String, PoloniexOpenOrder[]> poloniexOpenOrders = returnOpenOrders();
    return PoloniexAdapters.adaptPoloniexOpenOrders(poloniexOpenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    if (limitOrder.getType() == OrderType.BID) {
      return buy(limitOrder);
    }
    else {
      return sell(limitOrder);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return cancel(orderId);
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CurrencyPair currencyPair = null;
    Long startTime = null;
    Long endTime = null;

    if (arguments != null) {
      switch (arguments.length) {
      case 3:
        if (arguments[2] != null && arguments[2] instanceof Long) {
          endTime = (Long) arguments[2];
        }
      case 2:
        if (arguments[1] != null && arguments[1] instanceof Long) {
          startTime = (Long) arguments[1];
        }
      case 1:
        if (arguments[0] != null && arguments[0] instanceof CurrencyPair) {
          currencyPair = (CurrencyPair) arguments[0];
        }
      }
    }
    if (currencyPair == null) {
      throw new ExchangeException("Poloniex requires a CurrencyPair for trade history");
    }

    PoloniexUserTrade[] poloniexUserTrades = null;
    if (startTime == null && endTime == null) {
      poloniexUserTrades = returnTradeHistory(currencyPair);
    }
    else {
      poloniexUserTrades = returnTradeHistory(currencyPair, startTime, endTime);
    }

    List<Trade> trades = new ArrayList<Trade>();
    for (PoloniexUserTrade poloniexUserTrade : poloniexUserTrades) {
      trades.add(PoloniexAdapters.adaptPoloniexUserTrade(poloniexUserTrade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

}
