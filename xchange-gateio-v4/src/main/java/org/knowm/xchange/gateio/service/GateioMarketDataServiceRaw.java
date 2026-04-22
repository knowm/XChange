package org.knowm.xchange.gateio.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.util.List;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  public GateioMarketDataServiceRaw(GateioExchange exchange) {
    super(exchange);
  }

  public GateioServerTime getGateioServerTime() throws IOException {
    return gateio.getServerTime();
  }

  public List<GateioTicker> getGateioTickers(Instrument instrument) throws IOException {
    return gateio.getTickers(GateioAdapters.toGateioInstrument(instrument));
  }

  public List<GateioFuturesTicker> getGateioFuturesTickers(Instrument instrument)
      throws IOException {
    String settle = "usdt";
    return gateio.getFuturesTickers(settle, GateioAdapters.toGateioInstrument(instrument));
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
    return gateio.getSpotCandlesticks(
        GateioAdapters.toGateioInstrument(instrument), limit, from, to, interval);
  }

  public List<GateioFuturesCandlestick> getGateioFuturesCandlesticks(
      Instrument instrument, Integer limit, Long from, Long to, String interval)
      throws IOException {
    return gateio.getFuturesCandlesticks(instrument.getCounter().toString().toLowerCase(),
        GateioAdapters.toGateioInstrument(instrument), limit, from, to, interval);
  }
}
