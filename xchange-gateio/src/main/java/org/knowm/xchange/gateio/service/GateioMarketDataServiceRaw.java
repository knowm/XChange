package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> getGateioMarketInfo() throws IOException {

    GateioMarketInfoWrapper gateioMarketInfo = gateio.getMarketInfo();

    return gateioMarketInfo.getMarketInfoMap();
  }

  public Map<CurrencyPair, Ticker> getGateioTickers() throws IOException {

    Map<String, GateioTicker> gateioTickers = gateio.getTickers();
    Map<CurrencyPair, Ticker> adaptedTickers = new HashMap<>(gateioTickers.size());
    gateioTickers.forEach((currencyPairString, gateioTicker) -> {
      String[] currencyPairStringSplit = currencyPairString.split("_");
      CurrencyPair currencyPair = new CurrencyPair(new Currency(currencyPairStringSplit[0].toUpperCase()), new Currency(currencyPairStringSplit[1].toUpperCase()));
      adaptedTickers.put(currencyPair, GateioAdapters.adaptTicker(currencyPair, gateioTicker));
    });

    return adaptedTickers;
  }

  public Map<CurrencyPair, GateioDepth> getGateioDepths() throws IOException {
    Map<String, GateioDepth> depths = gateio.getDepths();
    Map<CurrencyPair, GateioDepth> adaptedDepths = new HashMap<>(depths.size());
    depths.forEach((currencyPairString, gateioDepth) -> {
      String[] currencyPairStringSplit = currencyPairString.split("_");
      CurrencyPair currencyPair = new CurrencyPair(new Currency(currencyPairStringSplit[0].toUpperCase()), new Currency(currencyPairStringSplit[1].toUpperCase()));
      adaptedDepths.put(currencyPair, gateioDepth);
    });
    return adaptedDepths;
  }

  public GateioTicker getGateioTicker(String tradableIdentifier, String currency) throws IOException {

    GateioTicker gateioTicker = gateio.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioTicker);
  }

  public GateioDepth getGateioOrderBook(String tradeableIdentifier, String currency) throws IOException {

    GateioDepth gateioDepth = gateio.getFullDepth(tradeableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioDepth);
  }

  public GateioTradeHistory getGateioTradeHistory(String tradeableIdentifier, String currency) throws IOException {

    GateioTradeHistory tradeHistory = gateio.getTradeHistory(tradeableIdentifier, currency);

    return handleResponse(tradeHistory);
  }

  public GateioTradeHistory getGateioTradeHistorySince(String tradeableIdentifier, String currency, String tradeId) throws IOException {

    GateioTradeHistory tradeHistory = gateio.getTradeHistorySince(tradeableIdentifier, currency, tradeId);

    return handleResponse(tradeHistory);
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<>(gateio.getPairs().getPairs());
    return currencyPairs;
  }
}
