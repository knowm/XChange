/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.MtGoxUtils;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.proxy.Params;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.CryptoUtils;

public class MtGoxPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private final Logger log = LoggerFactory.getLogger(MtGoxPollingTradeService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBaseURI;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public MtGoxPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getUri(), "Exchange specification URI cannot be null");
    Assert.notNull(exchangeSpecification.getVersion(), "Exchange specification version cannot be null");
    this.apiBaseURI = String.format("%s/api/%s/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());
  }

  @Override
  public OpenOrders getOpenOrders() {

    // Build request
    String url = apiBaseURI + "/generic/private/orders?raw";
    String postBody = Params.of("nonce", CryptoUtils.getNumericalNonce()).asFormEncodedPostBody();

    // Request data
    MtGoxOpenOrder[] mtGoxOpenOrders = httpTemplate.postForJsonObject(url, MtGoxOpenOrder[].class, postBody, mapper, MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody, exchangeSpecification
        .getApiKey(), exchangeSpecification.getSecretKey()));

    // Adapt to XChange DTOs
    return new OpenOrders(MtGoxAdapters.adaptOrders(mtGoxOpenOrders));

  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    verify(marketOrder);

    // Build request
    String symbol = marketOrder.getTradableIdentifier() + marketOrder.getTransactionCurrency();
    String type = marketOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    String amount = "" + (marketOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)));
    String url = apiBaseURI + symbol + "/private/order/add";

    String postBody = Params.of("nonce", CryptoUtils.getNumericalNonce(), "type", type, "amount_int", amount).asFormEncodedPostBody();

    // Request data
    MtGoxGenericResponse mtGoxSuccess = httpTemplate.postForJsonObject(url, MtGoxGenericResponse.class, postBody, mapper, MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody,
        exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey()));

    return mtGoxSuccess.getReturn();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    verify(limitOrder);
    Assert.notNull(limitOrder.getLimitPrice().getAmount(), "getLimitPrice().getAmount() cannot be null");
    Assert.notNull(limitOrder.getLimitPrice().getCurrencyUnit(), "getLimitPrice().getCurrencyUnit() cannot be null");

    // Build request
    String symbol = limitOrder.getTradableIdentifier() + limitOrder.getLimitPrice().getCurrencyUnit().toString();
    String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    String amount_int = "" + (limitOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)));
    String price_int = MtGoxUtils.getPriceString(limitOrder.getLimitPrice());
    String url = apiBaseURI + symbol + "/private/order/add";

    String postBody = Params.of("nonce", CryptoUtils.getNumericalNonce(), "type", type, "amount_int", amount_int, "price_int", price_int).asFormEncodedPostBody();

    // Request data
    MtGoxGenericResponse mtGoxSuccess = httpTemplate.postForJsonObject(url, MtGoxGenericResponse.class, postBody, mapper, MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody,
        exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey()));

    return mtGoxSuccess.getReturn();
  }

  @Override
  public boolean cancelOrder(String orderId) {

    // Assert.notNull(orderId, "orderId cannot be null");
    //
    // // Build request
    // String url = exchangeSpecification.getUri() + "/api/0/cancelOrder.php";
    // String postBody = "nonce=" + CryptoUtils.getNumericalNonce() + "&oid=" + orderId;
    //
    // // Request data
    // MtGoxGenericResponse mtGoxSuccess = httpTemplate.postForJsonObject(url, MtGoxGenericResponse.class, postBody, mapper,
    // MtGoxUtils.getMtGoxAuthenticationHeaderKeyValues(postBody, exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey()));
    //
    // return mtGoxSuccess.getResult().equals("success") ? true : false;

    throw new NotAvailableFromExchangeException();
  }

  private void verify(Order order) {

    Assert.notNull(order.getTradableIdentifier(), "getTradableIdentifier() cannot be null");
    Assert.notNull(order.getType(), "getType() cannot be null");
    Assert.notNull(order.getTradableAmount(), "getAmount_int() cannot be null");
    Assert.isTrue(MtGoxUtils.isValidCurrencyPair(new CurrencyPair(order.getTradableIdentifier(), order.getTransactionCurrency())), "currencyPair is not valid");

  }

}
