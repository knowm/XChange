package org.knowm.xchange.bitmarket;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmarket.service.BitMarketAccountService;
import org.knowm.xchange.bitmarket.service.BitMarketDataService;
import org.knowm.xchange.bitmarket.service.BitMarketTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author kpysniak, kfonal */
public class BitMarketExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();
  private final IRestProxyFactory restProxyFactory;

  public BitMarketExchange() {
    this(new RestProxyFactoryImpl());
  }

  private BitMarketExchange(IRestProxyFactory restProxyFactory) {
    this.restProxyFactory = restProxyFactory;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new BitMarketDataService(this, restProxyFactory);
    this.tradeService = new BitMarketTradeService(this, restProxyFactory);
    this.accountService = new BitMarketAccountService(this, restProxyFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitmarket.pl/");
    exchangeSpecification.setHost("www.bitmarket.pl");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmarket");
    exchangeSpecification.setExchangeDescription(
        "Bitmarket is a Bitcoin exchange based in Poland.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
