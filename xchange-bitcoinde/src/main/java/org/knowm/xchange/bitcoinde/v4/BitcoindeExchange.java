package org.knowm.xchange.bitcoinde.v4;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeAccountService;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeMarketDataService;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitcoin.de/");
    exchangeSpecification.setHost("bitcoin.de");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin.de");
    exchangeSpecification.setExchangeDescription(
        "Bitcoin.de is the largest bitcoin marketplace in Europe. All servers are located in Germany.");

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new BitcoindeMarketDataService(this);
    this.tradeService = new BitcoindeTradeService(this);
    this.accountService = new BitcoindeAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
