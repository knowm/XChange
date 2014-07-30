package com.xeiam.xchange.btcchina;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trade;

public class BTCChinaAdaptersTest {

  @Test
  public void testAdaptTransaction() {

    BTCChinaTransaction btcChinaTransaction = new BTCChinaTransaction(12158242, // id
        "sellbtc", // type
        new BigDecimal("-0.37460000"), // btc_amount
        new BigDecimal("0.00000000"), // ltc_amount
        new BigDecimal("1420.09151800"), // cny_amount
        1402922707L, // id
        "btccny" // market
    );
    Trade trade = BTCChinaAdapters.adaptTransaction(btcChinaTransaction);

    assertEquals(OrderType.ASK, trade.getType());
    assertEquals(new BigDecimal("0.37460000"), trade.getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, trade.getCurrencyPair());
    assertEquals(new BigDecimal("3790.95"), trade.getPrice());
    assertEquals(1402922707000L, trade.getTimestamp().getTime());
    assertEquals("12158242", trade.getId());
  }

}
