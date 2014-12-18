package com.xeiam.xchange.anx.v2;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.service.polling.ANXAccountService;
import com.xeiam.xchange.anx.v2.service.polling.ANXMarketDataService;
import com.xeiam.xchange.anx.v2.service.polling.ANXTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the ANX exchange API</li>
 * </ul>
 */
public class ANXExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    // Configure the basic services if configuration does not apply
    this.pollingMarketDataService = new ANXMarketDataService(exchangeSpecification);
    this.pollingTradeService = new ANXTradeService(exchangeSpecification, nonceFactory);
    this.pollingAccountService = new ANXAccountService(exchangeSpecification, nonceFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://anxpro.com");
    // exchangeSpecification.setPlainTextUriStreaming("ws://websocket.anx.hk");
    // exchangeSpecification.setSslUriStreaming("wss://websocket.anx.hk");
    exchangeSpecification.setHost("anxpro.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ANXPRO");
    exchangeSpecification.setExchangeDescription("Asia Nexgen is a Bitcoin exchange registered in Hong Kong.");

    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    // if (configuration instanceof ANXStreamingConfiguration) {
    // return new ANXWebsocketService(getExchangeSpecification(), (ANXStreamingConfiguration) configuration);
    // }

    throw new IllegalArgumentException("ANX.HK does not support streaming in this API");
  }
}
