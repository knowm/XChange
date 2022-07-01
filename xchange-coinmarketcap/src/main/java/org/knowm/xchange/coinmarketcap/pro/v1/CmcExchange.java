package org.knowm.xchange.coinmarketcap.pro.v1;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinmarketcap.pro.v1.service.CmcMarketDataService;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;

public class CmcExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    if (this.marketDataService == null) {
      this.marketDataService = new CmcMarketDataService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification defaultSpec = new ExchangeSpecification(getClass());

    defaultSpec.setSslUri("https://pro-api.coinmarketcap.com");
    defaultSpec.setHost("coinmarketcap.com");
    defaultSpec.setExchangeName("CoinMarketCap");
    defaultSpec.setExchangeDescription("Cryptocurrency market cap rankings, charts, and more.");
    AuthUtils.setApiAndSecretKey(defaultSpec, "coinmarketcap");

    return defaultSpec;
  }

  public ExchangeSpecification getSandboxExchangeSpecification() {
    final ExchangeSpecification sandboxSpec = new ExchangeSpecification(getClass());

    sandboxSpec.setSslUri("https://sandbox-api.coinmarketcap.com");
    sandboxSpec.setHost("coinmarketcap.com");
    sandboxSpec.setExchangeName("CoinMarketCap Sandbox");
    sandboxSpec.setExchangeDescription("Cryptocurrency market cap rankings, charts, and more.");
    AuthUtils.setApiAndSecretKey(sandboxSpec, "coinmarketcap-sandbox");

    return sandboxSpec;
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {
    if (this.exchangeSpecification == null)
      this.exchangeSpecification = getDefaultExchangeSpecification();
    return exchangeSpecification;
  }

  @Override
  public ExchangeMetaData getExchangeMetaData() {
    return null;
  }

  @Override
  public void remoteInit() throws ExchangeException {
    initServices();
  }
}
