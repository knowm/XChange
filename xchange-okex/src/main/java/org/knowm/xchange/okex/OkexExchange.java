package org.knowm.xchange.okex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.service.OkexAccountService;
import org.knowm.xchange.okex.service.OkexMarketDataService;
import org.knowm.xchange.okex.service.OkexMarketDataServiceRaw;
import org.knowm.xchange.okex.service.OkexTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.okex.OkexAdapters.SPOT;
import static org.knowm.xchange.okex.OkexAdapters.SWAP;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexExchange extends BaseExchange {

  public static final String PARAM_USE_AWS = "Use_AWS";
  public static final String PARAM_AWS_SSL_URI = "AWSSslUri";
  public static final String PARAM_AWS_HOST = "AWSHost";
  public static final String PARAM_SIMULATED = "simulated";
  public static final String PARAM_PASSPHRASE = "passphrase";
  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      final boolean useAWS =
          Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(PARAM_USE_AWS));
      if (useAWS) {
        exchangeSpecification.setSslUri(
            (String)
                exchangeSpecification.getExchangeSpecificParametersItem(
                    PARAM_AWS_SSL_URI));
        exchangeSpecification.setHost(
            (String)
                exchangeSpecification.getExchangeSpecificParametersItem(PARAM_AWS_HOST));
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

    this.marketDataService = new OkexMarketDataService(this, getResilienceRegistries());
    this.accountService = new OkexAccountService(this, getResilienceRegistries());
    this.tradeService = new OkexTradeService(this, getResilienceRegistries());
  }

  /**
   * For Demo Trading add the following param to exchangeSpecification:
   * exchangeSpecification.setExchangeSpecificParametersItem(PARAM_SIMULATED_TRADING, "1");
   *
   */
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.okx.com");
    exchangeSpecification.setHost("okx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Okex");
    exchangeSpecification.setExchangeDescription("Okx Exchange");

    exchangeSpecification.setExchangeSpecificParametersItem(PARAM_USE_AWS, false);
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_AWS_SSL_URI, "https://aws.okx.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        PARAM_AWS_HOST, "aws.okx.com");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException(
        "Okex uses timestamp rather than a nonce"); // TODO: This
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = OkexResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public void remoteInit() throws IOException {
    List<OkexInstrument> instruments =
        ((OkexMarketDataServiceRaw) marketDataService)
            .getOkexInstruments(SPOT, null, null)
            .getData();

    List<OkexInstrument> swap_instruments =
            ((OkexMarketDataServiceRaw) marketDataService)
                    .getOkexInstruments(SWAP, null, null)
                    .getData();

    instruments.addAll(swap_instruments);

    // Currency data and trade fee is only retrievable through a private endpoint
    List<OkexCurrency> currencies = null;
    List<OkexTradeFee> tradeFee = null;
    if (exchangeSpecification.getApiKey() != null
        && exchangeSpecification.getSecretKey() != null
        && exchangeSpecification.getExchangeSpecificParametersItem("passphrase") != null) {
      currencies = ((OkexMarketDataServiceRaw) marketDataService).getOkexCurrencies().getData();
      String accountLevel =
              ((OkexAccountService) accountService).getOkexAccountConfiguration().getData().get(0).getAccountLevel();
      tradeFee = ((OkexAccountService) accountService).getTradeFee(
              SPOT, null, null, accountLevel).getData();
    }

    exchangeMetaData = OkexAdapters.adaptToExchangeMetaData(instruments, currencies, tradeFee);
  }

  protected boolean useSandbox(){
    return Boolean.TRUE.equals(
            exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX)
    );
  }
}
