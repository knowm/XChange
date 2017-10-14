package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LivecoinTradeServiceRaw extends LivecoinBaseService<Livecoin> {

  public LivecoinTradeServiceRaw(LivecoinExchange exchange) {
    super(Livecoin.class, exchange);
  }

  public List<LimitOrder> getAllOpenOrders() throws
      ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    LivecoinPaginatedResponse response = service.allClientOrders(apiKey, signatureCreator, "OPEN");

    List<LimitOrder> resp = new ArrayList<>();
    if (response.data == null)
      return resp;

    for (Map map : response.data) {
      Object statusRaw = map.get("orderStatus");
      if (statusRaw != null && (statusRaw.toString().equals("OPEN") || statusRaw.toString().equals("PARTIALLY_FILLED"))) {
        resp.add(LivecoinAdapters.adaptOpenOrder(map));
      }
    }
    return resp;
  }

  public List<UserTrade> tradeHistory(Date start, Date end, Integer limit, Long offset) throws IOException {
    List<Map> response = service.transactions(
        apiKey,
        signatureCreator,
        String.valueOf(DateUtils.toMillisNullSafe(start)),
        String.valueOf(DateUtils.toMillisNullSafe(end)),
        "BUY,SELL",
        limit,
        offset
    );

//        if (!response.success)
//            throw new ExchangeException("Failed to get trade history: " + response.errorMessage);

    List<UserTrade> resp = new ArrayList<>();
    for (Map map : response) {
      UserTrade fundingRecord = LivecoinAdapters.adaptUserTrade(map);

      resp.add(fundingRecord);
    }

    return resp;
  }

  public String makeMarketOrder(MarketOrder order) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Map response;
    if (order.getType().equals(Order.OrderType.BID)) {
      response = service.buyWithMarketOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getOriginalAmount());
    } else {
      response = service.sellWithMarketOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getOriginalAmount());
    }

    return response.get("orderId").toString();
  }

  public String makeLimitOrder(LimitOrder order) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Map response;
    if (order.getType().equals(Order.OrderType.BID)) {
      response = service.buyWithLimitOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getLimitPrice(), order.getOriginalAmount());
    } else {
      response = service.sellWithLimitOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getLimitPrice(), order.getOriginalAmount());
    }

    if (response.containsKey("success") && !Boolean.valueOf(response.get("success").toString()))
      throw new ExchangeException("Failed to place order " + response);

    return response.get("orderId").toString();
  }

  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException,
      IOException {
    return cancelOrder(new CancelOrderByIdParams(orderId));
  }

  public boolean cancelOrder(CurrencyPair currencyPair, String orderId) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException,
      IOException {
    return cancelOrder(new LiveCoinCancelOrderParams(currencyPair, orderId));
  }

  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof LiveCoinCancelOrderParams) {
      LiveCoinCancelOrderParams params = (LiveCoinCancelOrderParams) orderParams;
      Map response = service.cancelLimitOrder(apiKey, signatureCreator, params.currencyPair.toString(), Long.valueOf(params.getOrderId()));

      if (response.containsKey("success") && !Boolean.valueOf(response.get("success").toString()))
        throw new ExchangeException("Failed to cancel order " + response);

      return Boolean.valueOf(response.get("cancelled").toString());
    } else {
      throw new IllegalStateException("Don't understand " + orderParams);
    }
  }

  public static class LiveCoinCancelOrderParams extends CancelOrderByIdParams {
    public final CurrencyPair currencyPair;

    public LiveCoinCancelOrderParams(CurrencyPair currencyPair, String orderId) {
      super(orderId);
      this.currencyPair = currencyPair;
    }
  }
}
