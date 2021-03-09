package org.knowm.xchange.paymium;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.paymium.service.PaymiumAccountService;
import org.knowm.xchange.paymium.service.PaymiumMarketDataService;
import org.knowm.xchange.paymium.service.PaymiumTradeService;

public class PaymiumExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://paymium.com/api/v1");
    exchangeSpecification.setHost("paymium.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Paymium");
    exchangeSpecification.setExchangeDescription(
        "Paymium is a Bitcoin exchange registered and maintained by a company based in Paris, France.");

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.accountService = new PaymiumAccountService(this);
    this.tradeService = new PaymiumTradeService(this);
    this.marketDataService = new PaymiumMarketDataService(this);
  }
}
