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
package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAuthenticated;
import com.xeiam.xchange.bittrex.v1.BittrexUtils;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexCancelOrderResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexOpenOrder;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexOpenOrdersResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexTradeResponse;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public class BittrexTradeServiceRaw extends BittrexBasePollingService<BittrexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BittrexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BittrexAuthenticated.class, exchangeSpecification);
  }

  public String placeBittrexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BittrexUtils.toPairString(marketOrder.getCurrencyPair());

    if (marketOrder.getType() == OrderType.BID) {

      BittrexTradeResponse response = bittrex.buymarket(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException(response.getMessage());
      }

    }
    else {

      BittrexTradeResponse response = bittrex.sellmarket(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException(response.getMessage());
      }

    }
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = BittrexUtils.toPairString(limitOrder.getCurrencyPair());

    if (limitOrder.getType() == OrderType.BID) {
      BittrexTradeResponse response =
          bittrex.buylimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException(response.getMessage());
      }

    }
    else {
      BittrexTradeResponse response =
          bittrex.selllimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException(response.getMessage());
      }
    }
  }

  public boolean cancelBittrexLimitOrder(String uuid) throws IOException {

    BittrexCancelOrderResponse response = bittrex.cancel(apiKey, signatureCreator, String.valueOf(nextNonce()), uuid);

    if (response.getSuccess()) {
      return true;
    }
    else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public List<BittrexOpenOrder> getBittrexOpenOrders() throws IOException {

    BittrexOpenOrdersResponse response = bittrex.openorders(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.getSuccess()) {
      return response.getBittrexOpenOrders();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }

  }
}