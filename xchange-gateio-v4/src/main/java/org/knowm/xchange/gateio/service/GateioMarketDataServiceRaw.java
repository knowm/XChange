package org.knowm.xchange.gateio.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.gateio.GateioResilience.*;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  public GateioMarketDataServiceRaw(GateioExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public GateioServerTime getGateioServerTime() throws IOException {
    return gateio.getServerTime();
  }

  public List<GateioTicker> getGateioTickers(Instrument instrument) throws IOException {
    return decorateApiCall(
        () -> gateio.getTickers(GateioAdapters.toGateioInstrument(instrument)))
        .withRateLimiter(rateLimiter(TICKERS_RATE_LIMITER))
        .call();
  }

  public List<GateioFuturesTickerAndFunding> getGateioFuturesTickers(Instrument instrument)
      throws IOException {
    String settle = "usdt";
    return decorateApiCall(
        () -> gateio.getFuturesTickers(settle, GateioAdapters.toGateioInstrument(instrument)))
        .withRateLimiter(rateLimiter(TICKERS_RATE_LIMITER))
        .call();
  }

  public List<GateioCurrencyInfo> getGateioCurrencyInfos() throws IOException {
    return gateio.getCurrencies();
  }

  public GateioOrderBook getGateioOrderBook(Instrument instrument) throws IOException {
    return gateio.getOrderBook(GateioAdapters.toGateioInstrument(instrument), false);
  }

  public List<GateioCurrencyChain> getCurrencyChains(Currency currency) throws IOException {
    return gateio.getCurrencyChains(currency.getCurrencyCode());
  }

  public List<GateioCurrencyPairDetails> getCurrencyPairDetails() throws IOException {
    return gateio.getCurrencyPairDetails();
  }

  public List<GateioInstrumentDetails> getInstrumentDetails() throws IOException {
    return gateio.getInstrumentDetails();
  }

  public GateioCurrencyPairDetails getCurrencyPairDetails(Instrument instrument)
      throws IOException {
    return gateio.getCurrencyPairDetails(GateioAdapters.toGateioInstrument(instrument));
  }

  public List<GateioSpotCandlestick> getGateioSpotCandlesticks(
      Instrument instrument, Integer limit, Long from, Long to, String interval)
      throws IOException {
    return decorateApiCall(
        () ->
            gateio.getSpotCandlesticks(
                GateioAdapters.toGateioInstrument(instrument), limit, from, to, interval))
        .withRateLimiter(rateLimiter(CANDLESTICK_DATA_RATE_LIMITER))
        .call();
  }

  public List<GateioFuturesCandlestick> getGateioFuturesCandlesticks(
      Instrument instrument, Integer limit, Long from, Long to, String interval)
      throws IOException {
    return decorateApiCall(
        () ->
            gateio.getFuturesCandlesticks(instrument.getCounter().toString().toLowerCase(),
                GateioAdapters.toGateioInstrument(instrument), limit, from, to, interval))
        .withRateLimiter(rateLimiter(CANDLESTICK_DATA_RATE_LIMITER))
        .call();
  }

  public List<GateioFundingRateHistory> getGateioFundingRateHistory(
      Instrument instrument, Long startTime, Long endTime, Integer limit)
      throws IOException {
    return decorateApiCall(
        () -> gateio.getFundingRateHistory(
            instrument.getCounter().toString().toLowerCase(),
            GateioAdapters.toGateioInstrument(instrument),
            limit,
            startTime,
            endTime))
        .withRateLimiter(rateLimiter(FUNDING_HISTORY_RATE_LIMITER))
        .call();
  }
}
