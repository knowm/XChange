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


  public List<GateioTicker> getGateioTickers(Instrument instrument) throws IOException {
    return gateio.getTickers(GateioAdapters.toString(instrument));
  }


  public List<GateioCurrencyInfo> getCurrencies() throws IOException {
    return gateio.getCurrencies();
  }


  public GateioOrderBook getGateioOrderBook(Instrument instrument) throws IOException {
    return gateio.getOrderBook(GateioAdapters.toString(instrument), false);
  }


  public List<GateioCurrencyChain> getCurrencyChains(Currency currency) throws IOException {
    return gateio.getCurrencyChains(currency.getCurrencyCode());
  }


  public List<GateioCurrencyPairDetails> getCurrencyPairDetails() throws IOException {
      return gateio.getCurrencyPairDetails();
  }


  public GateioCurrencyPairDetails getCurrencyPairDetails(Instrument instrument) throws IOException {
    return gateio.getCurrencyPairDetails(GateioAdapters.toString(instrument));
  }


}
