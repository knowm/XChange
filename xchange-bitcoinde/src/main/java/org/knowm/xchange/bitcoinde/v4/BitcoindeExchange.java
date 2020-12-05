package org.knowm.xchange.bitcoinde.v4;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeAccountService;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeMarketDataService;
import org.knowm.xchange.bitcoinde.v4.service.BitcoindeTradeService;

public class BitcoindeExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
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
}
