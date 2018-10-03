package org.knowm.xchange.coinmarketcap.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.NEO_USD;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinmarketcap.CoinMarketCapExchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.coinmarketcap.service.CoinMarketCapMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author allenday */
public class TickerFetchIntegration {
  private static Exchange exchange;

  @BeforeClass
  public static void initExchange() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinMarketCapExchange.class.getName());
  }

  @Test
  public void tickerFetchTest() throws Exception {

    MarketDataService marketDataService = exchange.getMarketDataService();

    CurrencyPair NEO_BTC = new CurrencyPair("NEO", "BTC");
    Ticker ticker0 = marketDataService.getTicker(NEO_USD);
    // System.out.println(ticker.toString());
    assertThat(ticker0).isNotNull();

    Boolean found = false;
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    for (CurrencyPair p : pairs) {
      if (p.compareTo(NEO_BTC) == 0) {
        found = true;
      }
    }
    assert (found);
  }

  @Test
  public void tickerConvertParamTest() throws Exception {

    CoinMarketCapMarketDataService marketDataService =
        (CoinMarketCapMarketDataService) exchange.getMarketDataService();

    CurrencyPair NEO_BTC = new CurrencyPair("NEO", "BTC");
    List<CoinMarketCapTicker> tickers =
        marketDataService.getCoinMarketCapTickers(0, new Currency("EUR"));
    // System.out.println(ticker.toString());
    assertThat(tickers).isNotNull();

    Boolean found = false;
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    for (CurrencyPair p : pairs) {
      if (p.compareTo(NEO_BTC) == 0) {
        found = true;
      }
    }
    assert (found);
  }
}
