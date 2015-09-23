package com.xeiam.xchange.btcmarkets;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.service.polling.BTCMarketsAccountService;
import com.xeiam.xchange.btcmarkets.service.polling.BTCMarketsMarketDataService;
import com.xeiam.xchange.btcmarkets.service.polling.BTCMarketsTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

/**
 * @author Matija Mazi
 */
public class BTCMarketsExchange extends BaseExchange implements Exchange {

  public static final String CURRENCY_PAIR = "CURRENCY_PAIR";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTCMarketsMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null) {
      this.pollingTradeService = new BTCMarketsTradeService(this);
      this.pollingAccountService = new BTCMarketsAccountService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.btcmarkets.net");
    exchangeSpecification.setHost("btcmarkets.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTCMarkets");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return new CurrentTimeNonceFactory();
  }
}
