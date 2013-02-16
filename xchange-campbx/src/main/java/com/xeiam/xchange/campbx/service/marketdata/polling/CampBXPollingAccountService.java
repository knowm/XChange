/*
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
package com.xeiam.xchange.campbx.service.marketdata.polling;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class CampBXPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  private static final Logger log = LoggerFactory.getLogger(CampBXPollingAccountService.class);

  private final CampBX campbx;

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public CampBXPollingAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getUri());
  }

  @Override
  public AccountInfo getAccountInfo() {

    Object myFunds = campbx.getMyFunds(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("myFunds = {}", myFunds);
    // todo!
    return new AccountInfo(exchangeSpecification.getUserName(), new ArrayList<Wallet>());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    Object result = campbx.withdrawBtc(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), address, amount);
    log.debug("result = {}", result);
    // todo!
    return null;
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) {

    Object result = campbx.getDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("result = {}", result);
    // todo!
    return result.toString();
  }
}
