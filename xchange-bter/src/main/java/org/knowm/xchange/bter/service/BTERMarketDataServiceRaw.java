package org.knowm.xchange.bter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.dto.marketdata.BTERDepth;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker;
import org.knowm.xchange.bter.dto.marketdata.BTERTickers;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;

public class BTERMarketDataServiceRaw extends BTERBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<CurrencyPair, BTERMarketInfo> getBTERMarketInfo() throws IOException {

    BTERMarketInfoWrapper bterMarketInfo = bter.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
  }

  public Map<CurrencyPair, BTERTicker> getBTERTickers() throws IOException {

    BTERTickers bterTickers = bter.getTickers();

    return handleResponse(bterTickers).getTickerMap();
  }

  public BTERTicker getBTERTicker(String tradableIdentifier, String currency) throws IOException {

    BTERTicker bterTicker = bter.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(bterTicker);
  }

  public BTERDepth getBTEROrderBook(String tradeableIdentifier, String currency) throws IOException {

    BTERDepth bterDepth = bter.getFullDepth(tradeableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(bterDepth);
  }

  public BTERTradeHistory getBTERTradeHistory(String tradeableIdentifier, String currency) throws IOException {

    BTERTradeHistory tradeHistory = bter.getTradeHistory(tradeableIdentifier, currency);

    return handleResponse(tradeHistory);
  }

  public BTERTradeHistory getBTERTradeHistorySince(String tradeableIdentifier, String currency, String tradeId) throws IOException {

    BTERTradeHistory tradeHistory = bter.getTradeHistorySince(tradeableIdentifier, currency, tradeId);

    return handleResponse(tradeHistory);
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<>(bter.getPairs().getPairs());
    return currencyPairs;
  }
}
