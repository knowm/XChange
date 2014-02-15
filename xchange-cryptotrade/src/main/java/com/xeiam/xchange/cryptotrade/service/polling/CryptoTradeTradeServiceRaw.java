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
package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeOrder;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePlaceOrderReturn;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class CryptoTradeTradeServiceRaw extends CryptoTradeBaseService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CryptoTradeTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public String placeCryptoTradeLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = String.format("%s_%s", limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency()).toLowerCase();
    CryptoTradeOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? CryptoTradeOrder.Type.Buy : CryptoTradeOrder.Type.Sell;
    CryptoTradePlaceOrderReturn cryptoTradePlaceOrderReturn =
        cryptoTradeProxy.trade(apiKey, signatureCreator, nextNonce(), pair, type, limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount());

    return cryptoTradePlaceOrderReturn.getSuccess();
  }

}
