package org.knowm.xchange.cointrader;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cointrader.service.polling.CointraderAccountService;
import org.knowm.xchange.cointrader.service.polling.CointraderMarketDataService;
import org.knowm.xchange.cointrader.service.polling.CointraderTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class CointraderExchange extends BaseExchange implements Exchange {

  public static final String CURRENCY_PAIR = "CURRENCY_PAIR";

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CointraderMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null && exchangeSpecification.getSecretKey() != null) {
      this.pollingTradeService = new CointraderTradeService(this);
      this.pollingAccountService = new CointraderAccountService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.cointrader.net");
    //    exchangeSpecification.setSslUri("https://sandbox.cointrader.net");
    exchangeSpecification.setHost("www.cointrader.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cointrader");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("No nonce factory is used.");
  }
}
