package org.knowm.xchange.bitstamp;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.service.polling.BitstampAccountService;
import org.knowm.xchange.bitstamp.service.polling.BitstampMarketDataService;
import org.knowm.xchange.bitstamp.service.polling.BitstampTradeService;
import org.knowm.xchange.bitstamp.service.streaming.BitstampPusherService;
import org.knowm.xchange.bitstamp.service.streaming.BitstampStreamingConfiguration;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class BitstampExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BitstampMarketDataService(this);
    this.pollingTradeService = new BitstampTradeService(this);
    this.pollingAccountService = new BitstampAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitstamp.net");
    exchangeSpecification.setHost("www.bitstamp.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitstamp");
    exchangeSpecification.setExchangeDescription("Bitstamp is a Bitcoin exchange registered in Slovenia.");
    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    if (configuration instanceof BitstampStreamingConfiguration) {
      return new BitstampPusherService(this, (BitstampStreamingConfiguration) configuration);
    }

    throw new IllegalArgumentException("Bitstamp only supports BitstampStreamingConfiguration");
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
