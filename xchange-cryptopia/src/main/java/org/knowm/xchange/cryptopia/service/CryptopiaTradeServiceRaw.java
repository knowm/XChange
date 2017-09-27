package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaDigest;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.DateUtils;

import si.mazi.rescu.RestProxyFactory;

public class CryptopiaTradeServiceRaw {

  private final Cryptopia api;
  private final CryptopiaDigest signatureCreator;
  private final CryptopiaExchange exchange;

  public CryptopiaTradeServiceRaw(CryptopiaExchange exchange) {
    this.api = RestProxyFactory.createProxy(Cryptopia.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = CryptopiaDigest.createInstance(exchange.getNonceFactory(), exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification().getApiKey());
    this.exchange = exchange;
  }

  public List<LimitOrder> getOpenOrders(CurrencyPair currencyPair, Integer count) throws IOException {
    CryptopiaBaseResponse<List<Map>> response = api.getOpenOrders(signatureCreator, new Cryptopia.GetOpenOrdersRequest(currencyPair.toString(), count));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get open orders: " + response.toString());

    List<LimitOrder> results = new ArrayList<>();
    for (Map map : response.getData()) {

      Order.OrderType type = type(map);

      BigDecimal tradableAmount = new BigDecimal(map.get("Amount").toString());
      BigDecimal remaining = new BigDecimal(map.get("Remaining").toString());
      BigDecimal total = new BigDecimal(map.get("Total").toString());

      String id = map.get("OrderId").toString();
      Date timestamp = DateUtils.fromISO8601DateString(map.get("TimeStamp").toString());
      BigDecimal limitPrice = new BigDecimal(map.get("Rate").toString());
      BigDecimal averagePrice = null;
      BigDecimal cumulativeAmount = tradableAmount.subtract(remaining);
      Order.OrderStatus status = Order.OrderStatus.PENDING_NEW;

      results.add(new LimitOrder(
          type,
          tradableAmount,
          currencyPair,
          id,
          timestamp,
          limitPrice,
          averagePrice,
          cumulativeAmount,
          status
      ));
    }

    return results;
  }

  public String submitTrade(CurrencyPair currencyPair, LimitOrder.OrderType type, BigDecimal price, BigDecimal amount) throws IOException {
    String rawType = type.equals(Order.OrderType.BID) ? "Buy" : "Sell";

    CryptopiaBaseResponse<Map> response = api.submitTrade(signatureCreator, new Cryptopia.SubmitTradeRequest(currencyPair.toString(), rawType, price, amount));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to submit order: " + response.toString());

    List<Integer> filled = (List<Integer>) response.getData().get("FilledOrders");
    if (filled.isEmpty()) {
      return response.getData().get("OrderId").toString();
    } else {
      //if it fills instantly we don't get an orderId, so instead we return the 1st fillId...  far from perfect because there could be many
      return filled.get(0).toString();
    }
  }

  public boolean cancel(String orderId, CurrencyPair currencyPair) throws IOException {
    Long marketId = exchange.tradePairId(currencyPair);

    CryptopiaBaseResponse<List> response = api.cancelTrade(signatureCreator, new Cryptopia.CancelTradeRequest("All", orderId, marketId));

    if (!response.isSuccess())
      throw new ExchangeException("Failed to cancel order: " + response.toString());

    return !response.getData().isEmpty();
  }

  public List<UserTrade> tradeHistory(CurrencyPair currencyPair, Integer count) throws IOException {
    CryptopiaBaseResponse<List<Map>> response = api.getTradeHistory(signatureCreator, new Cryptopia.GetTradeHistoryRequest(currencyPair.toString(), count == null ? 100 : count));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get trade history: " + response.toString());

    List<UserTrade> results = new ArrayList<>();

    for (Map map : response.getData()) {
      Order.OrderType type = type(map);
      BigDecimal amount = new BigDecimal(map.get("Amount").toString());
      BigDecimal price = new BigDecimal(map.get("Rate").toString());
      Date timestamp = DateUtils.fromISO8601DateString(map.get("TimeStamp").toString());
      String id = map.get("TradeId").toString();
      BigDecimal fee = new BigDecimal(map.get("Fee").toString());
      String orderId = null;//todo: check this
      Currency feeCcy = null;

      results.add(new UserTrade(
          type,
          amount,
          currencyPair,
          price,
          timestamp,
          id,
          orderId,
          fee,
          feeCcy
      ));
    }

    return results;
  }

  private static Order.OrderType type(Map map) {
    return map.get("Type").toString().equals("Buy") ? Order.OrderType.BID : Order.OrderType.ASK;
  }
}
