package com.xeiam.xchange.examples.bitfinex.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.service.polling.BitfinexTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();

    raw(bfx);
  }

  private static void raw(Exchange bfx) throws IOException {

    BitfinexTradeServiceRaw tradeService = (BitfinexTradeServiceRaw) bfx.getPollingTradeService();
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).limitPrice(new BigDecimal("481.69"))
        .tradableAmount(new BigDecimal("0.001")).build();
    tradeService.placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.LIMIT, false);
  }
}
