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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CambBXUtils;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;

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
    this.campbx = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  @Override
  public AccountInfo getAccountInfo() {

    MyFunds myFunds = campbx.getMyFunds(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("myFunds = {}", myFunds);
    CambBXUtils.handleError(myFunds);
    return new AccountInfo(exchangeSpecification.getUserName(), Arrays.asList(Wallet.createInstance("BTC", myFunds.getTotalBTC()), Wallet.createInstance("USD", myFunds.getTotalUSD())));
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {

    CampBXResponse result = campbx.withdrawBtc(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), address, amount);
    log.debug("result = {}", result);
    CambBXUtils.handleError(result);
    return result.getSuccess();
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) {

    CampBXResponse result = campbx.getDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    log.debug("result = {}", result);
    CambBXUtils.handleError(result);
    return result.getSuccess();
  }

}
