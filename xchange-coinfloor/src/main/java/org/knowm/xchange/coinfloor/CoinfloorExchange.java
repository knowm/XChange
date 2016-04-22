package org.knowm.xchange.coinfloor;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import org.knowm.xchange.coinfloor.streaming.CoinfloorStreamingExchangeService;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author obsessiveOrange
 */
public class CoinfloorExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUriStreaming("ws://api.coinfloor.co.uk");
    exchangeSpecification.setSslUriStreaming("wss://api.coinfloor.co.uk");
    exchangeSpecification.setHost("coinfloor.co.uk");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coinfloor");
    exchangeSpecification.setExchangeDescription(
        "Coinfloor is a company registered in England and Wales registration number 08493818. Coinfloor allows users to trade Bitcoin. Coinfloor LTD is registered at 200 Aldergate C/O Buckworth Solicitors EC1A 4HD London, United Kingdom.");

    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

    return new CoinfloorStreamingExchangeService(this, (CoinfloorStreamingConfiguration) exchangeStreamingConfiguration);
  }

  public StreamingExchangeService getStreamingExchangeService() {

    return getStreamingExchangeService(new CoinfloorStreamingConfiguration(10, 10000, 60000, false, true, false));
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // Coinfloor uses it's own custom request factory for making authenticated API calls
    return null;
  }

  @Override
  protected void initServices() {
  }
}
