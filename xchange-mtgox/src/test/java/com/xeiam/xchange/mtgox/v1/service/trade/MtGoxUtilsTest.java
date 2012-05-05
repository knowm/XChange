package com.xeiam.xchange.mtgox.v1.service.trade;

import static org.junit.Assert.assertEquals;

import org.joda.money.BigMoney;
import org.junit.Test;

import com.xeiam.xchange.mtgox.v1.MtGoxUtils;
import com.xeiam.xchange.utils.MoneyUtils;

public class MtGoxUtilsTest {

  @Test
  public void testJPYScaling() {

    BigMoney priceJPY = MoneyUtils.parseFiat("JPY 544.44");
    String mtGoxRequestStringJPY = MtGoxUtils.getPriceString(priceJPY);

    BigMoney priceUSD = MoneyUtils.parseFiat("USD 5.4444");
    String mtGoxRequestStringUSD = MtGoxUtils.getPriceString(priceUSD);

    assertEquals("Unexpected value", mtGoxRequestStringJPY, mtGoxRequestStringUSD);

  }
}
