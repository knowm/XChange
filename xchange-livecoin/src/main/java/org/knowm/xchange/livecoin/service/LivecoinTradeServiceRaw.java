package org.knowm.xchange.livecoin.service;

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

public class LivecoinTradeServiceRaw extends LivecoinBaseService<Livecoin> {

  public LivecoinTradeServiceRaw(LivecoinExchange exchange) {
    super(Livecoin.class, exchange);
  }

  public List<LimitOrder> getOpenOrders(CurrencyPair currencyPair, Date issuedFrom, Date issuedTo, Long startRow, Long endRow) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    LivecoinPaginatedResponse response = service.clientOrders(apiKey, signatureCreator,
        currencyPair.toString(),
        "OPEN",
        DateUtils.toMillisNullSafe(issuedFrom),
        DateUtils.toMillisNullSafe(issuedTo),
        startRow,
        endRow
    );

    List<LimitOrder> resp = new ArrayList<>();
    if (response.data == null)
      return resp;

    for (Map map : response.data) {
      resp.add(LivecoinAdapters.adaptOpenOrder(map));
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
      response = service.buyWithMarketOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getTradableAmount());
    } else {
      response = service.sellWithMarketOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getTradableAmount());
    }

    return response.get("orderId").toString();
  }

  public String makeLimitOrder(LimitOrder order) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Map response;
    if (order.getType().equals(Order.OrderType.BID)) {
      response = service.buyWithLimitOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getLimitPrice(), order.getTradableAmount());
    } else {
      response = service.sellWithLimitOrder(apiKey, signatureCreator, order.getCurrencyPair().toString(), order.getLimitPrice(), order.getTradableAmount());
    }

    if (response.containsKey("success") && !Boolean.valueOf(response.get("success").toString()))
      throw new ExchangeException("Failed to place order " + response);

    return response.get("orderId").toString();
  }

  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return cancelOrder(new CancelOrderByIdParams(orderId));
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
