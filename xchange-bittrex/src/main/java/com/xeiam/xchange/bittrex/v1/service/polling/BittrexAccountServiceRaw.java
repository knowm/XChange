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
package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAuthenticated;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexDepositAddressResponse;

public class BittrexAccountServiceRaw extends BittrexBasePollingService<BittrexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BittrexAuthenticated.class, exchangeSpecification);
  }

  public List<BittrexBalance> getBittrexAccountInfo() throws IOException {

    BittrexBalancesResponse response = bittrex.balances(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.getSuccess()) {
      return response.getResult();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String getBittrexDepositAddress(String currency) throws IOException {

    BittrexDepositAddressResponse response = bittrex.getdepositaddress(apiKey, signatureCreator, String.valueOf(nextNonce()), currency);
    if (response.getSuccess()) {
      return response.getResult().getAddress();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

}