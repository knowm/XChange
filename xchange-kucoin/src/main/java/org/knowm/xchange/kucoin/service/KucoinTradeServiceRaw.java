package org.knowm.xchange.kucoin.service;

import static org.knowm.xchange.kucoin.KucoinUtils.checkSuccess;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.KucoinException;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinActiveOrders;
import org.knowm.xchange.kucoin.dto.trading.KucoinDealtOrdersInfo;
import org.knowm.xchange.kucoin.dto.trading.KucoinOrder;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /** Places a limit order. */
  public KucoinResponse<KucoinOrder> placeKucoinLimitOrder(LimitOrder order) throws IOException {
    try {
      return checkSuccess(
          kucoin.order(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              KucoinAdapters.adaptCurrencyPair(order.getCurrencyPair()),
              KucoinOrderType.fromOrderType(order.getType()),
              order.getLimitPrice(),
              order.getOriginalAmount()));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  /** Cancels an order. */
  public KucoinResponse<KucoinOrder> cancelKucoinOrder(
      CurrencyPair currencyPair, String orderOid, OrderType orderType) throws IOException {
    try {
      return checkSuccess(
          kucoin.cancelOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              KucoinAdapters.adaptCurrencyPair(currencyPair),
              orderOid,
              KucoinOrderType.fromOrderType(orderType)));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  /** Lists all active orders for a currency pair. */
  public KucoinResponse<KucoinActiveOrders> getKucoinOpenOrders(
      CurrencyPair currencyPair, OrderType orderType) throws IOException {
    try {
      // keep orderType null for now, since setting it changes the response format
      return checkSuccess(
          kucoin.orderActive(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              KucoinAdapters.adaptCurrencyPair(currencyPair),
              null /*orderType*/));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  /** Returns the trade history. */
  KucoinResponse<KucoinDealtOrdersInfo> getKucoinTradeHistory(
      CurrencyPair currencyPair,
      OrderType orderType,
      Integer limit,
      Integer page,
      Date since,
      Date before)
      throws IOException {
    try {
      return checkSuccess(
          kucoin.orderDealt(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              currencyPair == null ? null : KucoinAdapters.adaptCurrencyPair(currencyPair),
              orderType == null ? null : KucoinOrderType.fromOrderType(orderType),
              limit,
              page,
              since == null ? null : since.getTime(),
              before == null ? null : before.getTime()));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
