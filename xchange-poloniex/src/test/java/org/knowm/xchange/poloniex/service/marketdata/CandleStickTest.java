package org.knowm.xchange.poloniex.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;

/**
 * @author timmolter
 */
public class CandleStickTest {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class);
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    Instrument instrument = exchange.getExchangeMetaData().getInstruments().keySet().iterator().next();

    Instant now = Instant.now();
    Date endDate = Date.from(now);
    Date startDate = Date.from(now.minus(1, ChronoUnit.DAYS));
    CurrencyPair currencyPair = new CurrencyPair(instrument.getBase(), instrument.getCounter());
    CandleStickData ticker = marketDataService.getCandleStickData(currencyPair,new DefaultCandleStickParam(
        startDate, endDate, 60*60));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}