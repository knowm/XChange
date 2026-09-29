package org.knowm.xchange.cexio.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;

/**
 * @author timmolter
 */
class TickerFetchIntegration {

  private static Exchange exchange;

  @BeforeAll
  static void setup() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class);
  }

  @Test
  void tickerFetchTest() throws Exception {
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  void tickerFetchAllTest() throws Exception {
    Set<Instrument> allCurrencyPairs = exchange.getExchangeMetaData().getInstruments().keySet();

    List<Ticker> tickers =
        exchange.getMarketDataService().getTickers((InstrumentsParams) () -> allCurrencyPairs);
    Set<Instrument> currencyPairsInTickers =
        tickers.stream().map(Ticker::getCurrencyPair).collect(Collectors.toSet());

    assertThat(currencyPairsInTickers.size())
        .as(
            "The number of currency pairs should be the same as the requested number of currency"
                + " pairs")
        .isEqualTo(allCurrencyPairs.size());
    assertThat(currencyPairsInTickers.containsAll(allCurrencyPairs))
        .withFailMessage("Returned currency pairs should be the same as the requested")
        .isTrue();
  }

  @Test
  void tickerFetchSomeTest() throws Exception {
    Set<CurrencyPair> someCurrencyPairs = new HashSet<>();
    someCurrencyPairs.add(new CurrencyPair("BTC", "USD"));
    someCurrencyPairs.add(new CurrencyPair("BTC", "EUR"));

    List<Ticker> tickers =
        exchange.getMarketDataService().getTickers((CurrencyPairsParam) () -> someCurrencyPairs);
    Set<CurrencyPair> currencyPairsInTickers =
        tickers.stream().map(Ticker::getCurrencyPair).collect(Collectors.toSet());

    assertThat(currencyPairsInTickers.size())
        .as(
            "The number of currency pairs should be the same as the requested number of currency"
                + " pairs")
        .isEqualTo(someCurrencyPairs.size());
    assertThat(currencyPairsInTickers.containsAll(someCurrencyPairs))
        .withFailMessage("Returned currency pairs should be the same as the requested")
        .isTrue();
  }

  @Test
  void tickerFetchNoneTest() throws Exception {
    List<Ticker> tickers =
        exchange.getMarketDataService().getTickers((CurrencyPairsParam) Collections::emptySet);
    Set<CurrencyPair> currencyPairsInTickers =
        tickers.stream().map(Ticker::getCurrencyPair).collect(Collectors.toSet());

    assertThat(currencyPairsInTickers.size())
        .as(
            "The number of currency pairs should be the same as the requested number of currency"
                + " pairs")
        .isEqualTo(0);
  }
}
