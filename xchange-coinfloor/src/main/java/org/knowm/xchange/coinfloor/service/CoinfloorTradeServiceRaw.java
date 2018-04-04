package org.knowm.xchange.coinfloor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorMarketOrderResponse;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorOrder;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import si.mazi.rescu.HttpStatusIOException;

// Coinfloor errors do not appear to be JSON formatted, e.g. the user_transactions query will return
// an HTTP status of 400 with and HTTP body consisting of '"limit" parameter must be positive'.

public class CoinfloorTradeServiceRaw extends CoinfloorAuthenticatedService {

  protected CoinfloorTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinfloorUserTransaction[] getUserTransactions(
      CurrencyPair pair,
      Integer numberOfTransactions,
      Long offset,
      TradeHistoryParamsSorted.Order sort)
      throws IOException {
    try {
      return coinfloor.getUserTransactions(
          normalise(pair.base),
          normalise(pair.counter),
          numberOfTransactions,
          offset,
          sort == null ? null : sort.toString());
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
        throw new ExchangeException(e.getHttpBody(), e);
      } else {
        throw e;
      }
    }
  }

  public CoinfloorOrder[] getOpenOrders(CurrencyPair pair) throws IOException {
    try {
      return coinfloor.getOpenOrders(normalise(pair.base), normalise(pair.counter));
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
        throw new ExchangeException(e.getHttpBody(), e);
      } else {
        throw e;
      }
    }
  }

  public CoinfloorOrder placeLimitOrder(
      CurrencyPair pair, OrderType side, BigDecimal amount, BigDecimal price) throws IOException {
    Currency base = normalise(pair.base);
    Currency counter = normalise(pair.counter);

    try {
      if (side == OrderType.BID) {
        return coinfloor.buy(base, counter, amount, price);
      } else {
        return coinfloor.sell(base, counter, amount, price);
      }
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
        // e.g. if too many decimal places are specified in the quantity field the HTTP body
        // contains the message: "amount" parameter must not have more than 4 fractional digits
        throw new ExchangeException(e.getHttpBody(), e);
      } else {
        throw e;
      }
    }
  }

  public CoinfloorMarketOrderResponse placeMarketOrder(
      CurrencyPair pair, OrderType side, BigDecimal amount) throws IOException {
    Currency base = normalise(pair.base);
    Currency counter = normalise(pair.counter);
    try {
      if (side == OrderType.BID) {
        return coinfloor.buyMarket(base, counter, amount);
      } else {
        return coinfloor.sellMarket(base, counter, amount);
      }
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
        // e.g. if too many decimal places are specified in the quantity field the HTTP body
        // contains the message: "quantity" parameter must not have more than 4 fractional digits
        throw new ExchangeException(e.getHttpBody(), e);
      } else {
        throw e;
      }
    }
  }

  public boolean cancelOrder(CurrencyPair pair, long id) throws IOException {
    try {
      return coinfloor.cancelOrder(normalise(pair.base), normalise(pair.counter), id);
    } catch (HttpStatusIOException e) {
      if (e.getHttpStatusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
        throw new ExchangeException(e.getHttpBody(), e);
      } else {
        throw e;
      }
    }
  }
}
