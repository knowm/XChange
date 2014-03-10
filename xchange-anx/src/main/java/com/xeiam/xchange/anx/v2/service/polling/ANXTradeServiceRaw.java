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
package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrderWrapper;
import com.xeiam.xchange.anx.v2.service.ANXBaseService;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXGenericResponse;
import com.xeiam.xchange.utils.Assert;

public class ANXTradeServiceRaw extends ANXBaseService {

  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected ANXTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public ANXOpenOrder[] getANXOpenOrders() throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper = anxV2.getOpenOrders(ANXUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, ANXUtils.getNonce());
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getOpenOrders(): " + e.getError(), e);
    }
  }

  public ANXGenericResponse placeANXMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse =
          anxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), marketOrder.getCurrencyPair().baseCurrency,  marketOrder.getCurrencyPair().counterCurrency, marketOrder
              .getType().equals(Order.OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount().multiply(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), null);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling placeMarketOrder(): " + e.getError(), e);
    }
  }

//  public ANXGenericResponse placeANXLimitOrder(String tradableIdentifier, String currency, String type, BigDecimal amount, String price) throws IOException {
  public ANXGenericResponse placeANXLimitOrder(CurrencyPair currencyPair, String type, BigDecimal amount, String price) throws IOException {

    try {
//      ANXGenericResponse anxGenericResponse = anxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), tradableIdentifier, currency, type, amount, price);
        ANXGenericResponse anxGenericResponse = anxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), currencyPair.baseCurrency, currencyPair.counterCurrency, type, amount, price);

        return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling placeLimitOrder(): " + e.getError(), e);
    }
  }

  public ANXGenericResponse cancelANXOrder(String orderId) throws IOException {

    try {

      ANXGenericResponse anxGenericResponse = anxV2.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), orderId);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling cancelOrder(): " + e.getError(), e);
    }
  }
}
