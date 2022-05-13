package org.knowm.xchange.bitrue.service.marketdata;

import org.junit.*;
import org.knowm.xchange.bitrue.BitrueExchangeIntegration;
import org.knowm.xchange.bitrue.dto.marketdata.BitrueTicker24h;
import org.knowm.xchange.bitrue.service.BitrueMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketDataServiceIntegration extends BitrueExchangeIntegration {

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
  public void testBitrueTicker24h() throws Exception {

    List<BitrueTicker24h> tickers = new ArrayList<>();
    for (CurrencyPair cp : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      if (cp.counter == Currency.USDT) {
        tickers.add(getBitrueTicker24h(cp));
      }
    }

    Collections.sort(
        tickers,
        (BitrueTicker24h t1, BitrueTicker24h t2) ->
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

  private BitrueTicker24h getBitrueTicker24h(CurrencyPair pair) throws IOException {
    BitrueMarketDataService service = (BitrueMarketDataService) marketService;
    return service.ticker24h(pair);
  }
}
