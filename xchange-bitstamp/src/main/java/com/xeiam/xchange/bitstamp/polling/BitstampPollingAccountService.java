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
package com.xeiam.xchange.bitstamp.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.api.BitStamp;
import com.xeiam.xchange.bitstamp.api.model.Balance;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.proxy.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.util.Arrays;

/**
 * @author Matija Mazi <br/>
 * @created 1/1/13 6:40 PM
 */
public class BitstampPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  private BitStamp bitstamp;

  public BitstampPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstamp = RestProxyFactory.createProxy(BitStamp.class, httpTemplate, exchangeSpecification, mapper);
  }

  @Override
  public AccountInfo getAccountInfo() {

    String userName = getUser();
    Balance balance = bitstamp.getBalance(userName, getPwd());
    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setUsername(userName);
    accountInfo.setWallets(Arrays.asList(
        new Wallet("USD", BigMoney.of(CurrencyUnit.USD, balance.getUsdBalance())),
        new Wallet("BTC", BigMoney.of(CurrencyUnit.of("BTC"), balance.getBtcBalance()))
    ));
    return accountInfo;
  }

  @Override
  public String withdrawFunds() {

    throw new UnsupportedOperationException("Funds withdrawal not yet implemented.");
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   * 
   * @param description must be null
   * @param notificationUrl must be null
   */
  @Override
  public String requestBitcoinDepositAddress(String description, String notificationUrl) {

    if (description != null || notificationUrl != null) {
      throw new IllegalArgumentException("Description and notification URL are not supported.");
    }
    return bitstamp.getBitcoinDepositAddress(getUser(), getPwd());
  }

  private String getPwd() {

    return exchangeSpecification.getPassword();
  }

  private String getUser() {

    return exchangeSpecification.getUserName();
  }
}
