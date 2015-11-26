package com.xeiam.xchange.bleutrade.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getName());
    exchange.remoteInit();
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    CurrencyPair market = exchange.getMetaData().getMarketMetaDataMap().keySet().iterator().next();
    Ticker ticker = marketDataService.getTicker(market);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

}
