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
package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAuthenticated;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;

/**
 * @author jamespedwards42
 */
class CoinbaseTradeServiceRaw extends CoinbaseBaseService<CoinbaseAuthenticated> {

  protected CoinbaseTradeServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(CoinbaseAuthenticated.class, exchangeSpecification);
  }

  public CoinbaseTransfer buy(final BigDecimal quantity) throws IOException {

    return buy(quantity, false);
  }

  public CoinbaseTransfer buy(final BigDecimal quantity, final boolean agreeBTCAmountVaries) throws IOException {

    final CoinbaseTransfer buyTransfer = coinbase.buy(quantity.toPlainString(), agreeBTCAmountVaries, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(buyTransfer);
  }

  public CoinbaseTransfer sell(final BigDecimal quantity) throws IOException {

    final CoinbaseTransfer sellTransfer = coinbase.sell(quantity.toPlainString(), exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(sellTransfer);
  }
  
  public CoinbaseTransfers getCoinbaseTransfers() throws IOException {

    return getCoinbaseTransfers(null, null);
  }

  public CoinbaseTransfers getCoinbaseTransfers(final Integer page, final Integer limit) throws IOException {

    final CoinbaseTransfers transfers = coinbase.getTransfers(page, limit, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return transfers;
  }
}
