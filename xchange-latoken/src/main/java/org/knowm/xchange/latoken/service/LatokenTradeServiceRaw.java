package org.knowm.xchange.latoken.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.latoken.LatokenAdapters;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.trade.LatokenCancelledOrders;
import org.knowm.xchange.latoken.dto.trade.LatokenNewOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderSide;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderStatus;
import org.knowm.xchange.latoken.dto.trade.LatokenTestOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenUserTrades;
import org.knowm.xchange.latoken.dto.trade.OrderSubclass;

public class LatokenTradeServiceRaw extends LatokenBaseService {

  protected LatokenTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Returns all currently open orders.
   *
   * @param pair
   * @param limit - optional (when {@code null}, default value of 50 is used)
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public List<LatokenOrder> getLatokenOpenOrders(CurrencyPair pair, Integer limit)
      throws LatokenException, IOException {

    return latoken.getOpenOrders(
        LatokenAdapters.toSymbol(pair),
        System.currentTimeMillis(),
        limit,
        super.apiKey,
        super.signatureCreator);
  }

  /**
   * Returns an order given its {@code orderId}.
   *
   * @param orderId
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public LatokenOrder getLatokenOrder(String orderId) throws LatokenException, IOException {

    return latoken.getOrder(orderId, super.apiKey, super.signatureCreator);
  }

  /**
   * Returns list of orders of a given {@link CurrencyPair pair} and {@link LatokenOrderStatus
   * status}. <br>
   * This includes both open-orders <b>and</b> order-history.
   *
   * @param pair
   * @param status
   * @param limit - optional (when {@code null}, default value of 100 is used)
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public List<LatokenOrder> getLatokenOrders(
      CurrencyPair pair, LatokenOrderStatus status, Integer limit)
      throws LatokenException, IOException {

    return latoken.getOrders(
        LatokenAdapters.toSymbol(pair),
        status.toString(),
        limit,
        super.apiKey,
        super.signatureCreator);
  }

  /**
   * Places a new {@link LimitOrder}.
   *
   * @param pair
   * @param clientOrderId
   * @param side
   * @param price
   * @param amount
   * @param timeAliveMillis - Indicates how long the request is valid in milliseconds (example:
   *     3000)
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  public LatokenNewOrder placeLatokenNewOrder(
      CurrencyPair pair,
      String clientOrderId,
      LatokenOrderSide side,
      BigDecimal price,
      BigDecimal amount)
      throws IOException, LatokenException {

    return latoken.newOrder(
        LatokenAdapters.toSymbol(pair),
        clientOrderId,
        side,
        price,
        amount,
        OrderSubclass.limit, // Only Limit is supported by Latoken
        System.currentTimeMillis(),
        "",
        super.apiKey,
        super.signatureCreator);
  }

  /**
   * Tests order placement.
   *
   * @param pair
   * @param clientOrderId
   * @param side
   * @param price
   * @param amount
   * @param timeAliveMillis
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  public LatokenTestOrder placeLatokenTestOrder(
      CurrencyPair pair,
      String clientOrderId,
      LatokenOrderSide side,
      BigDecimal price,
      BigDecimal amount)
      throws IOException, LatokenException {

    return latoken.testNewOrder(
        LatokenAdapters.toSymbol(pair),
        clientOrderId,
        side,
        price,
        amount,
        OrderSubclass.limit, // Only Limit is supported by Latoken
        System.currentTimeMillis(),
        "",
        super.apiKey,
        super.signatureCreator);
  }

  /**
   * Cancels an order given its {@code orderId}.
   *
   * @param orderId
   * @return
   * @throws IOException
   * @throws LatokenException
   */
  public LatokenOrder cancelLatokenOrder(String orderId) throws IOException, LatokenException {

    return latoken.cancelOrder(
        orderId, System.currentTimeMillis(), "", super.apiKey, super.signatureCreator);
  }

  /**
   * Cancels all orders of a given {@link CurrencyPair}.
   *
   * @param pair
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public LatokenCancelledOrders cancelLatokenOrders(CurrencyPair pair)
      throws LatokenException, IOException {

    return latoken.cancelAll(
        LatokenAdapters.toSymbol(pair),
        System.currentTimeMillis(),
        "",
        super.apiKey,
        super.signatureCreator);
  }

  /**
   * Returns all user-trades of a given {@link CurrencyPair}.
   *
   * @param pair
   * @param limit - optional (when {@code null}, default value of 10 is used)
   * @return
   * @throws LatokenException
   * @throws IOException
   */
  public LatokenUserTrades getLatokenUserTrades(CurrencyPair pair, Integer limit)
      throws LatokenException, IOException {

    return latoken.getUserTrades(
        LatokenAdapters.toSymbol(pair),
        System.currentTimeMillis(),
        limit,
        super.apiKey,
        super.signatureCreator);
  }
}
