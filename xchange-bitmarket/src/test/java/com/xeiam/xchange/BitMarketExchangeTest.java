package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.service.polling.BitMarketDataServiceRaw;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * @author kpysniak
 */
public class BitMarketExchangeTest {

  private BitMarketExchange bitMarketExchange;
  private BitMarketDataServiceRaw bitMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {
    this.bitMarketExchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getName());
    this.bitMarketDataServiceRaw = (BitMarketDataServiceRaw) bitMarketExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_PLN;
  }

}
