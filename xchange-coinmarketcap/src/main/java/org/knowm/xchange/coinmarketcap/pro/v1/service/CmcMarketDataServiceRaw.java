package org.knowm.xchange.coinmarketcap.pro.v1.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcErrorAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcTicker;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyInfoResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcCurrencyMapResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerListResponse;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response.CmcTickerResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.HttpStatusIOException;

class CmcMarketDataServiceRaw extends CmcBaseService {

  public CmcMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CmcCurrencyInfo getCmcCurrencyInfo(Currency currency) throws IOException {

    String currencyCode = currency.getCurrencyCode();

    CmcCurrencyInfoResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyInfo(apiKey, currencyCode);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData().get(currencyCode);
  }

  public Map<String, CmcCurrencyInfo> getCmcMultipleCurrencyInfo(List<Currency> currencyList)
      throws IOException {

    List<String> currencyCodes =
        currencyList.stream().map(Currency::getCurrencyCode).collect(Collectors.toList());
    String commaSeparatedCurrencyCodes = StringUtils.join(currencyCodes, ",");

    CmcCurrencyInfoResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyInfo(apiKey, commaSeparatedCurrencyCodes);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcCurrency> getCmcCurrencyList() throws IOException {

    CmcCurrencyMapResponse response = null;
    try {
      response = cmcAuthenticated.getCurrencyMap(apiKey, "active", 1, 5000, "id");
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcTicker> getCmcLatestDataForAllCurrencies() throws IOException {

    CmcTickerListResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestListing(
              apiKey, 1, 5000, Currency.USD.getCurrencyCode(), "symbol", "asc", "all");
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public List<CmcTicker> getCmcLatestDataForAllCurrencies(
      int startIndex,
      int limitIndex,
      String currencyCounters,
      String sortByField,
      String sortDirection,
      String currencyType)
      throws IOException {

    CmcTickerListResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestListing(
              apiKey,
              startIndex,
              limitIndex,
              currencyCounters,
              sortByField,
              sortDirection,
              currencyType);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public Map<String, CmcTicker> getCmcLatestQuote(CurrencyPair currencyPair) throws IOException {

    CmcTickerResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestQuotes(
              apiKey, currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }

  public Map<String, CmcTicker> getCmcLatestQuotes(
      Set<Currency> baseCurrencySet, Set<Currency> convertCurrencySet) throws IOException {

    List<String> baseSymbols =
        baseCurrencySet.stream().map(c -> c.getCurrencyCode()).collect(Collectors.toList());
    String commaSeparatedBaseSymbols = StringUtils.join(baseSymbols, ",");
    List<String> convertSymbols =
        convertCurrencySet.stream().map(c -> c.getCurrencyCode()).collect(Collectors.toList());
    String commaSeparatedConvertCurrencies = StringUtils.join(convertSymbols, ",");

    CmcTickerResponse response = null;
    try {
      response =
          cmcAuthenticated.getLatestQuotes(
              apiKey, commaSeparatedBaseSymbols, commaSeparatedConvertCurrencies);
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return response.getData();
  }
}
