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
package com.xeiam.xchange.intersango.v0_1.service.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.intersango.v0_1.service.trade.dto.IntersangoWallet;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.LimitOrder;
import com.xeiam.xchange.service.trade.MarketOrder;
import com.xeiam.xchange.service.trade.OpenOrders;
import com.xeiam.xchange.service.trade.TradeService;
import com.xeiam.xchange.service.trade.Wallet;

public class IntersangoTradeService extends BaseExchangeService implements TradeService {

  /**
   * Provides logging for this class
   */
  private final Logger log = LoggerFactory.getLogger(IntersangoTradeService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBase;

  /**
   * Initialise common properties from the exchange specification
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public IntersangoTradeService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.apiBase = String.format("%s/api/authenticated/%s/", exchangeSpecification.getUri(), exchangeSpecification.getVersion());
  }

  @Override
  public AccountInfo getAccountInfo() {

    // Build request
    String url = apiBase + "listAccounts.php";
    String postBody = "api_key=" + exchangeSpecification.getApiKey();

    // Request data
    IntersangoWallet[] intersangoWallets = httpTemplate.postForJsonObject(url, IntersangoWallet[].class, postBody, mapper, getIntersangoAuthenticationHeaderKeyValues(postBody));

    // Adapt to XChange DTOs
    AccountInfo accountInfo = new AccountInfo();
    // TODO Fill in more information in the AccountInfo
    accountInfo.setUsername("example");

    List<Wallet> wallets = new ArrayList<Wallet>();
    if (intersangoWallets != null) {
      for (IntersangoWallet intersangoWallet : intersangoWallets) {
        Wallet wallet = new Wallet();
        // Balance provided to 10dp
        // TODO Rethink the Wallet to use BigDecimal or Money
        wallet.setAmount_int((long) (Double.valueOf(intersangoWallet.getBalance()) * 10000000000L));
        wallet.setCurrency(intersangoWallet.getCurrency_abbreviation());
        wallets.add(wallet);
      }
    }
    accountInfo.setWallets(wallets);

    return accountInfo;

  }

  @Override
  public OpenOrders getOpenOrders() {

    return null;

    // // Build request
    // String url = apiBase + "/listOrders.php";
    // String postBody = "api_key=" + exchangeSpecification.getApiKey();
    //
    // // Request data
    // IntersangoOpenOrder[] intersangoOpenOrder = httpTemplate.postForJsonObject(url, IntersangoOpenOrder[].class, postBody, mapper, getIntersangoAuthenticationHeaderKeyValues(postBody));
    //
    // // Adapt to XChange DTOs
    // List<LimitOrder> openOrdersList = new ArrayList<LimitOrder>();
    // for (int i = 0; i < intersangoOpenOrder.length; i++) {
    // LimitOrder openOrder = new LimitOrder();
    // openOrder.setType(intersangoOpenOrder[i].getType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK);
    // openOrder.setTradableAmount(Long.valueOf(intersangoOpenOrder[i].getAmount()));
    // openOrder.setTradableIdentifier(intersangoOpenOrder[i].getAmount());
    //
    // openOrder.setPrice_int(Long.parseLong(intersangoOpenOrder[i].getPrice()));
    // openOrder.setPriceCurrency(intersangoOpenOrder[i].getPrice());
    //
    // openOrdersList.add(openOrder);
    // }
    // OpenOrders openOrders = new OpenOrders();
    // openOrders.setOpenOrders(openOrdersList);
    //
    // return openOrders;

  }

  @Override
  public boolean placeMarketOrder(MarketOrder marketOrder) {

    return false;
    // Assert.notNull(marketOrder.getTradableIdentifier(), "getAmountCurrency() cannot be null");
    // Assert.notNull(marketOrder.getPriceCurrency(), "getPriceCurrency() cannot be null");
    // Assert.notNull(marketOrder.getType(), "getType() cannot be null");
    // Assert.notNull(marketOrder.getTradableAmount(), "getAmount_int() cannot be null");
    //
    // // Build request
    // String symbol = marketOrder.getTradableIdentifier() + marketOrder.getPriceCurrency();
    // String type = marketOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    // String amount = "" + marketOrder.getTradableAmount();
    //
    // String url = apiBase + "/placeLimitOrder.php";
    // String postBody = "api_key=" + exchangeSpecification.getApiKey() + "&type=" + type + "&amount_int=" + amount;
    //
    // // Request data
    // IntersangoGenericResponse intersangoSuccess = httpTemplate.postForJsonObject(url, IntersangoGenericResponse.class, postBody, mapper, getIntersangoAuthenticationHeaderKeyValues(postBody));
    //
    // return intersangoSuccess.getError().equals("success") ? true : false;
  }

  @Override
  public boolean placeLimitOrder(LimitOrder limitOrder) {

    return false;

    //
    // Assert.notNull(limitOrder.getTradableIdentifier(), "getAmountCurrency() cannot be null");
    // Assert.notNull(limitOrder.getPriceCurrency(), "getPriceCurrency() cannot be null");
    // Assert.notNull(limitOrder.getType(), "getType() cannot be null");
    // Assert.notNull(limitOrder.getTradableAmount(), "getAmount_int() cannot be null");
    // Assert.notNull(limitOrder.getPrice_int(), "getPrice_int() cannot be null");
    //
    // // Build request
    // String symbol = limitOrder.getTradableIdentifier() + limitOrder.getPriceCurrency();
    // String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";
    // String amount = "" + limitOrder.getTradableAmount();
    // String price_int = "" + limitOrder.getPrice_int();
    //
    // String url = apiBase + "/placeLimitOrder.php";
    // String postBody = "api_key=" + exchangeSpecification.getApiKey() + "&type=" + type + "&amount_int=" + amount;
    //
    // // Request data
    // IntersangoGenericResponse intersangoSuccess = httpTemplate.postForJsonObject(url, IntersangoGenericResponse.class, postBody, mapper, getIntersangoAuthenticationHeaderKeyValues(postBody));
    //
    // return intersangoSuccess.getError().equals("success") ? true : false;
  }

  /**
   * Generates necessary authentication header values for Intersango
   * 
   * @param postBody
   * @return
   */
  private Map<String, String> getIntersangoAuthenticationHeaderKeyValues(String postBody) {

    Map<String, String> headerKeyValues = new HashMap<String, String>();
    return headerKeyValues;

    // try {
    //
    // Map<String, String> headerKeyValues = new HashMap<String, String>();
    //
    // headerKeyValues.put("api_key", URLEncoder.encode(apiKey, HttpTemplate.CHARSET_UTF_8));
    // return headerKeyValues;
    //
    // } catch (UnsupportedEncodingException e) {
    // throw new ExchangeException("Problem generating secure HTTP request  (Unsupported Encoding)", e);
    // }
  }

}
