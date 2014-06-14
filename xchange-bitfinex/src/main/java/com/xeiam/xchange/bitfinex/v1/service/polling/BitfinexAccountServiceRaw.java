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
package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;

public class BitfinexAccountServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexBalancesResponse[] getBitfinexAccountInfo() throws IOException {

    BitfinexBalancesResponse[] balances = bitfinex.balances(apiKey, payloadCreator, signatureCreator, new BitfinexBalancesRequest(String.valueOf(nextNonce())));

    return balances;
  }

}
