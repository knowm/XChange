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
package com.xeiam.xchange.btctrade.service.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;

public class BTCTradeTradeServiceRaw extends BTCTradeBaseTradePollingService {

  /**
   * @param exchangeSpecification
   */
  protected BTCTradeTradeServiceRaw(
      ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  public BTCTradeOrder[] getBTCTradeOrders(long since, String type) {
    return btcTrade.getOrders(since, type, nextNonce(), publicKey,
        getSignatureCreator());
  }

  public BTCTradeOrder getBTCTradeOrder(String id) {
    return btcTrade.getOrder(id, nextNonce(), publicKey, getSignatureCreator());
  }

  public BTCTradeResult cancelBTCTradeOrder(String id) {
    return btcTrade.cancelOrder(id, nextNonce(), publicKey,
        getSignatureCreator());
  }

  public BTCTradePlaceOrderResult buy(BigDecimal amount, BigDecimal price) {
    return btcTrade.buy(amount.toPlainString(), price.toPlainString(),
        nextNonce(), publicKey, getSignatureCreator());
  }

  public BTCTradePlaceOrderResult sell(BigDecimal amount, BigDecimal price) {
    return btcTrade.sell(amount.toPlainString(), price.toPlainString(),
        nextNonce(), publicKey, getSignatureCreator());
  }

}
