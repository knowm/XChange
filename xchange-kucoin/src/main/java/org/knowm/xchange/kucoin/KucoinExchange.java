package org.knowm.xchange.kucoin;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class KucoinExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  /**
   * Use with {@link ExchangeSpecification#getExchangeSpecificParametersItem(String)} to specify
   * that connection should be made to the Kucoin sandbox instead of the live API.
   */
  public static final String PARAM_SANDBOX = "Use_Sandbox";

  static final String SANDBOX_HOST = "openapi-sandbox.kucoin.com";
  static final String SANDBOX_URI = "https://" + SANDBOX_HOST;

  static final String LIVE_HOST = "openapi-v2.kucoin.com";
  static final String LIVE_URI = "https://" + LIVE_HOST;

  private void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(PARAM_SANDBOX))) {
        logger.debug("Connecting to sandbox");
        exchangeSpecification.setSslUri(KucoinExchange.SANDBOX_URI);
        exchangeSpecification.setHost(KucoinExchange.SANDBOX_HOST);
      } else {
        logger.debug("Connecting to live");
      }
    }
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    concludeHostParams(exchangeSpecification);
    this.marketDataService = new KucoinMarketDataService(this);
    this.accountService = new KucoinAccountService(this);
    this.tradeService = new KucoinTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(LIVE_URI);
    exchangeSpecification.setHost(LIVE_HOST);
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kucoin");
    exchangeSpecification.setExchangeDescription("Kucoin is a bitcoin and altcoin exchange.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    this.exchangeMetaData =
        KucoinAdapters.adaptMetadata(
            this.exchangeMetaData, getMarketDataService().getKucoinSymbols());
  }

  @Override
  public KucoinMarketDataService getMarketDataService() {
    return (KucoinMarketDataService) super.getMarketDataService();
  }

  @Override
  public KucoinTradeService getTradeService() {
    return (KucoinTradeService) super.getTradeService();
  }

  @Override
  public KucoinAccountService getAccountService() {
    return (KucoinAccountService) super.getAccountService();
  }
}
