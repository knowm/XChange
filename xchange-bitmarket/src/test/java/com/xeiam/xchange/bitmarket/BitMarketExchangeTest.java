package com.xeiam.xchange.bitmarket;

import org.junit.Before;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitmarket.service.polling.BitMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;

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
