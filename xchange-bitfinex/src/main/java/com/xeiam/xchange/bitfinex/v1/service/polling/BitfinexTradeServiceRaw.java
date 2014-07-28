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
package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexActiveCreditsRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.FixedRateLoanOrder;
import com.xeiam.xchange.dto.trade.FloatingRateLoanOrder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public class BitfinexTradeServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {

    BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(nextNonce())));

    return activeOrders;
  }
  
  public BitfinexOfferStatusResponse[] getBitfinexOpenOffers() throws IOException {
    
    BitfinexOfferStatusResponse[] activeOffers = bitfinex.activeOffers(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/offers", String.valueOf(nextNonce())));
    
    return activeOffers;
  }
  
  public BitfinexOrderStatusResponse placeBitfinexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BitfinexUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = BitfinexOrderType.MARKET.getValue();
    
    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, 
            new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount(), BigDecimal.ONE, "bitfinex", type, orderType, false));

    return newOrder;
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(LimitOrder limitOrder, BitfinexOrderType bitfinexOrderType) throws IOException {

    String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();
    
    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, 
            new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),
            "bitfinex", type, orderType, false));

    return newOrder;
  }
  
  public BitfinexOfferStatusResponse placeBitfinexFixedRateLoanOrder(FixedRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {
    
    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";
    
    BitfinexOfferStatusResponse newOrderResponse = bitfinex.newOffer(apiKey, payloadCreator, signatureCreator, 
        new BitfinexNewOfferRequest(String.valueOf(nextNonce()), loanOrder.getCurrency(), loanOrder.getTradableAmount(), loanOrder.getRate(), loanOrder.getDayPeriod(), direction));
    
    return newOrderResponse;
  }
  
public BitfinexOfferStatusResponse placeBitfinexFloatingRateLoanOrder(FloatingRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {
    
    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";
    
    BitfinexOfferStatusResponse newOrderResponse = bitfinex.newOffer(apiKey, payloadCreator, signatureCreator, 
        new BitfinexNewOfferRequest(String.valueOf(nextNonce()), loanOrder.getCurrency(), loanOrder.getTradableAmount(), new BigDecimal("0.0"), loanOrder.getDayPeriod(), direction));
    
    return newOrderResponse;
  }

  public BitfinexOrderStatusResponse cancelBitfinexOrder(String orderId) throws IOException {

    BitfinexOrderStatusResponse cancelResponse = bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOrderRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return cancelResponse;
  }
  
  public BitfinexOfferStatusResponse cancelBitfinexOffer(String offerId) throws IOException {
    
    BitfinexOfferStatusResponse cancelResponse = bitfinex.cancelOffer(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOfferRequest(String.valueOf(nextNonce()), Integer.valueOf(offerId)));
    
    return cancelResponse;
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    BitfinexOrderStatusResponse orderStatus = bitfinex.orderStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOrderStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));

    return orderStatus;

  }
  
  public BitfinexOfferStatusResponse getBitfinexOfferStatusResponse(String offerId) throws IOException {
    
    BitfinexOfferStatusResponse offerStatus = bitfinex.offerStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOfferStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(offerId)));
  
    return offerStatus;
  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator, new BitfinexPastTradesRequest(String.valueOf(nextNonce()), symbol, timestamp, limit));

    return trades;
  }
  
  public BitfinexCreditResponse[] getBitfinexActiveCredits() throws IOException {
  
    BitfinexCreditResponse[] credits = bitfinex.activeCredits(apiKey, payloadCreator, signatureCreator, new BitfinexActiveCreditsRequest(String.valueOf(nextNonce())));
    
    return credits;
  }
  
}
