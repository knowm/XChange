package org.knowm.xchange.kucoin.v2;

import java.io.IOException;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class KucoinV2Exchange extends BaseExchange implements Exchange {

  /**
   * Use with {@link ExchangeSpecification#getExchangeSpecificParametersItem(String)}
   * to specify that connection should be made to the Kucoin sandbox instead of the
   * live API.
   */
  public static final String PARAM_SANDBOX = "Use_Sandbox";

  static final String SANDBOX_HOST = "openapi-sandbox.kucoin.com";
  static final String SANDBOX_URI = "https://" + SANDBOX_HOST;

  static final String LIVE_HOST = "openapi-v2.kucoin.com";
  static final String LIVE_URI = "https://" + LIVE_HOST;

  private void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem(PARAM_SANDBOX).equals(true)) {
        exchangeSpecification.setSslUri(KucoinV2Exchange.SANDBOX_URI);
        exchangeSpecification.setHost(KucoinV2Exchange.SANDBOX_HOST);
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
    this.marketDataService = new KucoinV2MarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(LIVE_URI);
    exchangeSpecification.setHost(LIVE_HOST);
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kucoin");
    exchangeSpecification.setExchangeDescription("Kucoin is a bitcoin and altcoin exchange.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Kucoin does not use a nonce factory.");
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    this.exchangeMetaData = KucoinV2Adapters.adaptMetadata(
      this.exchangeMetaData,
      getMarketDataService().getKucoinSymbols()
    );
  }

  @Override
  public KucoinV2MarketDataService getMarketDataService() {
    return (KucoinV2MarketDataService) super.getMarketDataService();
  }

  @Override
  public TradeService getTradeService() {
    throw new NotYetImplementedForExchangeException("Trade service not yet implemented. Monitor https://github.com/knowm/XChange/issues/2914 for progress.");
  }

  @Override
  public AccountService getAccountService() {
    throw new NotYetImplementedForExchangeException("Account service not yet implemented. Monitor https://github.com/knowm/XChange/issues/2914 for progress.");
  }
}