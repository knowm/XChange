/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
public class CampBXAccountService extends CampBXAccountServiceRaw implements PollingAccountService {

  private final Logger logger = LoggerFactory.getLogger(CampBXAccountService.class);

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    MyFunds myFunds = getCampBXAccountInfo();
    logger.debug("myFunds = {}", myFunds);

    if (!myFunds.isError()) {
      return new AccountInfo(exchangeSpecification.getUserName(), Arrays.asList(Wallet.createInstance("BTC", myFunds.getTotalBTC()), Wallet.createInstance("USD", myFunds.getTotalUSD())));
    }
    else {
      throw new ExchangeException("Error calling getAccountInfo(): " + myFunds.getError());
    }
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) throws IOException {

    CampBXResponse campBXResponse = withdrawCampBXFunds(amount, address);
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.getSuccess();
    }
    else {
      throw new ExchangeException("Error calling withdrawFunds(): " + campBXResponse.getError());
    }
  }

  @Override
  public String requestBitcoinDepositAddress(String... args) throws IOException {

    CampBXResponse campBXResponse = requestCampBXBitcoinDepositAddress();
    logger.debug("campBXResponse = {}", campBXResponse);

    if (!campBXResponse.isError()) {
      return campBXResponse.getSuccess();
    }
    else {
      throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + campBXResponse.getError());
    }
  }

}
