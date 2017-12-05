package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTickers;
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

  public Map<CurrencyPair, GateioMarketInfoWrapper.BTERMarketInfo> getBTERMarketInfo() throws IOException {

    GateioMarketInfoWrapper bterMarketInfo = bter.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
  }

  public Map<CurrencyPair, GateioTicker> getBTERTickers() throws IOException {

    GateioTickers gateioTickers = bter.getTickers();

    return handleResponse(gateioTickers).getTickerMap();
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
