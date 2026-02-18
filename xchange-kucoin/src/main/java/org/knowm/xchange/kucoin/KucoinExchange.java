package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.kucoin.dto.response.KucoinCurrencyResponseV3;
import org.knowm.xchange.kucoin.dto.response.SymbolResponse;
import org.knowm.xchange.kucoin.dto.response.WebsocketResponse;

public class KucoinExchange extends BaseExchange implements Exchange {

  static final String SANDBOX_URI = "https://openapi-sandbox.kucoin.com";
  static final String PROD_URI = "https://api.kucoin.com";

  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  protected void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
        logger.debug("Connecting to sandbox");
        exchangeSpecification.setSslUri(KucoinExchange.SANDBOX_URI);
        try {
          URL url = new URL(KucoinExchange.SANDBOX_URI);
          exchangeSpecification.setHost(url.getHost());
        } catch (MalformedURLException exception) {
          logger.error("Kucoin sandbox host exception: {}", exception.getMessage());
        }
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
    this.marketDataService = new KucoinMarketDataService(this, getResilienceRegistries());
    this.accountService = new KucoinAccountService(this, getResilienceRegistries());
    this.tradeService = new KucoinTradeService(this, getResilienceRegistries());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri(PROD_URI);
    try {
      URL url = new URL(KucoinExchange.PROD_URI);
      exchangeSpecification.setHost(url.getHost());
    } catch (MalformedURLException exception) {
      logger.error("Kucoin host exception: {}", exception.getMessage());
    }
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kucoin");
    exchangeSpecification.setExchangeDescription("Kucoin is a bitcoin and altcoin exchange.");
    return exchangeSpecification;
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = KucoinResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    List<SymbolResponse> symbols = getMarketDataService().getKucoinSymbolsV2();

    Map<Instrument, InstrumentMetaData> instruments =
        symbols.stream()
            .collect(
                Collectors.toMap(
                    SymbolResponse::getCurrencyPair, KucoinAdapters::toInstrumentMetaData));

    List<KucoinCurrencyResponseV3> currencies = getMarketDataService().getAllKucoinCurrencies();

    Map<Currency, CurrencyMetaData> currencyMetaData =
        currencies.stream()
            .collect(
                Collectors.toMap(
                    KucoinCurrencyResponseV3::getCurrency, KucoinAdapters::toCurrencyMetaData));

    exchangeMetaData.setInstruments(instruments);
    exchangeMetaData.setCurrencies(currencyMetaData);
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

  public WebsocketResponse getPublicWebsocketConnectionDetails() throws IOException {
    return classifyingExceptions(getAccountService().websocketAPI::getPublicWebsocketDetails);
  }

  public WebsocketResponse getPrivateWebsocketConnectionDetails() throws IOException {
    getAccountService().checkAuthenticated();

    return classifyingExceptions(
        () ->
            getAccountService()
                .websocketAPI
                .getPrivateWebsocketDetails(
                    getAccountService().apiKey,
                    getAccountService().digest,
                    getAccountService().nonceFactory,
                    getAccountService().passphrase));
  }
}
