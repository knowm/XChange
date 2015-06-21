package com.xeiam.xchange.mexbt.service.polling;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mexbt.MeXBTExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class MeXBTBasePollingServiceTest {

  @Test
  public void testGetExchangeSymbols() throws IOException {
    PollingMarketDataService mds = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName()).getPollingMarketDataService();
    List<CurrencyPair> exchangeSymbols = mds.getExchangeSymbols();
    assertEquals(CurrencyPair.BTC_MXN, exchangeSymbols.get(0));
    assertEquals(CurrencyPair.BTC_USD, exchangeSymbols.get(1));
  }

}
