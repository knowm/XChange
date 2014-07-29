package com.xeiam.xchange.btce.v2.service;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.xeiam.xchange.btce.v2.BTCEUtils;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Test class for BTCEUtils class
 */
@Deprecated
@Ignore
public class BTCEUtilsTest {

  @Test
  public void testIsValidCurrencyPair() {

    assertThat(BTCEUtils.isValidCurrencyPair(CurrencyPair.BTC_USD)).isTrue();
    assertThat(BTCEUtils.isValidCurrencyPair(new CurrencyPair("BTC", "USD"))).isTrue();
    assertThat(BTCEUtils.isValidCurrencyPair(new CurrencyPair("BTC", "CAD"))).isFalse();
  }
}
