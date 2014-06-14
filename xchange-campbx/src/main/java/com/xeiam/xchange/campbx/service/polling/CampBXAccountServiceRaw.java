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

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;

/**
 * @author Matija Mazi
 */
public class CampBXAccountServiceRaw extends CampBXBasePollingService {

  private final CampBX campBX;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campBX = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  public MyFunds getCampBXAccountInfo() throws IOException {

    MyFunds myFunds = campBX.getMyFunds(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    return myFunds;
  }

  public CampBXResponse withdrawCampBXFunds(BigDecimal amount, String address) throws IOException {

    CampBXResponse campBXResponse = campBX.withdrawBtc(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), address, amount);
    return campBXResponse;
  }

  public CampBXResponse requestCampBXBitcoinDepositAddress() throws IOException {

    CampBXResponse campBXResponse = campBX.getDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    return campBXResponse;
  }

}
