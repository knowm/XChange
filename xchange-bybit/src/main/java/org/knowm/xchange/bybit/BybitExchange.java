package org.knowm.xchange.bybit;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitMarketDataServiceRaw;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class BybitExchange extends BaseExchange {

  public static final String SPECIFIC_PARAM_ACCOUNT_TYPE = "accountType";
  private static final String BASE_URL = "https://api.bybit.com";
//  private static final String DEMO_URL = "https://api-demo.bybit.com";
  private static final String TESTNET_URL = "https://api-testnet.bybit.com";


  @Override
  protected void initServices() {
    marketDataService = new BybitMarketDataService(this);
    tradeService = new BybitTradeService(this);
    accountService =
        new BybitAccountService(
            this,
            (BybitAccountType)
                getExchangeSpecification()
                    .getExchangeSpecificParametersItem(SPECIFIC_PARAM_ACCOUNT_TYPE));
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri(BASE_URL);
    exchangeSpecification.setHost("bybit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bybit");
    exchangeSpecification.setExchangeDescription("BYBIT");
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(
        Exchange.USE_SANDBOX, false);
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    ((BybitMarketDataServiceRaw) marketDataService)
        .getInstrumentsInfo(BybitCategory.SPOT)
        .getResult()
        .getList()
        .forEach(
            instrumentInfo ->
                exchangeMetaData
                    .getInstruments()
                    .put(
                        BybitAdapters.adaptInstrumentInfo(instrumentInfo),
                        BybitAdapters.symbolToCurrencyPairMetaData(
                            (BybitSpotInstrumentInfo) instrumentInfo)));

    ((BybitMarketDataServiceRaw) marketDataService)
        .getInstrumentsInfo(BybitCategory.LINEAR)
        .getResult()
        .getList()
        .forEach(
            instrumentInfo ->
                exchangeMetaData
                    .getInstruments()
                    .put(
                        BybitAdapters.adaptInstrumentInfo(instrumentInfo),
                        BybitAdapters.symbolToCurrencyPairMetaData(
                            (BybitLinearInverseInstrumentInfo) instrumentInfo)));

    ((BybitMarketDataServiceRaw) marketDataService)
        .getInstrumentsInfo(BybitCategory.INVERSE)
        .getResult()
        .getList()
        .forEach(
            instrumentInfo ->
                exchangeMetaData
                    .getInstruments()
                    .put(
                        BybitAdapters.adaptInstrumentInfo(instrumentInfo),
                        BybitAdapters.symbolToCurrencyPairMetaData(
                            (BybitLinearInverseInstrumentInfo) instrumentInfo)));

    ((BybitMarketDataServiceRaw) marketDataService)
        .getInstrumentsInfo(BybitCategory.OPTION)
        .getResult()
        .getList()
        .forEach(
            instrumentInfo ->
                exchangeMetaData
                    .getInstruments()
                    .put(
                        BybitAdapters.adaptInstrumentInfo(instrumentInfo),
                        BybitAdapters.symbolToCurrencyPairMetaData(
                            (BybitOptionInstrumentInfo) instrumentInfo)));
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification
        .getExchangeSpecificParametersItem(Exchange.USE_SANDBOX)
        .equals(true)) {
      exchangeSpecification.setSslUri(TESTNET_URL);
    }
    super.applySpecification(exchangeSpecification);
  }
}
