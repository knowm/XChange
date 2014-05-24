package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitmarket.BitMarketExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.bitmarket.service.polling.BitMarketDataServiceRaw;
import org.junit.Before;

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
