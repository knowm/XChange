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
package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;

public class ItBitAccountServiceRaw extends ItBitBasePollingService {

  private final String userId;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.userId = (String) exchangeSpecification.getExchangeSpecificParametersItem("userId");
  }

  public ItBitAccountInfoReturn[] getItBitAccountInfo() throws IOException {

    ItBitAccountInfoReturn[] info = itBit.getInfo(signatureCreator, new Date().getTime(), nextNonce(), userId);
    return info;
  }

  public String withdrawItBitFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public String requestItBitDepositAddress(String currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
