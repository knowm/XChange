package org.knowm.xchange.cryptonit2;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptonit2.service.CryptonitAccountService;
import org.knowm.xchange.cryptonit2.service.CryptonitMarketDataService;
import org.knowm.xchange.cryptonit2.service.CryptonitTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Matija Mazi */
public class CryptonitExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new CryptonitMarketDataService(this);
    this.tradeService = new CryptonitTradeService(this);
    this.accountService = new CryptonitAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.cryptonit.net");
    exchangeSpecification.setHost("www.cryptonit.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptonit");
    exchangeSpecification.setExchangeDescription(
        "Cryptonit is a Bitcoin exchange registered in England.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
