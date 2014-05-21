package com.xeiam.xchange.lakebtc;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCMarketDataServiceRaw;
import org.junit.Before;

/**
 * @author kpysniak
 */
public class LakeBTCExchangeTest {

  private LakeBTCExchange lakeBTCExchange;
  private LakeBTCMarketDataServiceRaw lakeBTCMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {
    this.lakeBTCExchange = (LakeBTCExchange) ExchangeFactory.INSTANCE.createExchange(LakeBTCExchange.class.getName());
    this.lakeBTCMarketDataServiceRaw = (LakeBTCMarketDataServiceRaw) lakeBTCExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_USD;
  }

}
