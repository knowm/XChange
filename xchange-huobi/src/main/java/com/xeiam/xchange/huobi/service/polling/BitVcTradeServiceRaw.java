package com.xeiam.xchange.huobi.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import com.xeiam.xchange.huobi.dto.trade.HuobiOrder;
import com.xeiam.xchange.huobi.dto.trade.HuobiOrderResult;
import com.xeiam.xchange.huobi.dto.trade.HuobiPlaceOrderResult;
import com.xeiam.xchange.huobi.service.TradeServiceRaw;

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
