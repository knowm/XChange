package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.kraken.KrakenAuthenticated;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssets;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderFlags;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

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

    kraken =
        ExchangeRestProxyBuilder.forInterface(
                KrakenAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        KrakenDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public KrakenServerTime getServerTime() throws IOException {

    KrakenServerTimeResult timeResult = kraken.getServerTime();

    return checkResult(timeResult);
  }

  public KrakenAssets getKrakenAssets(Currency... assets) throws IOException {

    KrakenAssetsResult assetPairsResult = kraken.getAssets(null, delimitAssets(assets));

    return new KrakenAssets(checkResult(assetPairsResult));
  }

  /**
   * For more info on each error codes
   *
   * <p>https://support.kraken.com/hc/en-us/articles/360001491786-API-Error-Codes
   */
  public <R> R checkResult(KrakenResult<R> krakenResult) {

    if (!krakenResult.isSuccess()) {
      String[] errors = krakenResult.getError();
      if (errors.length == 0 || errors[0] == null) {
        throw new ExchangeException("Missing error message");
      }
      String error = errors[0];
      switch (error) {
        case "EAPI:Invalid nonce":
          throw new NonceException(error);
        case "EGeneral:Temporary lockout":
          throw new FrequencyLimitExceededException(error);
        case "EOrder:Insufficient funds":
          throw new FundsExceededException(error);
        case "EGeneral:Too many requests":
        case "EAPI:Rate limit exceeded":
        case "EOrder:Rate limit exceeded":
          throw new RateLimitExceededException(error);
        case "EService:Unavailable":
        case "EService:Busy":
          throw new ExchangeUnavailableException(error);
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
        commaDelimitedAssets
            .append((started) ? "," : "")
            .append(KrakenUtils.getKrakenCurrencyCode(asset));
        started = true;
      }

      return commaDelimitedAssets.toString();
    }

    return null;
  }

  protected String delimitAssetPairs(CurrencyPair[] currencyPairs) throws IOException {
    return currencyPairs != null && currencyPairs.length > 0
        ? Arrays.stream(currencyPairs)
            .map(KrakenUtils::createKrakenCurrencyPair)
            .filter(Objects::nonNull)
            .collect(Collectors.joining(","))
        : null;
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

  protected int getAssetPairScale(Instrument instrument) throws IOException {
    // get decimal precision scale
    CurrencyPair cp = (CurrencyPair) instrument;
    Map<String, KrakenAssetPair> assetPairMap = kraken.getAssetPairs(cp.toString()).getResult();
    KrakenAssetPair assetPair = assetPairMap.get(cp.toString());
    int scale = assetPair.getPairScale();
    return scale;
  }
}
