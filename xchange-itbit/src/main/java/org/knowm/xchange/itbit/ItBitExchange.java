package org.knowm.xchange.itbit;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.itbit.service.ItBitAccountService;
import org.knowm.xchange.itbit.service.ItBitMarketDataService;
import org.knowm.xchange.itbit.service.ItBitTradeService;

public class ItBitExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new ItBitMarketDataService(this);
    this.accountService = new ItBitAccountService(this);
    this.tradeService = new ItBitTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.itbit.com");
    exchangeSpecification.setHost("api.itbit.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ItBit");
    exchangeSpecification.setExchangeDescription("ItBit Bitcoin Exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("authHost", " https://api.itbit.com");

    return exchangeSpecification;
  }
}
