package org.knowm.xchange.bitmarket;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmarket.service.polling.BitMarketAccountService;
import org.knowm.xchange.bitmarket.service.polling.BitMarketDataService;
import org.knowm.xchange.bitmarket.service.polling.BitMarketTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BitMarketDataService(this);
    this.pollingTradeService = new BitMarketTradeService(this);
    this.pollingAccountService = new BitMarketAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitmarket.pl/");
    exchangeSpecification.setHost("www.bitmarket.pl");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmarket");
    exchangeSpecification.setExchangeDescription("Bitmarket is a Bitcoin exchange based in Poland.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
