package com.xeiam.xchange.btctrade;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeAccountService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeTradeService;

public class BTCTradeExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BTCTradeMarketDataService(exchangeSpecification);
    if (exchangeSpecification.getApiKey() != null) {
      this.pollingAccountService = new BTCTradeAccountService(exchangeSpecification);
      this.pollingTradeService = new BTCTradeTradeService(exchangeSpecification);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://www.btctrade.com/api");
    exchangeSpecification.setHost("www.btctrade.com");
    exchangeSpecification.setExchangeName("BTCTrade");
    return exchangeSpecification;
  }

}
