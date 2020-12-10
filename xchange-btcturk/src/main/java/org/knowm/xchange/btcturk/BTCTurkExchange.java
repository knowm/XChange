package org.knowm.xchange.btcturk;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.service.BTCTurkAccountService;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.btcturk.service.BTCTurkTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class BTCTurkExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new BTCTurkMarketDataService(this);
    this.accountService = new BTCTurkAccountService(this);
    this.tradeService = new BTCTurkTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.btcturk.com");
    exchangeSpecification.setHost("www.btcturk.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTCTurk");
    exchangeSpecification.setExchangeDescription(
        "BTCTurk is a Turkish Lira based Bitcoin, Ethereum, Ripple, Litecoin, Tether and Stellar Lumens exchange platform.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {}
}
