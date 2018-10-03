package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepth;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepthWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepthsWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTicker;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTickerWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTickersWrapper;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.IRestProxyFactory;

public class ANXMarketDataServiceRaw extends ANXBaseService {

  private final ANXV2 anxV2;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected ANXMarketDataServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange);

    Assert.notNull(
        exchange.getExchangeSpecification().getSslUri(),
        "Exchange specification URI cannot be null");
    this.anxV2 =
        restProxyFactory.createProxy(
            ANXV2.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public ANXTicker getANXTicker(CurrencyPair currencyPair) throws IOException {

    try {
      ANXTickerWrapper anxTickerWrapper =
          anxV2.getTicker(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return anxTickerWrapper.getAnxTicker();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public Map<String, ANXTicker> getANXTickers(Collection<CurrencyPair> currencyPairs)
      throws IOException {

    CurrencyPair pathCurrencyPair = null;
    StringBuilder extraCurrencyPairs = new StringBuilder();
    int i = 1;
    for (CurrencyPair currencyPair : currencyPairs) {
      if (i++ == currencyPairs.size()) {
        pathCurrencyPair = currencyPair;
      } else {
        extraCurrencyPairs
            .append(currencyPair.base.getCurrencyCode())
            .append(currencyPair.counter.getCurrencyCode())
            .append(",");
      }
    }
    if (pathCurrencyPair == null) {
      return null;
    }
    try {
      if (i == 2) {
        ANXTicker anxTicker = getANXTicker(pathCurrencyPair);
        Map<String, ANXTicker> ticker = new HashMap<>();
        ticker.put(
            pathCurrencyPair.base.getCurrencyCode() + pathCurrencyPair.counter.getCurrencyCode(),
            anxTicker);
        return ticker;
      }
      ANXTickersWrapper anxTickerWrapper =
          anxV2.getTickers(
              pathCurrencyPair.base.getCurrencyCode(),
              pathCurrencyPair.counter.getCurrencyCode(),
              extraCurrencyPairs.toString());
      return anxTickerWrapper.getAnxTickers();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXDepthWrapper getANXFullOrderBook(CurrencyPair currencyPair) throws IOException {

    try {
      ANXDepthWrapper anxDepthWrapper =
          anxV2.getFullDepth(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return anxDepthWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public Map<String, ANXDepth> getANXFullOrderBooks(Collection<CurrencyPair> currencyPairs)
      throws IOException {

    CurrencyPair pathCurrencyPair = null;
    StringBuilder extraCurrencyPairs = new StringBuilder();
    int i = 1;
    for (CurrencyPair currencyPair : currencyPairs) {
      if (i++ == currencyPairs.size()) {
        pathCurrencyPair = currencyPair;
      } else {
        extraCurrencyPairs
            .append(currencyPair.base.getCurrencyCode())
            .append(currencyPair.counter.getCurrencyCode())
            .append(",");
      }
    }
    if (pathCurrencyPair == null) {
      return null;
    }
    try {
      if (i == 2) {
        ANXDepthWrapper anxDepthWrapper = getANXFullOrderBook(pathCurrencyPair);
        Map<String, ANXDepth> book = new HashMap<>();
        book.put(
            pathCurrencyPair.base.getCurrencyCode() + pathCurrencyPair.counter.getCurrencyCode(),
            anxDepthWrapper.getAnxDepth());
        return book;
      }
      ANXDepthsWrapper anxDepthWrapper =
          anxV2.getFullDepths(
              pathCurrencyPair.base.getCurrencyCode(),
              pathCurrencyPair.counter.getCurrencyCode(),
              extraCurrencyPairs.toString());
      return anxDepthWrapper.getAnxDepths();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXDepthWrapper getANXPartialOrderBook(CurrencyPair currencyPair) throws IOException {

    try {
      ANXDepthWrapper anxDepthWrapper =
          anxV2.getPartialDepth(
              currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
      return anxDepthWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public List<ANXTrade> getANXTrades(CurrencyPair currencyPair, Long sinceTimeStamp)
      throws IOException {

    try {
      return anxV2
          .getTrades(
              currencyPair.base.getCurrencyCode(),
              currencyPair.counter.getCurrencyCode(),
              sinceTimeStamp)
          .getANXTrades();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
