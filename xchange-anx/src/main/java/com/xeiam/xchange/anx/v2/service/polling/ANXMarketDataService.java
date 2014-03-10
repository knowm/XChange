/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**

 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTradesWrapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Mt Gox V2
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class ANXMarketDataService extends ANXMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public ANXMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
//  public Ticker getTicker(String tradableIdentifier, String currency, Object... args) throws IOException {
//  Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    verify(currencyPair);
    return ANXAdapters.adaptTicker(getANXTicker(currencyPair));
  }

  /**
   * Get market depth from exchange
   * 
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *          absent or "full" -> get full OrderBook
   *          "partial" -> get partial OrderBook
   * @return The OrderBook
   * @throws java.io.IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

//    verify(tradableIdentifier, currency);
      verify(currencyPair);

    // Request data
    ANXDepthWrapper anxDepthWrapper = null;
    if (args.length > 0) {
      if (args[0] instanceof String) {
        if ("full" == args[0]) {
          anxDepthWrapper = getANXFullOrderBook(currencyPair);
        }
        else {
          anxDepthWrapper = getANXPartialOrderBook(currencyPair);
        }
      } else {
        throw new ExchangeException("Orderbook type argument must be a String!");
      }
    }
    else { // default to full orderbook
      anxDepthWrapper = getANXFullOrderBook(currencyPair);
    }

    // Adapt to XChange DTOs
      List<LimitOrder> asks = ANXAdapters.adaptOrders(anxDepthWrapper.getAnxDepth().getAsks(), currencyPair.baseCurrency, currencyPair.counterCurrency, "ask", "");
      List<LimitOrder> bids = ANXAdapters.adaptOrders(anxDepthWrapper.getAnxDepth().getBids(), currencyPair.baseCurrency, currencyPair.counterCurrency, "bid", "");
    Date date = new Date(anxDepthWrapper.getAnxDepth().getMicroTime() / 1000);
    return new OrderBook(date, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

      //    verify(tradableIdentifier, currency);
      verify(currencyPair);


      ANXTradesWrapper anxTradeWrapper = null;
    if (args.length > 0) {
      if (args[0] instanceof Long) {
        Long sinceTimeStamp = (Long) args[0];
        // Request data
        anxTradeWrapper = getANXTrades(currencyPair, sinceTimeStamp);
      }
      else {
        throw new ExchangeException("the \"since\" type argument must be a Long!");
      }
    }
    else {
      anxTradeWrapper = getANXTrades(currencyPair, null);
    }

    return ANXAdapters.adaptTrades(anxTradeWrapper.getANXTrades());
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
