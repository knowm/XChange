package org.knowm.xchange.bity;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bity.dto.BityException;
import org.knowm.xchange.bity.dto.account.BityToken;
import org.knowm.xchange.bity.dto.marketdata.BityPair;
import org.knowm.xchange.bity.service.BityAccountService;
import org.knowm.xchange.bity.service.BityMarketDataService;
import org.knowm.xchange.bity.service.BityMarketDataServiceRaw;
import org.knowm.xchange.bity.service.BityTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BityExchange extends BaseExchange implements Exchange {

  protected BityToken token;

  @Override
  protected void initServices() {
    this.marketDataService = new BityMarketDataService(this);
    this.accountService = new BityAccountService(this);
    this.tradeService = new BityTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://bity.com");
    exchangeSpecification.setHost("bity.com");
    exchangeSpecification.setExchangeName("Bity");
    exchangeSpecification.setExchangeDescription(
        "Bity is a Swiss gateway to convert money into cryptocurrencies and digital assets.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return null;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    try {
      BityMarketDataServiceRaw dataService = (BityMarketDataServiceRaw) this.marketDataService;
      List<BityPair> bityPairs = dataService.getBityPairs();
      exchangeMetaData = BityAdapters.adaptMetaData(bityPairs, exchangeMetaData);
      token = ((BityAccountService) accountService).createToken();

      // Force tickers to be cached
      marketDataService.getTickers(null);

    } catch (BityException e) {
      throw BityErrorAdapter.adapt(e);
    }
  }

  public BityToken getToken() {
    return token;
  }
}
