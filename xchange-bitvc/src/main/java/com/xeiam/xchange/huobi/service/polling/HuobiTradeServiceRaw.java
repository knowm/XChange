package com.xeiam.xchange.huobi.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;
import com.xeiam.xchange.dto.Order.OrderType;


public class HuobiTradeServiceRaw extends HuobiBaseTradeService implements TradeServiceRaw {
  public HuobiTradeServiceRaw(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);  
  }
  
  public BitVcOrder[] getBitVcOrders(int coinType) throws IOException {
    return huobi.getOrders(accessKey, coinType, nextCreated(), "get_orders", digest);
  }
  
  public BitVcPlaceOrderResult placeBitVcLimitOrder(OrderType type, int coinType, BigDecimal price, BigDecimal amount) throws IOException {
    String method = type == BID ? "buy" : "sell";
   
    return huobi.placeLimitOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), price.toPlainString(), method, digest);
  }
  
  public BitVcPlaceOrderResult placeBitVcMarketOrder(OrderType type, int coinType, BigDecimal amount) throws IOException {
    final String method = type == BID ? "buy_market" : "sell_market";

    return huobi.placeMarketOrder(accessKey, amount.toPlainString(), coinType, nextCreated(), method, digest);
  }
  
  public BitVcCancelOrderResult cancelBitVcOrder(int coinType, long id) throws IOException {
    return huobi.cancelOrder(accessKey, coinType, nextCreated(), id, "cancel_order", digest);
  }
}