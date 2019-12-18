package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.LivecoinPaginatedResponse;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinUserOrder;
import org.knowm.xchange.livecoin.dto.trade.LivecoinCancelResponse;
import org.knowm.xchange.livecoin.dto.trade.LivecoinOrderResponse;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinTradeServiceRaw extends LivecoinBaseService<Livecoin> {

  private static final long THIRTY_DAYS_MILLISECONDS = 2_592_000_000L;

  public LivecoinTradeServiceRaw(LivecoinExchange exchange) {
    super(Livecoin.class, exchange);
  }

  public LivecoinPaginatedResponse<LivecoinUserOrder> clientOrders(
      CurrencyPair currencyPair,
      String openClosed,
      Long issuedFrom,
      Long issuedTo,
      Long startRow,
      Long endRow)
      throws IOException {
    return service.clientOrders(
        apiKey,
        signatureCreator,
        Optional.ofNullable(currencyPair).map(CurrencyPair::toString).orElse(null),
        openClosed,
        issuedFrom,
        issuedTo,
        startRow,
        endRow);
  }

  public List<UserTrade> tradeHistory(Date startTime, Date endTime, Integer limit, Long offset)
      throws IOException {
    long end = DateUtils.toMillisNullSafe(endTime);
    // Livecoin API limitation: 30 days max period
    long start = Math.max(DateUtils.toMillisNullSafe(startTime), end - THIRTY_DAYS_MILLISECONDS);

    List<Map> response =
        service.transactions(
            apiKey,
            signatureCreator,
            String.valueOf(start),
            String.valueOf(end),
            "BUY,SELL",
            limit,
            offset);

    List<UserTrade> resp = new ArrayList<>();
    for (Map map : response) {
      UserTrade fundingRecord = LivecoinAdapters.adaptUserTrade(map);

      resp.add(fundingRecord);
    }

    return resp;
  }

  public String makeMarketOrder(MarketOrder order) throws IOException {
    LivecoinOrderResponse response;
    if (order.getType().equals(Order.OrderType.BID)) {
      response =
          service.buyWithMarketOrder(
              apiKey,
              signatureCreator,
              order.getCurrencyPair().toString(),
              order.getOriginalAmount());
    } else {
      response =
          service.sellWithMarketOrder(
              apiKey,
              signatureCreator,
              order.getCurrencyPair().toString(),
              order.getOriginalAmount());
    }
    return response.getOrderId();
  }

  public String makeLimitOrder(LimitOrder order) throws IOException {
    LivecoinOrderResponse response;
    if (order.getType().equals(Order.OrderType.BID)) {
      response =
          service.buyWithLimitOrder(
              apiKey,
              signatureCreator,
              order.getCurrencyPair().toString(),
              order.getLimitPrice(),
              order.getOriginalAmount());
    } else {
      response =
          service.sellWithLimitOrder(
              apiKey,
              signatureCreator,
              order.getCurrencyPair().toString(),
              order.getLimitPrice(),
              order.getOriginalAmount());
    }
    return response.getOrderId();
  }

  public boolean cancelOrder(CurrencyPair currencyPair, String orderId) throws IOException {
    LivecoinCancelResponse response =
        service.cancelLimitOrder(
            apiKey, signatureCreator, currencyPair.toString(), Long.valueOf(orderId));

    return response.isCancelled();
  }

  protected boolean isOrderOpen(LivecoinUserOrder order) {
    return Objects.equals(order.getOrderStatus(), "OPEN")
        || Objects.equals(order.getOrderStatus(), "PARTIALLY_FILLED");
  }

  public static class LiveCoinCancelOrderParams
      implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
    public final CurrencyPair currencyPair;
    public final String orderId;

    public LiveCoinCancelOrderParams(CurrencyPair currencyPair, String orderId) {
      this.currencyPair = currencyPair;
      this.orderId = orderId;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }
  }
}
