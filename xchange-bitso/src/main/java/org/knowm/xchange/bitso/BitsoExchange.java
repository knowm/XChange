package org.knowm.xchange.bitso;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitso.service.BitsoAccountService;
import org.knowm.xchange.bitso.service.BitsoMarketDataService;
import org.knowm.xchange.bitso.service.BitsoTradeService;

/** @author Matija Mazi, Piotr Ładyżyński */
public class BitsoExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new BitsoMarketDataService(this);
    this.accountService = new BitsoAccountService(this);
    this.tradeService = new BitsoTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bitso.com");
    exchangeSpecification.setHost("bitso.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitso");
    exchangeSpecification.setExchangeDescription(
        "A new hub for trade and remittance of Bitcoin in Mexico.");
    return exchangeSpecification;
  }
}
