package org.knowm.xchange.huobi.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderInfo;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

public class BitVcTradeServiceRaw extends BitVcBaseTradeService implements TradeServiceRaw {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  @Override
  public HuobiOrder[] getOrders(int coinType) throws IOException {

    HuobiOrderResult orders = bitvc.getOrders(accessKey, coinType, nextCreated(), digest);

    return orders.getOrders();
  }

  @Override
  public HuobiOrderInfo getOrderInfo(long orderId, int coinType) throws NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }

  public HuobiOrder getBitVcOrder(int coinType, long id) throws IOException {
    return bitvc.getOrder(accessKey, coinType, nextCreated(), digest, id);
  }

  @Override
  public HuobiPlaceOrderResult placeLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException {

    final String method = type == BID ? "buy" : "sell";

    final HuobiPlaceOrderResult result;

    result = bitvc.placeLimitOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), price.toPlainString(), digest, method);
    return result;
  }

  @Override
  public HuobiPlaceOrderResult placeMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException {

    final String method = type == BID ? "buy_market" : "sell_market";

    final HuobiPlaceOrderResult result;

    result = bitvc.placeMarketOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), digest, method);

    return result;
  }

  @Override
  public HuobiCancelOrderResult cancelOrder(int coinType, long id) throws IOException {

    return bitvc.cancelOrder(accessKey, coinType, nextCreated(), id, digest, id);
  }
}
