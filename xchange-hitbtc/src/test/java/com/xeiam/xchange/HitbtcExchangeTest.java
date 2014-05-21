package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.HitbtcMarketDataServiceRaw;
import org.junit.Before;

/**
 * @author kpysniak
 */
public class HitbtcExchangeTest {

  private HitbtcExchange hitbtcExchange;
  private HitbtcMarketDataServiceRaw hitbtcMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {
    this.hitbtcExchange = (HitbtcExchange) ExchangeFactory.INSTANCE.createExchange(HitbtcExchange.class.getName());
    this.hitbtcMarketDataServiceRaw = (HitbtcMarketDataServiceRaw) hitbtcExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_USD;
  }

}
