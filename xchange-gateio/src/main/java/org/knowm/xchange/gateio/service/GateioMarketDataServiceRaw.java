package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> getBTERMarketInfo() throws IOException {

    GateioMarketInfoWrapper bterMarketInfo = bter.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
  }

  public Map<CurrencyPair, Ticker> getBTERTickers() throws IOException {

    Map<String, GateioTicker> gateioTickers = bter.getTickers();
    Map<CurrencyPair, Ticker> adaptedTickers = new HashMap<>(gateioTickers.size());
    gateioTickers.forEach((currencyPairString, gateioTicker) -> {
      String[] currencyPairStringSplit = currencyPairString.split("_");
      CurrencyPair currencyPair = new CurrencyPair(Currency.getInstance(currencyPairStringSplit[0].toUpperCase()), Currency.getInstance(currencyPairStringSplit[1].toUpperCase()));
      adaptedTickers.put(currencyPair, GateioAdapters.adaptTicker(currencyPair, gateioTicker));
    });

    return adaptedTickers;
  }

  public Map<CurrencyPair, GateioDepth> getGateioDepths() throws IOException {
    Map<String, GateioDepth> depths = bter.getDepths();
    Map<CurrencyPair, GateioDepth> adaptedDepths = new HashMap<>(depths.size());
    depths.forEach((currencyPairString, gateioDepth) -> {
      String[] currencyPairStringSplit = currencyPairString.split("_");
      CurrencyPair currencyPair = new CurrencyPair(Currency.getInstance(currencyPairStringSplit[0].toUpperCase()), Currency.getInstance(currencyPairStringSplit[1].toUpperCase()));
      adaptedDepths.put(currencyPair, gateioDepth);
    });
    return adaptedDepths;
  }

  public GateioTicker getBTERTicker(String tradableIdentifier, String currency) throws IOException {

    GateioTicker gateioTicker = bter.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioTicker);
  }

  public GateioDepth getBTEROrderBook(String tradeableIdentifier, String currency) throws IOException {

    GateioDepth gateioDepth = bter.getFullDepth(tradeableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioDepth);
  }

  public GateioTradeHistory getBTERTradeHistory(String tradeableIdentifier, String currency) throws IOException {

    GateioTradeHistory tradeHistory = bter.getTradeHistory(tradeableIdentifier, currency);

    return handleResponse(tradeHistory);
  }

  public GateioTradeHistory getBTERTradeHistorySince(String tradeableIdentifier, String currency, String tradeId) throws IOException {

    GateioTradeHistory tradeHistory = bter.getTradeHistorySince(tradeableIdentifier, currency, tradeId);

    return handleResponse(tradeHistory);
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<>(bter.getPairs().getPairs());
    return currencyPairs;
  }
}
