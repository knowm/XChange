package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
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
  
  /**
   * Places a limit order.
   */
  public KucoinResponse<KucoinOrder> placeKucoinLimitOrder(LimitOrder order) throws IOException {
    return kucoin.order(apiKey, exchange.getNonceFactory(), signatureCreator,
        KucoinAdapters.adaptCurrencyPair(order.getCurrencyPair()),
        KucoinOrderType.fromOrderType(order.getType()),
        order.getLimitPrice(),
        order.getOriginalAmount());
  }
  
  /**
   * Cancels an order.
   */
  public KucoinResponse<KucoinOrder> cancelKucoinOrder(CurrencyPair currencyPair, String orderOid,
      OrderType orderType) throws IOException {
    return kucoin.cancelOrder(apiKey, exchange.getNonceFactory(), signatureCreator,
        KucoinAdapters.adaptCurrencyPair(currencyPair), orderOid, KucoinOrderType.fromOrderType(orderType));
  }
  

  /**
   * Lists all active orders for a currency pair.
   */
  public KucoinResponse<KucoinActiveOrders> getKucoinOpenOrders(CurrencyPair currencyPair, OrderType orderType)
      throws IOException {
    return kucoin.orderActive(apiKey, exchange.getNonceFactory(), signatureCreator,
        KucoinAdapters.adaptCurrencyPair(currencyPair),
        orderType == null ? null : KucoinOrderType.fromOrderType(orderType));
  }
  

  /**
   * Returns the trade history.
   */
  KucoinResponse<KucoinDealtOrdersInfo> getKucoinTradeHistory(CurrencyPair currencyPair, OrderType orderType,
      Integer limit, Integer page, Date since, Date before)
      throws IOException {
    return kucoin.orderDealt(apiKey, exchange.getNonceFactory(), signatureCreator,
        currencyPair == null ? null : KucoinAdapters.adaptCurrencyPair(currencyPair),
        orderType == null ? null : KucoinOrderType.fromOrderType(orderType),
        limit,
        page,
        since == null ? null : since.getTime(),
        before == null ? null : before.getTime());
  }
}
