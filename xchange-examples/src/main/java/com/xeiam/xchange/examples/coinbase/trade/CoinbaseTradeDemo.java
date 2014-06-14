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
package com.xeiam.xchange.examples.coinbase.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseTradeService;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author jamespedwards42
 */
public class CoinbaseTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    PollingTradeService tradeService = coinbase.getPollingTradeService();

    generic(tradeService);
    raw((CoinbaseTradeService) tradeService);
  }

  public static void generic(PollingTradeService tradeService) throws IOException {

    // MarketOrder marketOrder = new MarketOrder(OrderType.BID, new BigDecimal(".01"), Currencies.BTC, Currencies.USD);
    // String orderId = tradeService.placeMarketOrder(marketOrder);
    // System.out.println("Order Id: " + orderId);

    int page = 1; // optional
    int limit = 3; // optional
    Trades trades = tradeService.getTradeHistory(page, limit);
    System.out.println(trades);
  }

  public static void raw(CoinbaseTradeService tradeService) throws IOException {

    // CoinbaseTransfer buyTransfer = tradeService.buy(new BigDecimal(".01"));
    // System.out.println(buyTransfer);

    CoinbaseTransfers transfers = tradeService.getCoinbaseTransfers();
    System.out.println(transfers);
  }
}
