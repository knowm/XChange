/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
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
package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateAuthenticated;
import org.knowm.xchange.coinmate.dto.account.CoinmateBalance;
import org.knowm.xchange.coinmate.dto.account.CoinmateDepositAddresses;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import si.mazi.rescu.RestProxyFactory;

/** @author Martin Stachon */
public class CoinmateAccountServiceRaw extends CoinmateBaseService {

  private final CoinmateDigest signatureCreator;
  private final CoinmateAuthenticated coinmateAuthenticated;

  public CoinmateAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateAuthenticated =
        RestProxyFactory.createProxy(
            CoinmateAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.signatureCreator =
        CoinmateDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public CoinmateBalance getCoinmateBalance() throws IOException {

    CoinmateBalance coinmateBalance =
        coinmateAuthenticated.getBalances(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(coinmateBalance);

    return coinmateBalance;
  }

  public CoinmateTradeResponse coinmateBitcoinWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.bitcoinWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateBitcoinDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.bitcoinDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTransactionHistory getCoinmateTransactionHistory(
      int offset, Integer limit, String sort) throws IOException {
    CoinmateTransactionHistory tradeHistory =
        coinmateAuthenticated.getTransactionHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            offset,
            limit,
            sort);

    throwExceptionIfError(tradeHistory);

    return tradeHistory;
  }
}
