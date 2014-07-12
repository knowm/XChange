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
package com.xeiam.xchange.mintpal.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mintpal.MintPal;
import com.xeiam.xchange.mintpal.MintPalAdapters;
import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author jamespedwards42
 */
public class MintPalBasePollingService<T extends MintPal> extends BaseExchangeService implements BasePollingService {

  protected T mintPal;
  private final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  public MintPalBasePollingService(final Class<T> type, final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    mintPal = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty())
      currencyPairs.addAll(MintPalAdapters.adaptCurrencyPairs(handleRespone(mintPal.getAllTickers())));

    return currencyPairs;
  }

  public <R> R handleRespone(final MintPalBaseResponse<R> response) {

    if (!response.getStatus().equals("success"))
      throw new ExchangeException(response.getMessage());

    return response.getData();
  }
}
