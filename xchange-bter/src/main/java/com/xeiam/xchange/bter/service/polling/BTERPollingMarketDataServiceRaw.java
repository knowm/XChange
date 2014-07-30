package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTER;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERMarketInfoWrapper;
import com.xeiam.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTickers;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTERPollingMarketDataServiceRaw extends BTERBasePollingService<BTER> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTER.class, exchangeSpecification);
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
}
