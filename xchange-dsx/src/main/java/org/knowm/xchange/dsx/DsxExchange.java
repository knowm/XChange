package org.knowm.xchange.dsx;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.DsxMetaData;
import org.knowm.xchange.dsx.dto.DsxSymbol;
import org.knowm.xchange.dsx.service.DsxAccountService;
import org.knowm.xchange.dsx.service.DsxMarketDataService;
import org.knowm.xchange.dsx.service.DsxMarketDataServiceRaw;
import org.knowm.xchange.dsx.service.DsxTradeService;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DsxExchange extends BaseExchange implements Exchange {

  private static final Logger LOGGER = LoggerFactory.getLogger(DsxExchange.class);

  static {
    setupPatchSupport();
  }

  private DsxMetaData dsxMetaData;

  private static void setupPatchSupport() {

    try {
      Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
      methodsField.setAccessible(true);
      // get the methods field modifiers
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      // bypass the "private" modifier
      modifiersField.setAccessible(true);

      // remove the "final" modifier
      modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

      /* valid HTTP methods */
      String[] methods = {"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"};
      // set the new methods - including patch
      methodsField.set(null, methods);
    } catch (SecurityException
            | IllegalArgumentException
            | IllegalAccessException
            | NoSuchFieldException e) {
      LOGGER.error("Error while setting up PATCH support");
    }
  }

  @Override
  protected void initServices() {

    marketDataService = new DsxMarketDataService(this);
    tradeService = new DsxTradeService(this);
    accountService = new DsxAccountService(this);
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {

    dsxMetaData = loadMetaData(is, DsxMetaData.class);
    exchangeMetaData =
            DsxAdapters.adaptToExchangeMetaData(
                    null, dsxMetaData.getCurrencies(), dsxMetaData.getCurrencyPairs());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.dsxglobal.com/");
    exchangeSpecification.setHost("dsx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("dsx");
    exchangeSpecification.setExchangeDescription("Dsx is a Bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException {
    DsxMarketDataServiceRaw dataService = ((DsxMarketDataServiceRaw) marketDataService);
    List<DsxSymbol> dsxSymbols = dataService.getDsxSymbols();
    Map<Currency, CurrencyMetaData> currencies =
            dataService.getDsxCurrencies().stream()
                    .collect(
                            Collectors.toMap(
                                    dsxCurrency -> new Currency(dsxCurrency.getId()),
                                    dsxCurrency -> new CurrencyMetaData(null, dsxCurrency.getPayoutFee())));

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
            dsxSymbols.stream()
                    .collect(
                            Collectors.toMap(
                                    dsxSymbol ->
                                            new CurrencyPair(
                                                    new Currency(dsxSymbol.getBaseCurrency()),
                                                    new Currency(dsxSymbol.getQuoteCurrency())),
                                    dsxSymbol ->
                                            new CurrencyPairMetaData(
                                                    null,
                                                    dsxSymbol.getQuantityIncrement(),
                                                    null,
                                                    dsxSymbol.getTickSize().scale(),
                                                    null)));
    exchangeMetaData = DsxAdapters.adaptToExchangeMetaData(dsxSymbols, currencies, currencyPairs);
  }
}