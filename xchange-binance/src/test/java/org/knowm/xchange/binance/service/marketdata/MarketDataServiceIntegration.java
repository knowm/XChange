package org.knowm.xchange.binance.service.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceExchangeIntegration;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegration extends BinanceExchangeIntegration {

  static MarketDataService marketService;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
    marketService = exchange.getMarketDataService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void testTimestamp() throws Exception {

    long serverTime = exchange.getTimestampFactory().createValue();
    Assert.assertTrue(0 < serverTime);
  }

  @Test
  public void testBinanceTicker24h() throws Exception {

    List<BinanceTicker24h> tickers = new ArrayList<>();
    for (CurrencyPair cp : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      if (cp.counter == Currency.USDT) {
        tickers.add(getBinanceTicker24h(cp));
      }
    }

    Collections.sort(
        tickers,
        (BinanceTicker24h t1, BinanceTicker24h t2) ->
            t2.getPriceChangePercent().compareTo(t1.getPriceChangePercent()));

    tickers.stream()
        .forEach(
            t -> {
              System.out.println(
                  t.getCurrencyPair()
                      + " => "
                      + String.format("%+.2f%%", t.getPriceChangePercent()));
            });
  }

  private BinanceTicker24h getBinanceTicker24h(CurrencyPair pair) throws IOException {
    BinanceMarketDataService service = (BinanceMarketDataService) marketService;
    return service.ticker24h(pair);
  }
}
