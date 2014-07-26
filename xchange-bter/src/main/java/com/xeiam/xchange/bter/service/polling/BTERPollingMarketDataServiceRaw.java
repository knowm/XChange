/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTER;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERMarketInfoWrapper;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTickers;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
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
