package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.dto.marketdata.BTCCentralTrade;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.BTCCentralMarketDataServiceRaw;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * @author kpysniak
 */
public class BTCCentralExchangeTest {

  private BTCCentralExchange btcCentralExchange;
  private BTCCentralMarketDataServiceRaw btcCentralMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {
    this.btcCentralExchange = (BTCCentralExchange) ExchangeFactory.INSTANCE.createExchange(BTCCentralExchange.class.getName());
    this.btcCentralMarketDataServiceRaw = (BTCCentralMarketDataServiceRaw) btcCentralExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_EUR;
  }

  @Test
  public void testBTCCentralTickerRequest() {
    try {
      BTCCentralTicker btcCentralTicker = btcCentralMarketDataServiceRaw.getBTCCentralTicker();

      assertNotNull(btcCentralTicker);
      assertNotNull(btcCentralTicker.getAsk());
      assertNotNull(btcCentralTicker.getBid());
      assertNotNull(btcCentralTicker.getCurrency());
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void testBTCCentralOrderBook() {
    try {
      BTCCentralMarketDepth btcCentralMarketDepth = btcCentralMarketDataServiceRaw.getBTCCentralMarketDepth();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void testBTCCentralTrades() {
    try {
      BTCCentralTrade[] trades = btcCentralMarketDataServiceRaw.getBTCCentralTrades();
    } catch (IOException e) {
      Assert.fail();
    }
  }

}
