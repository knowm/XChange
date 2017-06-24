package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.kraken.KrakenAuthenticated;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssets;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderFlags;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class KrakenBaseService extends BaseExchangeService implements BaseService {

  protected KrakenAuthenticated kraken;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenBaseService(Exchange exchange) {

    super(exchange);

    ClientConfig rescuConfig = new ClientConfig(); // default rescu config

    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }
    
    kraken = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
    signatureCreator = KrakenDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public KrakenServerTime getServerTime() throws IOException {

    KrakenServerTimeResult timeResult = kraken.getServerTime();

    return checkResult(timeResult);
  }

  public KrakenAssets getKrakenAssets(Currency... assets) throws IOException {

    KrakenAssetsResult assetPairsResult = kraken.getAssets(null, delimitAssets(assets));

    return new KrakenAssets(checkResult(assetPairsResult));
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

  protected String delimitAssets(Currency[] assets) throws IOException {

    StringBuilder commaDelimitedAssets = new StringBuilder();
    if (assets != null && assets.length > 0) {
      boolean started = false;
      for (Currency asset : assets) {
        commaDelimitedAssets.append((started) ? "," : "").append(KrakenUtils.getKrakenCurrencyCode(asset));
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
        String krakenAssetPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
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

  protected String delimitSet(Set<IOrderFlags> items) {

    String delimitedSetString = null;
    if (items != null && !items.isEmpty()) {
      StringBuilder delimitStringBuilder = null;
      for (Object item : items) {
        if (item instanceof KrakenOrderFlags) {
          if (delimitStringBuilder == null) {
            delimitStringBuilder = new StringBuilder(item.toString());
          } else {
            delimitStringBuilder.append(",").append(item.toString());
          }
        }
      }
      if (delimitStringBuilder != null) {
        delimitedSetString = delimitStringBuilder.toString();
      }
    }
    return delimitedSetString;
  }

}
