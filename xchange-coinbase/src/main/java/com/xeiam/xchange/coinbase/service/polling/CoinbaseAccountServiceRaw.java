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

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAuthenticated;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddressCallback;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddresses;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseContacts;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseRecurringPayment;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseRecurringPayments;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseRequestMoneyRequest;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseSendMoneyRequest;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransactions;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrder;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrders;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;

/**
 * @author jamespedwards42
 */
class CoinbaseAccountServiceRaw extends CoinbaseBaseService<CoinbaseAuthenticated> {

  protected CoinbaseAccountServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(CoinbaseAuthenticated.class, exchangeSpecification);
  }

  public CoinbaseUsers getCoinbaseUsers() throws IOException {

    final CoinbaseUsers users = coinbase.getUsers(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return users;
  }

  public CoinbaseUser updateCoinbaseUser(final CoinbaseUser user) throws IOException {

    final CoinbaseUser updatedUser = coinbase.updateUser(user.getId(), user, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(updatedUser);
  }

  public boolean redeemCoinbaseToken(final String tokenId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.redeemToken(tokenId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response).isSuccess();
  }

  public CoinbaseAmount getCoinbaseBalance() throws IOException {

    final CoinbaseAmount balance = coinbase.getBalance(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return balance;
  }

  public CoinbaseAddress getCoinbaseReceiveAddress() throws IOException {

    final CoinbaseAddress receiveResult = coinbase.getReceiveAddress(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return receiveResult;
  }

  public CoinbaseAddresses getCoinbaseAddresses() throws IOException {

    return getCoinbaseAddresses(null, null, null);
  }

  public CoinbaseAddresses getCoinbaseAddresses(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseAddresses receiveResult = coinbase.getAddresses(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return receiveResult;
  }

  public CoinbaseAddress generateCoinbaseReceiveAddress() throws IOException {

    return generateCoinbaseReceiveAddress(null, null);
  }

  public CoinbaseAddress generateCoinbaseReceiveAddress(final String callbackUrl, final String label) throws IOException {

    final CoinbaseAddressCallback callbackUrlParam = new CoinbaseAddressCallback(callbackUrl, label);
    final CoinbaseAddress generateReceiveAddress = coinbase.generateReceiveAddress(callbackUrlParam, exchangeSpecification.getApiKey(), signatureCreator, getNonce());

    return handleResponse(generateReceiveAddress);
  }

  public CoinbaseAccountChanges getCoinbaseAccountChanges() throws IOException {

    return getCoinbaseAccountChanges(null);
  }

  public CoinbaseAccountChanges getCoinbaseAccountChanges(final Integer page) throws IOException {

    final CoinbaseAccountChanges accountChanges = coinbase.getAccountChanges(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return accountChanges;
  }

  public CoinbaseContacts getCoinbaseContacts() throws IOException {

    return getCoinbaseContacts(null, null, null);
  }

  public CoinbaseContacts getCoinbaseContacts(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseContacts contacts = coinbase.getContacts(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return contacts;
  }

  public CoinbaseTransactions getCoinbaseTransactions() throws IOException {

    return getCoinbaseTransactions(null);
  }

  public CoinbaseTransactions getCoinbaseTransactions(final Integer page) throws IOException {

    final CoinbaseTransactions transactions = coinbase.getTransactions(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return transactions;
  }

  public CoinbaseTransaction getCoinbaseTransaction(final String transactionId) throws IOException {

    final CoinbaseTransaction transaction = coinbase.getTransactionDetails(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(transaction);
  }

  public CoinbaseTransaction requestMoneyCoinbaseRequest(final CoinbaseRequestMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.requestMoney(new CoinbaseTransaction(transactionRequest), exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(pendingTransaction);
  }

  public CoinbaseTransaction sendMoneyCoinbaseRequest(final CoinbaseSendMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.sendMoney(new CoinbaseTransaction(transactionRequest), exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(pendingTransaction);
  }

  public CoinbaseBaseResponse resendCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.resendRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  public CoinbaseTransaction completeCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseTransaction response = coinbase.completeRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  public CoinbaseBaseResponse cancelCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.cancelRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  public CoinbaseButton createCoinbaseButton(final CoinbaseButton button) throws IOException {

    final CoinbaseButton createdButton = coinbase.createButton(button, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdButton);
  }

  public CoinbaseOrders getCoinbaseOrders() throws IOException {

    return getCoinbaseOrders(null);
  }

  public CoinbaseOrders getCoinbaseOrders(final Integer page) throws IOException {

    final CoinbaseOrders orders = coinbase.getOrders(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return orders;
  }

  public CoinbaseOrder getCoinbaseOrder(final String orderId) throws IOException {

    final CoinbaseOrder order = coinbase.getOrder(orderId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(order);
  }

  public CoinbaseOrder createCoinbaseOrder(final String code) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(code, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdOrder);
  }

  public CoinbaseOrder createCoinbaseOrder(final CoinbaseButton button) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(button, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdOrder);
  }

  public CoinbaseRecurringPayments getCoinbaseRecurringPayments() throws IOException {

    return getCoinbaseRecurringPayments(null, null);
  }

  public CoinbaseRecurringPayments getCoinbaseRecurringPayments(final Integer page, final Integer limit) throws IOException {

    final CoinbaseRecurringPayments recurringPayments = coinbase.getRecurringPayments(page, limit, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return recurringPayments;
  }

  public CoinbaseRecurringPayment getCoinbaseRecurringPayment(final String recurringPaymentId) throws IOException {

    final CoinbaseRecurringPayment recurringPayment = coinbase.getRecurringPayment(recurringPaymentId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return recurringPayment;
  }

  public CoinbaseSubscriptions getCoinbaseSubscriptions() throws IOException {

    return getCoinbaseSubscriptions(null, null);
  }

  public CoinbaseSubscriptions getCoinbaseSubscriptions(final Integer page, final Integer limit) throws IOException {

    final CoinbaseSubscriptions subscriptions = coinbase.getsSubscriptions(page, limit, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return subscriptions;
  }

  public CoinbaseSubscription getCoinbaseSubscription(final String subscriptionId) throws IOException {

    final CoinbaseSubscription subscription = coinbase.getsSubscription(subscriptionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return subscription;
  }
}
