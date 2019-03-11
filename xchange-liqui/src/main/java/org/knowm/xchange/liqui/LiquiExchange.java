package org.knowm.xchange.liqui;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.service.LiquiAccountService;
import org.knowm.xchange.liqui.service.LiquiMarketDataService;
import org.knowm.xchange.liqui.service.LiquiMarketDataServiceRaw;
import org.knowm.xchange.liqui.service.LiquiTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LiquiExchange extends BaseExchange implements Exchange {

  // TODO do we need this?
  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  public LiquiExchange() {}

  @Override
  protected void initServices() {
    marketDataService = new LiquiMarketDataService(this);
    accountService = new LiquiAccountService(this);
    tradeService = new LiquiTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification spec = new ExchangeSpecification(getClass().getCanonicalName());
    spec.setSslUri("https://api.liqui.io/");
    spec.setHost("api.liqui.io");
    spec.setPort(80);
    spec.setExchangeName("Liqui.io");
    spec.setExchangeDescription("Liqui.io Exchange.");
    return spec;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    final Map<String, LiquiPairInfo> infos =
        ((LiquiMarketDataServiceRaw) marketDataService).getInfo();
    exchangeMetaData = LiquiAdapters.adaptToExchangeMetaData(infos);
  }
}
