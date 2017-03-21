package org.knowm.xchange.examples.bitfinex.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();

    raw(bfx);
  }

  private static void raw(Exchange bfx) throws IOException {

    BitfinexTradeServiceRaw tradeService = (BitfinexTradeServiceRaw) bfx.getTradeService();
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).limitPrice(new BigDecimal("481.69"))
        .tradableAmount(new BigDecimal("0.001")).build();
    tradeService.placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.LIMIT);
  }
}
