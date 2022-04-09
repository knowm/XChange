package org.knowm.xchange.okex.v5;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.v5.service.OkexAccountService;
import org.knowm.xchange.okex.v5.service.OkexMarketDataService;
import org.knowm.xchange.okex.v5.service.OkexMarketDataServiceRaw;
import org.knowm.xchange.okex.v5.service.OkexTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.okex.v5.service.OkexMarketDataService.SPOT;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexExchange extends BaseExchange {

  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      final boolean useAWS =
          Boolean.TRUE.equals(
              exchangeSpecification.getExchangeSpecificParametersItem(Parameters.PARAM_USE_AWS));
      if (useAWS) {
        exchangeSpecification.setSslUri(
            (String)
                exchangeSpecification.getExchangeSpecificParametersItem(
                    Parameters.PARAM_AWS_SSL_URI));
        exchangeSpecification.setHost(
            (String)
                exchangeSpecification.getExchangeSpecificParametersItem(Parameters.PARAM_AWS_HOST));
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

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.okex.com");
    exchangeSpecification.setHost("okex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Okex");
    exchangeSpecification.setExchangeDescription("Okex Exchange");

    exchangeSpecification.setExchangeSpecificParametersItem(Parameters.PARAM_USE_AWS, false);
    exchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_AWS_SSL_URI, "https://aws.okex.com");
    exchangeSpecification.setExchangeSpecificParametersItem(
        Parameters.PARAM_AWS_HOST, "aws.okex.com");

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

    exchangeMetaData =
        OkexAdapters.adaptToExchangeMetaData(exchangeMetaData, instruments, currencies, tradeFee);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Parameters {
    public static final String PARAM_USE_AWS = "Use_AWS";
    public static final String PARAM_AWS_SSL_URI = "AWSSslUri";
    public static final String PARAM_AWS_HOST = "AWSHost";
  }
}
