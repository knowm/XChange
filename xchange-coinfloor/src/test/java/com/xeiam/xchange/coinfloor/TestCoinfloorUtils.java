/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinfloor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.xeiam.xchange.ExchangeException;

/**
 * @author obsessiveOrange
 */
public class TestCoinfloorUtils {

  @Test
  public void verifyNoncelength() {

    Assert.assertEquals(16, CoinfloorUtils.buildNonceString().getBytes().length);
  }

  @Test (expected = ExchangeException.class)
  public void verifyCheckSuccessInt() {
	Map<String, Object> payload = new HashMap<String, Object>();
	payload.put("error_code", 1);
	CoinfloorUtils.checkSuccess(payload);
  }
  
  @Test (expected = ExchangeException.class)
  public void verifyCheckSuccessString() {
	Map<String, Object> payload = new HashMap<String, Object>();
	payload.put("error_code", "ERROR!");
	CoinfloorUtils.checkSuccess(payload);
  }
  
  @Test
  public void verifyCurrencyUtils() {

    Assert.assertEquals("BTC", CoinfloorUtils.getCurrency(63488));
    Assert.assertEquals("GBP", CoinfloorUtils.getCurrency(64032));
    Assert.assertEquals(63488, CoinfloorUtils.toCurrencyCode("BTC"));
    Assert.assertEquals(64032, CoinfloorUtils.toCurrencyCode("GBP"));
    Assert.assertEquals(63488, CoinfloorUtils.toCurrencyCode(CoinfloorUtils.getCurrency(63488)));
    Assert.assertEquals(64032, CoinfloorUtils.toCurrencyCode(CoinfloorUtils.getCurrency(64032)));
    Assert.assertEquals("BTC", CoinfloorUtils.getCurrency(CoinfloorUtils.toCurrencyCode("BTC")));
    Assert.assertEquals("GBP", CoinfloorUtils.getCurrency(CoinfloorUtils.toCurrencyCode("GBP")));
  }
  
  @Test
  public void verifyScaling() {

    Assert.assertEquals(new BigDecimal(1), CoinfloorUtils.scaleToBigDecimal("BTC", 10000));
    Assert.assertEquals(new BigDecimal(1), CoinfloorUtils.scaleToBigDecimal("GBP", 10000));
    Assert.assertEquals(new BigDecimal(1), CoinfloorUtils.scalePriceToBigDecimal(10000));
    Assert.assertEquals(10000, CoinfloorUtils.scaleToInt("BTC", new BigDecimal(1)));
    Assert.assertEquals(10000, CoinfloorUtils.scaleToInt("GBP", new BigDecimal(1)));
    Assert.assertEquals(10000, CoinfloorUtils.scalePriceToInt(new BigDecimal(1)));
  }
  
  @Test (expected = ExchangeException.class)
  public void verifyScalingErrors() {
	    CoinfloorUtils.scaleToBigDecimal("ASDF", 10000);
	    CoinfloorUtils.scaleToInt("ASDF", new BigDecimal(1));
	  }
}
