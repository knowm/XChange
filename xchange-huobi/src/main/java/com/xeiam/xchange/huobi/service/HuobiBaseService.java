/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.huobi.service;

import java.io.IOException;
import java.util.*;


import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.huobi.dto.HuobiResponce;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class HuobiBaseService extends BaseExchangeService implements BasePollingService {

    Map<Integer, String> HUOBI_ERRORS = new HashMap<Integer, String>();

    public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

        CurrencyPair.BTC_CNY,
        CurrencyPair.LTC_CNY
    );

  public HuobiBaseService(Exchange exchange) {

      super(exchange);

      HUOBI_ERRORS.put(1, "Server error");
      HUOBI_ERRORS.put(2, "Insufficient CNY");
      HUOBI_ERRORS.put(3, "Restarting failed");
      HUOBI_ERRORS.put(4, "Transaction is over");
      HUOBI_ERRORS.put(10, "Insufficient BTC");
      HUOBI_ERRORS.put(26, "Order is not existed");
      HUOBI_ERRORS.put(41, "Unable to modify due to filled order");
      HUOBI_ERRORS.put(42, "Order has been cancelled, unable to modify");
      HUOBI_ERRORS.put(44, "Price is too low");
      HUOBI_ERRORS.put(45, "Price is too high");
      HUOBI_ERRORS.put(46, "Minimum amount is 0.001");
      HUOBI_ERRORS.put(47, "Exceed the limit amount");
      HUOBI_ERRORS.put(55, "105% higher than current price, not allowed");
      HUOBI_ERRORS.put(56, "95% lower than current price, not allowed");
      HUOBI_ERRORS.put(64, "Invalid request");
      HUOBI_ERRORS.put(65, "Invalid method");
      HUOBI_ERRORS.put(66, "Invalid access key");
      HUOBI_ERRORS.put(67, "Invalid private key");
      HUOBI_ERRORS.put(68, "Invalid price");
      HUOBI_ERRORS.put(69, "Invalid amount");
      HUOBI_ERRORS.put(70, "Invalid submitting time");
      HUOBI_ERRORS.put(71, "Too many requests");
      HUOBI_ERRORS.put(87, "Buying price cannot exceed 101% of last price when transaction amount is less than 0.1 BTC.");
      HUOBI_ERRORS.put(88, "Selling price cannot below 99% of last price when transaction amount is less than 0.1 BTC.");
  }

    public String getExchangeSymbolsString() {

        StringBuilder extraSumbolsBuilder = new StringBuilder();

        for (CurrencyPair currencyPair : CURRENCY_PAIRS) {
            extraSumbolsBuilder.append(currencyPair.baseSymbol).append(currencyPair.counterSymbol).append(",");
        }
        return extraSumbolsBuilder.deleteCharAt(extraSumbolsBuilder.length() - 1).toString();
    }

    public void checkResponce(HuobiResponce responce) {
        if (responce.getCode() != null) {
            throw new ExchangeException("Got error code: " + HUOBI_ERRORS.get(responce.getCode()));
        }
    }

    @Override
    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        return CURRENCY_PAIRS;
    }
}
