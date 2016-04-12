package org.knowm.xchange.coinfloor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author obsessiveOrange
 */
public class CoinfloorUtilsTest {

  @Test(expected = ExchangeException.class)
  public void verifyCheckSuccessInt() {

    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("error_code", 1);
    CoinfloorUtils.checkSuccess(payload);
  }

  @Test(expected = ExchangeException.class)
  public void verifyCheckSuccessString() {

    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("error_code", "ERROR!");
    CoinfloorUtils.checkSuccess(payload);
  }

  @Test
  public void verifyCurrencyUtils() {

    Assert.assertEquals(CoinfloorCurrency.BTC, CoinfloorUtils.getCurrency(63488));
    Assert.assertEquals(CoinfloorCurrency.EUR, CoinfloorUtils.getCurrency(64000));
    Assert.assertEquals(CoinfloorCurrency.GBP, CoinfloorUtils.getCurrency(64032));
    Assert.assertEquals(CoinfloorCurrency.USD, CoinfloorUtils.getCurrency(64128));
    Assert.assertEquals(CoinfloorCurrency.PLN, CoinfloorUtils.getCurrency(64936));

    Assert.assertEquals(63488, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.BTC));
    Assert.assertEquals(64000, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.EUR));
    Assert.assertEquals(64032, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.GBP));
    Assert.assertEquals(64128, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.USD));
    Assert.assertEquals(64936, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.PLN));

    for (int n : new int[] { 63488, 64000, 64032, 64128, 64936 }) {
      Assert.assertEquals(n, CoinfloorUtils.toCurrencyCode(CoinfloorUtils.getCurrency(n)));
    }

    for (CoinfloorCurrency c : CoinfloorCurrency.values()) {
      Assert.assertEquals(c, CoinfloorUtils.getCurrency(CoinfloorUtils.toCurrencyCode(c)));
    }
  }

  @Test
  public void verifyScaling() {

    Assert.assertEquals(BigDecimal.valueOf(10000, 4), CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, 10000));
    Assert.assertEquals(BigDecimal.valueOf(100, 2), CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.GBP, 100));
    Assert.assertEquals(BigDecimal.valueOf(10, 2), CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.PLN, 10));
    Assert.assertEquals(BigDecimal.valueOf(100, 2), CoinfloorUtils.scalePriceToBigDecimal(CoinfloorCurrency.BTC, CoinfloorCurrency.GBP, 100));
    Assert.assertEquals(10000, CoinfloorUtils.scaleToInt(CoinfloorCurrency.BTC, BigDecimal.valueOf(1)));
    Assert.assertEquals(100, CoinfloorUtils.scaleToInt(CoinfloorCurrency.GBP, BigDecimal.valueOf(1)));
    Assert.assertEquals(100, CoinfloorUtils.scaleToInt(CoinfloorCurrency.EUR, BigDecimal.valueOf(1)));
    Assert.assertEquals(100, CoinfloorUtils.scalePriceToInt(CoinfloorCurrency.BTC, CoinfloorCurrency.GBP, BigDecimal.valueOf(1)));
  }

  @Test(expected = ExchangeException.class)
  public void verifyScalingErrors() {

    CoinfloorUtils.scaleToBigDecimal("ASDF", 10000);
    CoinfloorUtils.scaleToInt("ASDF", new BigDecimal(1));
  }
}
