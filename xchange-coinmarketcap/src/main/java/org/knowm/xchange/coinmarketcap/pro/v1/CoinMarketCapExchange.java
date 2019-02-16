package org.knowm.xchange.coinmarketcap.pro.v1;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CoinMarketCapMarketDataService;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

/** @author allenday */
public class CoinMarketCapExchange extends BaseExchange implements Exchange {

  private static Logger logger = LoggerFactory.getLogger(CoinMarketCapExchange.class);

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private CoinMarketCapMarketDataService marketDataService;
  private ExchangeSpecification exchangeSpecification;

  public CoinMarketCapExchange() {
    initServices();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification defaultExchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    defaultExchangeSpecification.setSslUri("https://pro-api.coinmarketcap.com");
    defaultExchangeSpecification.setHost("coinmarketcap.com");
    defaultExchangeSpecification.setExchangeName("CoinMarketCapAuthenticated");
    defaultExchangeSpecification.setExchangeDescription(
        "Cryptocurrency market cap rankings, charts, and more.");

    AuthUtils.setApiAndSecretKey(defaultExchangeSpecification, "coinmarketcap");

//
//    Properties props = AuthUtils.getSecretProperties("coinmarketcap");
//    if (props != null) {
//      defaultExchangeSpecification.setApiKey(props.getProperty("apiKey"));
//    }

    return defaultExchangeSpecification;
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {
    if (exchangeSpecification == null) exchangeSpecification = getDefaultExchangeSpecification();
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    this.exchangeSpecification = exchangeSpecification;
  }

  @Override
  public ExchangeMetaData getExchangeMetaData() {
    return null;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    initServices();
  }

  @Override
  protected void initServices() {
    if (this.marketDataService == null) {
      this.marketDataService = new CoinMarketCapMarketDataService(this);
    }
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public TradeService getTradeService() {
    return null;
  }

  @Override
  public AccountService getAccountService() {
    return null;
  }

  @Override
  public MarketDataService getMarketDataService() {
    return marketDataService;
  }
}
