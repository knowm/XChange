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
package com.xeiam.xchange.mtgox.v1.service.trade;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Constants;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.HttpException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.OpenOrder;
import com.xeiam.xchange.service.trade.OpenOrders;
import com.xeiam.xchange.service.trade.TradeService;
import com.xeiam.xchange.service.trade.Wallet;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.CryptoUtils;
import com.xeiam.xchange.utils.HttpUtils;

public class MtGoxTradeService extends BaseExchangeService implements TradeService {

  private final Logger log = LoggerFactory.getLogger(MtGoxTradeService.class);

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final String apiBaseURI = String.format("%s/api/%s/", apiURI, apiVersion);

  /**
   * Initialise common properties from the exchange specification
   * 
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  public MtGoxTradeService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() {

    // verify
    Assert.notNull(apiKey, "apiKey cannot be null");
    Assert.notNull(apiSecret, "apiSecret cannot be null");
    Assert.notNull(apiURI, "apiURI cannot be null");
    Assert.notNull(apiVersion, "apiVersion cannot be null");

    try {

      // Build account info request
      String url = apiBaseURI + "/generic/private/info?raw";
      String postBody = "nonce=" + CryptoUtils.getNumericalNonce();
      Map<String, String> headerKeyValues = new HashMap<String, String>();
      headerKeyValues.put("Rest-Key", URLEncoder.encode(apiKey, HttpUtils.CHARSET_UTF_8));
      headerKeyValues.put("Rest-Sign", CryptoUtils.computeSignature("HmacSHA512", postBody, apiSecret));

      // Request data
      MtGoxAccountInfo mtGoxAccountInfo = HttpUtils.postForJsonObject(url, MtGoxAccountInfo.class, postBody, mapper, headerKeyValues);

      // Adapt to XChange DTOs
      AccountInfo accountInfo = new AccountInfo();
      accountInfo.setUsername(mtGoxAccountInfo.getLogin());

      List<Wallet> wallets = new ArrayList<Wallet>();
      Wallet usdWallet = new Wallet();
      usdWallet.setCurrency(Constants.USD);
      usdWallet.setAmount_int(mtGoxAccountInfo.getWallets().getUSD().getBalance().getValue_int());
      wallets.add(usdWallet);
      Wallet btcWallet = new Wallet();
      btcWallet.setCurrency(Constants.BTC);
      btcWallet.setAmount_int(mtGoxAccountInfo.getWallets().getBTC().getBalance().getValue_int());
      wallets.add(btcWallet);
      accountInfo.setWallets(wallets);

      return accountInfo;

    } catch (GeneralSecurityException e) {
      throw new ExchangeException("Problem generating secure HTTP request (General Security)", e);
    } catch (UnsupportedEncodingException e) {
      throw new ExchangeException("Problem generating secure HTTP request  (Unsupported Encoding)", e);
    } catch (HttpException e) {
      throw new ExchangeException("Problem getting server response (Http error)", e);
    } catch (NumberFormatException e) {
      throw new ExchangeException("Problem generating Account Info (number formatting)", e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() {

    // verify
    Assert.notNull(apiKey, "apiKey cannot be null");
    Assert.notNull(apiSecret, "apiSecret cannot be null");
    Assert.notNull(apiURI, "apiURI cannot be null");
    Assert.notNull(apiVersion, "apiVersion cannot be null");

    try {
      // Build open orders request
      String url = apiBaseURI + "/generic/private/orders?raw";
      String postBody = "nonce=" + CryptoUtils.getNumericalNonce();
      Map<String, String> headerKeyValues = new HashMap<String, String>();
      headerKeyValues.put("Rest-Key", URLEncoder.encode(apiKey, HttpUtils.CHARSET_UTF_8));
      headerKeyValues.put("Rest-Sign", CryptoUtils.computeSignature("HmacSHA512", postBody, apiSecret));

      // Request data
      MtGoxOpenOrder[] mtGoxOpenOrder = HttpUtils.postForJsonObject(url, MtGoxOpenOrder[].class, postBody, mapper, headerKeyValues);

      // Adapt to XChange DTOs
      List<OpenOrder> openOrdersList = new ArrayList<OpenOrder>();
      for (int i = 0; i < mtGoxOpenOrder.length; i++) {
        OpenOrder openOrder = new OpenOrder();
        openOrder.setType(mtGoxOpenOrder[i].getType().equalsIgnoreCase("bid") ? OpenOrder.BID : OpenOrder.ASK);
        openOrder.setAmount_int(mtGoxOpenOrder[i].getAmount().getValue_int());
        openOrder.setAmountCurrency(mtGoxOpenOrder[i].getAmount().getCurrency());

        openOrder.setPrice_int(mtGoxOpenOrder[i].getPrice().getValue_int());
        openOrder.setPriceCurrency(mtGoxOpenOrder[i].getPrice().getCurrency());

        openOrdersList.add(openOrder);
      }
      OpenOrders openOrders = new OpenOrders();
      openOrders.setOpenOrders(openOrdersList);

      return openOrders;

    } catch (GeneralSecurityException e) {
      throw new ExchangeException("Problem generating secure HTTP request (General Security)", e);
    } catch (UnsupportedEncodingException e) {
      throw new ExchangeException("Problem generating secure HTTP request  (Unsupported Encoding)", e);
    } catch (HttpException e) {
      throw new ExchangeException("Problem getting server response (Http error)", e);
    } catch (NumberFormatException e) {
      throw new ExchangeException("Problem generating Account Info (number formatting)", e);
    }
  }
}
