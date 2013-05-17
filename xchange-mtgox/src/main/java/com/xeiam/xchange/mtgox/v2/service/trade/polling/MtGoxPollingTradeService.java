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
package com.xeiam.xchange.mtgox.v2.service.trade.polling;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrderWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class MtGoxPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private final Logger logger = LoggerFactory.getLogger(MtGoxPollingTradeService.class);

  private final MtGoxV2 mtGoxV2;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public MtGoxPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    signatureCreator = MtGoxV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public OpenOrders getOpenOrders() {

    MtGoxOpenOrderWrapper mtGoxOpenOrderWrapper = mtGoxV2.getOpenOrders(MtGoxUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, MtGoxUtils.getNonce());

    if (mtGoxOpenOrderWrapper.getResult().equals("success")) {
      return new OpenOrders(MtGoxAdapters.adaptOrders(mtGoxOpenOrderWrapper.getMtGoxOpenOrders()));
    } else if (mtGoxOpenOrderWrapper.getResult().equals("error")) {
      logger.warn("Error calling getOpenOrders(): {}", mtGoxOpenOrderWrapper.getError());
    }
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    verify(marketOrder);

    MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), marketOrder.getTradableIdentifier(), marketOrder
        .getTransactionCurrency(), marketOrder.getType().equals(OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount().multiply(
        new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), null);

    if (mtGoxGenericResponse.getResult().equals("success")) {
      return mtGoxGenericResponse.getDataString();
    } else if (mtGoxGenericResponse.getResult().equals("error")) {
      logger.warn("Error calling placeMarketOrder(): {}", mtGoxGenericResponse.getError());
    }
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    verify(limitOrder);
    Assert.notNull(limitOrder.getLimitPrice().getAmount(), "getLimitPrice().getAmount() cannot be null");
    Assert.notNull(limitOrder.getLimitPrice().getCurrencyUnit(), "getLimitPrice().getCurrencyUnit() cannot be null");

    String tradableIdentifier = limitOrder.getTradableIdentifier();
    String currency = limitOrder.getLimitPrice().getCurrencyUnit().toString();
    String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    // need to convert to MtGox "amount"
    BigDecimal amount = limitOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    // need to convert to MtGox "Price"
    String price = MtGoxUtils.getPriceString(limitOrder.getLimitPrice());

    MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), tradableIdentifier, currency, type, amount, price);

    if (mtGoxGenericResponse.getResult().equals("success")) {
      return mtGoxGenericResponse.getDataString();
    } else if (mtGoxGenericResponse.getResult().equals("error")) {
      logger.warn("Error calling placeLimitOrder(): {}", mtGoxGenericResponse.getError());
    }
    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) {

    Assert.notNull(orderId, "orderId cannot be null");

    MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), orderId);

    logger.info(mtGoxGenericResponse.toString());
    if (mtGoxGenericResponse.getResult().equals("success")) {
      return true;
    } else if (mtGoxGenericResponse.getResult().equals("error")) {
      logger.warn("Error calling cancelOrder(): {}", mtGoxGenericResponse.getError());
    }
    return false;
  }

  private void verify(Order order) {

    Assert.notNull(order.getTradableIdentifier(), "getTradableIdentifier() cannot be null");
    Assert.notNull(order.getType(), "getType() cannot be null");
    Assert.notNull(order.getTradableAmount(), "getAmount_int() cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(order.getTradableIdentifier(), order.getTransactionCurrency())), "currencyPair is not valid");

  }

}
