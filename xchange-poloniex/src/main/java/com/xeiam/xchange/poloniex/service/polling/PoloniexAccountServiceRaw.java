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
package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;
import com.xeiam.xchange.poloniex.PoloniexUtils;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountServiceRaw extends PoloniexBasePollingService<PoloniexAuthenticated> {

  public PoloniexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(PoloniexAuthenticated.class, exchangeSpecification);
  }

  public List<Wallet> getWallets() {

    List<Wallet> wallets = PoloniexAdapters.adaptPoloniexBalances(poloniex.returnBalances(apiKey, signatureCreator, String.valueOf(nextNonce())));
    return wallets;
  }

  public String getDepositAddress(String currency) throws ExchangeException {

    HashMap<String, String> depositAddresses = poloniex.returnDepositAddresses(apiKey, signatureCreator, String.valueOf(nextNonce()));
    if (depositAddresses.containsKey(currency)) {
      return depositAddresses.get(currency);
    }
    else {
      throw new ExchangeException("Poloniex did not return a deposit address for " + currency);
    }
  }

}
