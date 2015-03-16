package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.FrequencyLimitExceededException;
import com.xeiam.xchange.exceptions.NonceException;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssets;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenServerTime;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import com.xeiam.xchange.kraken.service.KrakenDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class KrakenBasePollingService extends BaseExchangeService implements BasePollingService {

  //  protected static final String PREFIX = "kraken";
  //  protected static final String KEY_ORDER_SIZE_MIN_DEFAULT = PREFIX + SUF_ORDER_SIZE_MIN_DEFAULT;

  private final Set<String> FIAT_CURRENCIES = new HashSet<String>();
  private final Set<String> DIGITAL_CURRENCIES = new HashSet<String>();

  protected KrakenAuthenticated kraken;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenBasePollingService(Exchange exchange) {

    super(exchange);

    kraken = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    final Set<String> krakenCurrencyPairs = getKrakenAssetPairs().getAssetPairMap().keySet();
    for (String krakenCurrencyPair : krakenCurrencyPairs) {
      String krakenTradeCurrency = krakenCurrencyPair.substring(0, 4);
      String krakenPriceCurrency = krakenCurrencyPair.substring(4);

      String tradeCurrency = addCurrencyAndGetCode(krakenTradeCurrency);
      String priceCurrency = addCurrencyAndGetCode(krakenPriceCurrency);

      currencyPairs.add(new CurrencyPair(tradeCurrency, priceCurrency));
    }
    return currencyPairs;
  }

  private String addCurrencyAndGetCode(String krakenCurrencyString) {

    String currencyCode = KrakenAdapters.adaptCurrency(krakenCurrencyString);
    if (krakenCurrencyString.startsWith("X")) {
      DIGITAL_CURRENCIES.add(currencyCode);
    } else {
      FIAT_CURRENCIES.add(currencyCode);
    }

    return currencyCode;
  }

  protected String createKrakenCurrencyPair(CurrencyPair currencyPair) throws IOException {

    return createKrakenCurrencyPair(currencyPair.baseSymbol, currencyPair.counterSymbol);
  }

  protected String createKrakenCurrencyPair(String tradableIdentifier, String currency) throws IOException {

    return getKrakenCurrencyCode(tradableIdentifier) + getKrakenCurrencyCode(currency);
  }

  protected String getKrakenCurrencyCode(String currency) throws IOException {

    if (FIAT_CURRENCIES.isEmpty()) {
      getExchangeSymbols();
    }

    if (FIAT_CURRENCIES.contains(currency)) {
      return "Z" + currency;
    } else if (DIGITAL_CURRENCIES.contains(currency)) {
      if (currency.equals(Currencies.BTC)) {
        return "XXBT";
      }
      if (currency.equals(Currencies.DOGE)) {
        return "XXDG";
      }

      return "X" + currency;
    }

    throw new ExchangeException("Kraken does not support the currency code " + currency);
  }

  public KrakenServerTime getServerTime() throws IOException {

    KrakenServerTimeResult timeResult = kraken.getServerTime();

    return checkResult(timeResult);
  }

  public KrakenAssets getKrakenAssets(String... assets) throws IOException {

    KrakenAssetsResult assetPairsResult = kraken.getAssets(null, delimitAssets(assets));

    return new KrakenAssets(checkResult(assetPairsResult));
  }

  public KrakenAssetPairs getKrakenAssetPairs(CurrencyPair... currencyPairs) throws IOException {

    KrakenAssetPairsResult assetPairsResult = kraken.getAssetPairs(delimitAssetPairs(currencyPairs));

    return new KrakenAssetPairs(checkResult(assetPairsResult));
  }

  protected <R> R checkResult(KrakenResult<R> krakenResult) {

    if (!krakenResult.isSuccess()) {
      String[] errors = krakenResult.getError();
      if (errors.length == 0) {
        throw new ExchangeException("Missing error message");
      }
      String error = errors[0];
      if ("EAPI:Invalid nonce".equals(error)) {
        throw new NonceException(error);
      } else if ("EGeneral:Temporary lockout".equals(error)) {
        throw new FrequencyLimitExceededException(error);
      }

      throw new ExchangeException(Arrays.toString(errors));
    }

    return krakenResult.getResult();
  }

  protected String createDelimitedString(String[] items) {

    StringBuilder commaDelimitedString = null;
    if (items != null) {
      for (String item : items) {
        if (commaDelimitedString == null) {
          commaDelimitedString = new StringBuilder(item);
        } else {
          commaDelimitedString.append(",").append(item);
        }
      }
    }

    return (commaDelimitedString == null) ? null : commaDelimitedString.toString();
  }

  protected String delimitAssets(String[] assets) throws IOException {

    StringBuilder commaDelimitedAssets = new StringBuilder();
    if (assets != null && assets.length > 0) {
      boolean started = false;
      for (String asset : assets) {
        commaDelimitedAssets.append((started) ? "," : "").append(getKrakenCurrencyCode(asset));
        started = true;
      }

      return commaDelimitedAssets.toString();
    }

    return null;
  }

  protected String delimitAssetPairs(CurrencyPair[] currencyPairs) throws IOException {

    String assetPairsString = null;
    if (currencyPairs != null && currencyPairs.length > 0) {
      StringBuilder delimitStringBuilder = null;
      for (CurrencyPair currencyPair : currencyPairs) {
        String krakenAssetPair = createKrakenCurrencyPair(currencyPair);
        if (delimitStringBuilder == null) {
          delimitStringBuilder = new StringBuilder(krakenAssetPair);
        } else {
          delimitStringBuilder.append(",").append(krakenAssetPair);
        }
      }
      assetPairsString = delimitStringBuilder.toString();
    }

    return assetPairsString;
  }

  protected String delimitSet(Set<?> items) {

    String delimitedSetString = null;
    if (items != null && !items.isEmpty()) {
      StringBuilder delimitStringBuilder = null;
      for (Object item : items) {
        if (delimitStringBuilder == null) {
          delimitStringBuilder = new StringBuilder(item.toString());
        } else {
          delimitStringBuilder.append(",").append(item.toString());
        }
      }
    }
    return delimitedSetString;
  }

}
