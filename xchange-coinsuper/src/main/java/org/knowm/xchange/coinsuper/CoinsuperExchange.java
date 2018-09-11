package org.knowm.xchange.coinsuper;

/** author: kevin gates */
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.service.CoinsuperAccountService;
import org.knowm.xchange.coinsuper.service.CoinsuperMarketDataService;
import org.knowm.xchange.coinsuper.service.CoinsuperTradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsuperExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.accountService = new CoinsuperAccountService(this);
    this.marketDataService = new CoinsuperMarketDataService(this);
    this.tradeService = new CoinsuperTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coinsuper.com");
    exchangeSpecification.setHost("api.coinsuper.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coinsuper");
    exchangeSpecification.setExchangeDescription(
        "Coinsuper is a world leading digital asset exchange.");
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "coinsuper");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
