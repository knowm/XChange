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
package com.xeiam.xchange.mtgox.v1.service.trade.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v0.MtGoxV0;
import com.xeiam.xchange.mtgox.v0.dto.trade.MtGoxCancelOrder;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxV1;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v1.service.MtGoxHmacPostBodyDigest;
import com.xeiam.xchange.rest.ParamsDigest;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class MtGoxPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private final ParamsDigest postBodySignatureCreator;
  private final MtGoxV1 mtGoxV1;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public MtGoxPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getUri(), "Exchange specification URI cannot be null");
    this.mtGoxV1 = RestProxyFactory.createProxy(MtGoxV1.class, exchangeSpecification.getUri());
    postBodySignatureCreator = MtGoxHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public OpenOrders getOpenOrders() {

    MtGoxOpenOrder[] mtGoxOpenOrders = mtGoxV1.getOpenOrders(MtGoxUtils.urlEncode(exchangeSpecification.getApiKey()), postBodySignatureCreator, getNonce());
    return new OpenOrders(MtGoxAdapters.adaptOrders(mtGoxOpenOrders));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    verify(marketOrder);

    MtGoxGenericResponse mtGoxSuccess = mtGoxV1.placeOrder(exchangeSpecification.getApiKey(), postBodySignatureCreator, getNonce(), marketOrder.getTradableIdentifier(), marketOrder
        .getTransactionCurrency(), marketOrder.getType().equals(OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount().multiply(
        new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), null);

    return mtGoxSuccess.getReturn();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    verify(limitOrder);
    Assert.notNull(limitOrder.getLimitPrice().getAmount(), "getLimitPrice().getAmount() cannot be null");
    Assert.notNull(limitOrder.getLimitPrice().getCurrencyUnit(), "getLimitPrice().getCurrencyUnit() cannot be null");

    MtGoxGenericResponse mtGoxSuccess = mtGoxV1.placeOrder(exchangeSpecification.getApiKey(), postBodySignatureCreator, getNonce(), limitOrder.getTradableIdentifier(), limitOrder.getLimitPrice()
        .getCurrencyUnit().toString(), limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask", limitOrder.getTradableAmount().multiply(
        new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), MtGoxUtils.getPriceString(limitOrder.getLimitPrice()));

    return mtGoxSuccess.getReturn();
  }

  @Override
  public boolean cancelOrder(String orderId) {

    // Get V0 API version as the V1 API has no cancel orders functionality
    MtGoxV0 mtGoxV0 = RestProxyFactory.createProxy(MtGoxV0.class, exchangeSpecification.getUri());

    Assert.notNull(orderId, "orderId cannot be null");

    MtGoxCancelOrder mtGoxCancelOrder = mtGoxV0.cancelOrder(exchangeSpecification.getApiKey(), postBodySignatureCreator, getNonce(), orderId);

    boolean orderExists = false;
    for (int i = 0; i < mtGoxCancelOrder.getOrders().size(); i++) {
      // System.out.println(mtGoxCancelOrder.getOrders().get(i).getOid());
      if (mtGoxCancelOrder.getOrders().get(i).getOid().equals(orderId)) {
        orderExists = true;
        break;
      }
    }

    return orderExists;
  }

  private void verify(Order order) {

    Assert.notNull(order.getTradableIdentifier(), "getTradableIdentifier() cannot be null");
    Assert.notNull(order.getType(), "getType() cannot be null");
    Assert.notNull(order.getTradableAmount(), "getAmount_int() cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(order.getTradableIdentifier(), order.getTransactionCurrency())), "currencyPair is not valid");

  }

  private long getNonce() {

    return System.currentTimeMillis();
  }

}
