package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.service.polling.BitbayMarketDataServiceRaw;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * @author kpysniak
 */
public class BitbayExchangeTest {

  private BitbayExchange bitbayExchange;
  private BitbayMarketDataServiceRaw bitbayMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {
    this.bitbayExchange = (BitbayExchange) ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName());
    this.bitbayMarketDataServiceRaw = (BitbayMarketDataServiceRaw) bitbayExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_USD;
  }

  /*
  @Test
  public void testBitbayRequest() {
    try {
      BitbayTicker bitbayTicker = bitbayMarketDataServiceRaw.getBitbayTicker(currencyPair);
      System.out.println(bitbayTicker.toString());

      BitbayOrderBook bitbayOrderBook = bitbayMarketDataServiceRaw.getBitbayOrderBook(currencyPair);
      System.out.println(bitbayOrderBook.toString());

      BitbayTrade[] trades = bitbayMarketDataServiceRaw.getBitbayTrades(currencyPair);
      System.out.println(Arrays.toString(trades));

      Ticker ticker = bitbayExchange.getPollingMarketDataService().getTicker(currencyPair);
      System.out.println(ticker.toString());

      OrderBook orderBook = bitbayExchange.getPollingMarketDataService().getOrderBook(currencyPair);
      System.out.println(orderBook.toString());

      Trades tradesAll = bitbayExchange.getPollingMarketDataService().getTrades(currencyPair);
      System.out.println(tradesAll.toString());

    } catch (IOException e) {
      Assert.fail();
    }
  }
  */


}
