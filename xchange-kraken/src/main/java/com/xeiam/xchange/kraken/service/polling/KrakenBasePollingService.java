/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.Kraken;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssets;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenServerTime;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import com.xeiam.xchange.kraken.service.KrakenBaseService;
import com.xeiam.xchange.kraken.service.KrakenDigest;

public class KrakenBasePollingService<T extends Kraken> extends KrakenBaseService {

  private final Set<CurrencyPair> CURRENCY_PAIRS = new HashSet<CurrencyPair>();
  private final Set<String> FIAT_CURRENCIES = new HashSet<String>();
  private final Set<String> DIGITAL_CURRENCIES = new HashSet<String>();

  protected T kraken;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public KrakenBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    kraken = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (CURRENCY_PAIRS.isEmpty()) {
      final Set<String> krakenCurrencyPairs = getKrakenAssetPairs().getAssetPairMap().keySet();
      for (final String krakenCurrencyPair : krakenCurrencyPairs) {
        String krakenTradeCurrency = krakenCurrencyPair.substring(0, 4);
        String krakenPriceCurrency = krakenCurrencyPair.substring(4);

        String tradeCurrency = addCurrencyAndGetCode(krakenTradeCurrency);
        String priceCurrency = addCurrencyAndGetCode(krakenPriceCurrency);

        CURRENCY_PAIRS.add(new CurrencyPair(tradeCurrency, priceCurrency));
      }
    }
    return CURRENCY_PAIRS;
  }

  private String addCurrencyAndGetCode(String krakenCurrencyString) {

    String currencyCode = KrakenAdapters.adaptCurrency(krakenCurrencyString);
    if (krakenCurrencyString.startsWith("X"))
      DIGITAL_CURRENCIES.add(currencyCode);
    else
      FIAT_CURRENCIES.add(currencyCode);

    return currencyCode;
  }

  protected String createKrakenCurrencyPair(CurrencyPair currencyPair) throws IOException {

    return createKrakenCurrencyPair(currencyPair.baseSymbol, currencyPair.counterSymbol);
  }

  protected String createKrakenCurrencyPair(String tradableIdentifier, String currency) throws IOException {

    return getKrakenCurrencyCode(tradableIdentifier) + getKrakenCurrencyCode(currency);
  }

  protected String getKrakenCurrencyCode(String currency) throws IOException {

    if (FIAT_CURRENCIES.isEmpty())
      getExchangeSymbols();

    if (FIAT_CURRENCIES.contains(currency))
      return "Z" + currency;
    else if (DIGITAL_CURRENCIES.contains(currency)) {
      if (currency.equals(Currencies.BTC))
        return "XXBT";

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
      throw new ExchangeException(Arrays.toString(krakenResult.getError()));
    }

    return krakenResult.getResult();
  }

  protected long nextNonce() {

    return System.currentTimeMillis();
  }

  protected String createDelimitedString(String[] items) {

    StringBuilder commaDelimitedString = null;
    if (items != null) {
      for (String item : items) {
        if (commaDelimitedString == null) {
          commaDelimitedString = new StringBuilder(item);
        }
        else {
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
        }
        else {
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
        }
        else {
          delimitStringBuilder.append(",").append(item.toString());
        }
      }
    }
    return delimitedSetString;
  }

}
