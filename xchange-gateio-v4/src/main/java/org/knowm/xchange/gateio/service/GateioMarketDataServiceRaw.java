package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioContract;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyChain;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioFuturesTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioServerTime;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.instrument.Instrument;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  public GateioMarketDataServiceRaw(GateioExchange exchange) {
    super(exchange);
  }

  public GateioServerTime getGateioServerTime() throws IOException {
    return gateio.getServerTime();
  }

  public List<GateioTicker> getGateioTickers(Instrument instrument) throws IOException {
    return gateio.getTickers(GateioAdapters.toString(instrument));
  }

  public List<GateioCurrencyInfo> getGateioCurrencyInfos() throws IOException {
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

  public GateioCurrencyPairDetails getCurrencyPairDetails(Instrument instrument)
      throws IOException {
    return gateio.getCurrencyPairDetails(GateioAdapters.toString(instrument));
  }

  public List<GateioContract> getContracts(String settle, int limit, int offset) throws IOException{
    return gateio.getContracts(settle, limit, offset);
  }

  public GateioContract getContract(String contract, String settle) throws IOException{
    return gateio.getContract(contract, settle);
  }

  public List<GateioFuturesTicker> getFuturesTickers(String contract, String settle) throws IOException{
    return gateio.getFuturesTickers(contract, settle);
  }
}
