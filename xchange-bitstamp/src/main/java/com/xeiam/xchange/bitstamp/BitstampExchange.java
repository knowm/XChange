package com.xeiam.xchange.bitstamp;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.service.polling.BitstampAccountService;
import com.xeiam.xchange.bitstamp.service.polling.BitstampMarketDataService;
import com.xeiam.xchange.bitstamp.service.polling.BitstampTradeService;
import com.xeiam.xchange.bitstamp.service.streaming.BitstampPusherService;
import com.xeiam.xchange.bitstamp.service.streaming.BitstampStreamingConfiguration;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * @author Matija Mazi
 */
public class BitstampExchange extends BaseExchange implements Exchange {

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
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitstampMarketDataService(exchangeSpecification);
    this.pollingTradeService = new BitstampTradeService(exchangeSpecification);
    this.pollingAccountService = new BitstampAccountService(exchangeSpecification);
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    if (configuration instanceof BitstampStreamingConfiguration) {
      return new BitstampPusherService(getExchangeSpecification(), (BitstampStreamingConfiguration) configuration);
    }

    throw new IllegalArgumentException("Bitstamp only supports BitstampStreamingConfiguration");
  }

}
