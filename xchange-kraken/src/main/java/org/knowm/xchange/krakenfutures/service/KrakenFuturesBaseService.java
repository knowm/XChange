package org.knowm.xchange.krakenfutures.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.*;
import org.knowm.xchange.kraken.service.KrakenDigest;
import org.knowm.xchange.krakenfutures.KrakenFutures;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

import java.io.IOException;
import java.util.Arrays;

public class KrakenFuturesBaseService extends BaseExchangeService implements BaseService {

  protected KrakenFutures kraken;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenFuturesBaseService(Exchange exchange) {

    super(exchange);

    kraken =
        ExchangeRestProxyBuilder.forInterface(
                KrakenFutures.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        KrakenDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
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
        case "apiLimitExceeded":
//        case "EOrder:Rate limit exceeded":
          throw new RateLimitExceededException(error);
        case "unavailable":
//        case "EService:Busy":
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
}
