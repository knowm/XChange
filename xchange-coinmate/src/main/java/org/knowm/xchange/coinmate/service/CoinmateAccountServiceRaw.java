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
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmate.CoinmateAuthenticated;
import org.knowm.xchange.coinmate.dto.account.CoinmateBalance;
import org.knowm.xchange.coinmate.dto.account.CoinmateDepositAddresses;
import org.knowm.xchange.coinmate.dto.account.CoinmateTradingFeesResponse;
import org.knowm.xchange.coinmate.dto.account.CoinmateTradingFeesResponseData;
import org.knowm.xchange.coinmate.dto.account.FeePriority;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransferDetail;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransferHistory;

/** @author Martin Stachon */
public class CoinmateAccountServiceRaw extends CoinmateBaseService {

  private final CoinmateDigest signatureCreator;
  private final CoinmateAuthenticated coinmateAuthenticated;

  public CoinmateAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoinmateAuthenticated.class, exchange.getExchangeSpecification())
            .build();
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

  public CoinmateTradingFeesResponseData getCoinmateTraderFees(String currencyPair) throws IOException {
    CoinmateTradingFeesResponse response =
        coinmateAuthenticated.traderFees(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            currencyPair);

    throwExceptionIfError(response);

    return response.getData();
  }

  public CoinmateTradeResponse coinmateBitcoinWithdrawal(BigDecimal amount, String address)
      throws IOException {
    return coinmateBitcoinWithdrawal(amount, address, FeePriority.HIGH);
  }

  public CoinmateTradeResponse coinmateBitcoinWithdrawal(BigDecimal amount, String address, FeePriority feePriority)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.bitcoinWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address,
            feePriority);

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

  public CoinmateTradeResponse coinmateLitecoinWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.litecoinWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateLitecoinDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.litecoinDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTradeResponse coinmateEthereumWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.ethereumWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateEthereumDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.ethereumDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTradeResponse coinmateRippleWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.rippleWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateRippleDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.rippleDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTradeResponse coinmateDashWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.dashWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateDashDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.dashDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTradeResponse coinmateCardanoWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.adaWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateCardanoDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.adaDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTradeResponse coinmateSolanaWithdrawal(BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response =
        coinmateAuthenticated.solWithdrawal(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);

    throwExceptionIfError(response);

    return response;
  }

  public CoinmateDepositAddresses coinmateSolanaDepositAddresses() throws IOException {
    CoinmateDepositAddresses addresses =
        coinmateAuthenticated.solDepositAddresses(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory());

    throwExceptionIfError(addresses);

    return addresses;
  }

  public CoinmateTransactionHistory getCoinmateTransactionHistory(
      int offset, Integer limit, String sort, Long timestampFrom, Long timestampTo, String orderId)
      throws IOException {
    CoinmateTransactionHistory tradeHistory =
        coinmateAuthenticated.getTransactionHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            offset,
            limit,
            sort,
            timestampFrom,
            timestampTo,
            orderId);

    throwExceptionIfError(tradeHistory);

    return tradeHistory;
  }

  public CoinmateTransferHistory getTransfersData(Integer limit, Long timestampFrom, Long timestampTo) throws IOException {
    return getCoinmateTransferHistory(limit, null, null, timestampFrom, timestampTo, null);
  }

  public CoinmateTransferHistory getCoinmateTransferHistory(
          Integer limit,
          Integer lastId,
          String sort,
          Long timestampFrom,
          Long timestampTo,
          String currency)
          throws IOException {
    CoinmateTransferHistory transferHistory =
            coinmateAuthenticated.getTransferHistory(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getExchangeSpecification().getUserName(),
                    signatureCreator,
                    exchange.getNonceFactory(),
                    limit,
                    lastId,
                    sort,
                    timestampFrom,
                    timestampTo,
                    currency);

    throwExceptionIfError(transferHistory);

    return transferHistory;
  }

  public CoinmateTransferDetail getCoinmateTransferDetail(Long transactionId)
      throws IOException {
    CoinmateTransferDetail transferDetail =
        coinmateAuthenticated.getTransferDetail(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            signatureCreator,
            exchange.getNonceFactory(),
            transactionId);

    throwExceptionIfError(transferDetail);

    return transferDetail;
  }
}
