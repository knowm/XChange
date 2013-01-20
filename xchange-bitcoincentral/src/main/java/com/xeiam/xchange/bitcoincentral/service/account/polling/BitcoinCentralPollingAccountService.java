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
package com.xeiam.xchange.bitcoincentral.service.account.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitcoincentral.BitcoinCentral;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralAdapters;
import com.xeiam.xchange.bitcoincentral.dto.account.BitcoinCentralAccountInfo;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.rest.BasicAuthCredentials;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  private BitcoinCentral bitcoincentral;
  private BasicAuthCredentials credentials;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitcoinCentralPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoincentral = RestProxyFactory.createProxy(BitcoinCentral.class, exchangeSpecification.getUri());
    this.credentials = new BasicAuthCredentials(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }

  @Override
  public AccountInfo getAccountInfo() {

    BitcoinCentralAccountInfo accountInfo = getBTCCAccountInfo();

    return BitcoinCentralAdapters.adaptAccountInfo(accountInfo, exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    throw new NotYetImplementedForExchangeException("Withdraw funds not implemented");
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestBitcoinDepositAddress(final String... arguments) {

    return getBTCCAccountInfo().getAddress();
  }

  private BitcoinCentralAccountInfo getBTCCAccountInfo() {

    return bitcoincentral.getAccountInfo(credentials);
  }

}
