package com.xeiam.xchange.huobi.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;
import com.xeiam.xchange.dto.Order.OrderType;

public class HuobiTradeServiceRaw extends HuobiBaseTradeService implements TradeServiceRaw {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  @Override
  public BitVcOrder[] getBitVcOrders(int coinType) throws IOException {
    return huobi.getOrders(accessKey, coinType, nextCreated(), "get_orders", digest);
  }

  @Override
  public BitVcPlaceOrderResult placeBitVcLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException {
    String method = type == BID ? "buy" : "sell";

    return huobi.placeLimitOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), price.toPlainString(), method, digest);
  }

  @Override
  public BitVcPlaceOrderResult placeBitVcMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException {
    final String method = type == BID ? "buy_market" : "sell_market";

    return huobi.placeMarketOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), method, digest);
  }

  @Override
  public BitVcCancelOrderResult cancelBitVcOrder(int coinType, long id) throws IOException {
    return huobi.cancelOrder(accessKey, coinType, nextCreated(), id, "cancel_order", digest);
  }
}