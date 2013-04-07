/**
 * Copyright (C) 2013 Matija Mazi
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
package com.xeiam.xchange.bitstamp.service.account.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitStamp;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;

/**
 * @author Matija Mazi
 */
public class BitstampPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  private BitStamp bitstamp;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitstampPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstamp = RestProxyFactory.createProxy(BitStamp.class, exchangeSpecification.getSslUri());
  }

  @Override
  public AccountInfo getAccountInfo() {

    BitstampBalance bitstampBalance = bitstamp.getBalance(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    if (bitstampBalance.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
    }

    return BitstampAdapters.adaptAccountInfo(bitstampBalance, exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    return bitstamp.withdrawBitcoin(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), amount, address).toString();
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestBitcoinDepositAddress(final String... arguments) {

    return bitstamp.getBitcoinDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }

}
