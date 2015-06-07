package com.xeiam.xchange.bitcoinde;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.service.polling.BitcoindeMarketDataService;

/**
 * @author matthewdowney
 */
public class BitcoindeExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcoinapi.de/v1/");
    exchangeSpecification.setHost("bitcoin.de");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin.de");
    exchangeSpecification.setExchangeDescription("Bitcoin.de is the largest bitcoin marketplace in Europe. All servers are located in Germany.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoindeMarketDataService(this);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    // No private API implemented. Not needed for this exchange at the
    // moment.
    return null;
  }
}
