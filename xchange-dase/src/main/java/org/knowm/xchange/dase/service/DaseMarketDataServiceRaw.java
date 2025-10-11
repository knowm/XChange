package org.knowm.xchange.dase.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseV1;
import org.knowm.xchange.dase.DaseV1.DaseTradesResponse;
import org.knowm.xchange.dase.dto.DaseApiException;
import org.knowm.xchange.dase.dto.marketdata.DaseCandlesResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketsResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseOrderBookSnapshot;
import org.knowm.xchange.dase.dto.marketdata.DaseTicker;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class DaseMarketDataServiceRaw extends BaseExchangeService<Exchange> implements BaseService {

  protected final DaseV1 dase;

  public DaseMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.dase =
        ExchangeRestProxyBuilder.forInterface(DaseV1.class, exchange.getExchangeSpecification())
            .build();
  }

  public List<DaseMarketConfig> getMarkets() throws IOException {
    try {
      DaseMarketsResponse resp = dase.getMarkets();
      return resp == null || resp.markets == null ? Collections.emptyList() : resp.markets;
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseMarketConfig getMarket(String market) throws IOException {
    try {
      return dase.getMarket(market);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseTicker getTicker(String market) throws IOException {
    try {
      return dase.getTicker(market);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseOrderBookSnapshot getSnapshot(String market) throws IOException {
    try {
      return dase.getSnapshot(market);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public List<DaseTrade> getTrades(String market, Integer limit, String before) throws IOException {
    try {
      DaseTradesResponse resp = dase.getTrades(market, limit, before);
      return resp == null || resp.trades == null ? Collections.emptyList() : resp.trades;
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseCandlesResponse getCandles(String market) throws IOException {
    try {
      // default granularity and full range left to server if unspecified
      return dase.getCandles(market, null, null, null);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public DaseCandlesResponse getCandles(String market, String granularity, Long from, Long to)
      throws IOException {
    try {
      return dase.getCandles(market, granularity, from, to);
    } catch (DaseApiException e) {
      throw e.toExchangeException();
    }
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    List<CurrencyPair> out = new ArrayList<>();
    for (DaseMarketConfig mc : getMarkets()) {
      if (mc == null || mc.market == null) {
        continue;
      }
      String[] parts = mc.market.split("-");
      if (parts.length == 2) {
        out.add(new CurrencyPair(parts[0], parts[1]));
      }
    }
    return out;
  }
}
