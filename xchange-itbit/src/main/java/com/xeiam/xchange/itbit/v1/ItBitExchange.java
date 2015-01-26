package com.xeiam.xchange.itbit.v1;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitAccountService;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitMarketDataService;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitTradeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

public class ItBitExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new ItBitMarketDataService(this, nonceFactory);
    this.pollingAccountService = new ItBitAccountService(this, nonceFactory);
    this.pollingTradeService = new ItBitTradeService(this, nonceFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.itbit.com");
    exchangeSpecification.setHost("www.itbit.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ItBit");
    exchangeSpecification.setExchangeDescription("ItBit Bitcoin Exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("authHost", " https://api.itbit.com");

    return exchangeSpecification;
  }
}
