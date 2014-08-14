package com.xeiam.xchange.coinfloor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */
public class TestCoinfloorUtils {

  @Test
  public void verifyNoncelength() {

    Assert.assertEquals(16, CoinfloorUtils.buildNonceString().getBytes().length);
  }

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
    Assert.assertEquals(CoinfloorCurrency.GBP, CoinfloorUtils.getCurrency(64032));
    Assert.assertEquals(63488, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.BTC));
    Assert.assertEquals(64032, CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.GBP));
    Assert.assertEquals(63488, CoinfloorUtils.toCurrencyCode(CoinfloorUtils.getCurrency(63488)));
    Assert.assertEquals(64032, CoinfloorUtils.toCurrencyCode(CoinfloorUtils.getCurrency(64032)));
    Assert.assertEquals(CoinfloorCurrency.BTC, CoinfloorUtils.getCurrency(CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.BTC)));
    Assert.assertEquals(CoinfloorCurrency.GBP, CoinfloorUtils.getCurrency(CoinfloorUtils.toCurrencyCode(CoinfloorCurrency.GBP)));
  }

  @Test
  public void verifyScaling() {

    Assert.assertEquals(BigDecimal.valueOf(10000, 4), CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, 10000));
    Assert.assertEquals(BigDecimal.valueOf(100, 2), CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.GBP, 100));
    Assert.assertEquals(BigDecimal.valueOf(100, 2), CoinfloorUtils.scalePriceToBigDecimal(CoinfloorCurrency.BTC, CoinfloorCurrency.GBP, 100));
    Assert.assertEquals(10000, CoinfloorUtils.scaleToInt(CoinfloorCurrency.BTC, BigDecimal.valueOf(1)));
    Assert.assertEquals(100, CoinfloorUtils.scaleToInt(CoinfloorCurrency.GBP, BigDecimal.valueOf(1)));
    Assert.assertEquals(100, CoinfloorUtils.scalePriceToInt(CoinfloorCurrency.BTC, CoinfloorCurrency.GBP, BigDecimal.valueOf(1)));
  }

  @Test(expected = ExchangeException.class)
  public void verifyScalingErrors() {

    CoinfloorUtils.scaleToBigDecimal("ASDF", 10000);
    CoinfloorUtils.scaleToInt("ASDF", new BigDecimal(1));
  }
}
