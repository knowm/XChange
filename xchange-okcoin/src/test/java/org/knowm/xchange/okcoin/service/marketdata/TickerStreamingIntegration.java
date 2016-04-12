package org.knowm.xchange.okcoin.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.java_websocket.WebSocket;
import org.junit.Test;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.okcoin.service.streaming.OkCoinExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

/**
 * @author timmolter
 */
public class TickerStreamingIntegration {

  @Test
  public void tickerFetchStreamingChinaTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", false);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    final StreamingExchangeService service = exchange.getStreamingExchangeService(new OkCoinExchangeStreamingConfiguration());

    service.connect();

    boolean gotTicker = false;

    while (!gotTicker) {
      assertThat(service.getWebSocketStatus()).isNotEqualTo(WebSocket.READYSTATE.CLOSED);

      ExchangeEvent event = service.getNextEvent();

      if (event != null) {
        if (event.getEventType().equals(ExchangeEventType.TICKER)) {
          gotTicker = true;
          Ticker ticker = (Ticker) event.getPayload();
          System.out.println(ticker.toString());
          assertThat(ticker).isNotNull();
        }
      }
    }

    service.disconnect();
  }

  @Test
  public void tickerFetchStreamingIntlTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    //This constructor hack has AIDS
    final StreamingExchangeService service = exchange
        .getStreamingExchangeService(new OkCoinExchangeStreamingConfiguration(new CurrencyPair[] { CurrencyPair.BTC_USD }));

    service.connect();

    boolean gotTicker = false;

    while (!gotTicker) {
      assertThat(service.getWebSocketStatus()).isNotEqualTo(WebSocket.READYSTATE.CLOSED);

      ExchangeEvent event = service.getNextEvent();

      if (event != null) {
        if (event.getEventType().equals(ExchangeEventType.TICKER)) {
          gotTicker = true;
          //Ticker ticker = (Ticker) event.getPayload();
          //System.out.println(ticker.toString());
          //assertThat(ticker).isNotNull();
        }
      }
    }

    service.disconnect();
  }

}
