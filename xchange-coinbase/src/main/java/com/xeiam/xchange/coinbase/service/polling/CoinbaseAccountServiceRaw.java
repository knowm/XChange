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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
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

  /**
   * Authenticated resource that shows the current user and their settings.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/users/index.html">coinbase.com/api/doc/1.0/users/index.html</a>
   * @return A {@code CoinbaseUsers} wrapper around the current {@code CoinbaseUser} containing account settings.
   * @throws IOException
   */
  public CoinbaseUsers getCoinbaseUsers() throws IOException {

    final CoinbaseUsers users = coinbase.getUsers(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return users;
  }

  /**
   * Authenticated resource that lets you update account settings for the current user.
   * Use {@link #getCoinbaseUsers()} to retrieve the current user first.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/users/update.html">coinbase.com/api/doc/1.0/users/update.html</a>
   * @param user {@code CoinbaseUser} with new information to be updated.
   * @return The current {@code CoinbaseUser} with the requested updated account settings.
   * @throws IOException
   */
  public CoinbaseUser updateCoinbaseUser(final CoinbaseUser user) throws IOException {

    final CoinbaseUser updatedUser = coinbase.updateUser(user.getId(), user, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(updatedUser);
  }

  /**
   * Authenticated resource which claims a redeemable token for its address and Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/tokens/redeem.html">coinbase.com/api/doc/1.0/tokens/redeem.html</a>
   * @param tokenId
   * @return True if the redemption was successful.
   * @throws IOException
   */
  public boolean redeemCoinbaseToken(final String tokenId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.redeemToken(tokenId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response).isSuccess();
  }

  /**
   * Authenticated resource that returns the user’s current account balance in BTC.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/balance.html">coinbase.com/api/doc/1.0/accounts/balance.html</a>
   * @return A {@code CoinbaseAmount} wrapper around a {@code BigMoney} object representing the current user's balance.
   * @throws IOException
   */
  public CoinbaseMoney getCoinbaseBalance() throws IOException {

    final CoinbaseMoney balance = coinbase.getBalance(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return balance;
  }

  /**
   * Authenticated resource that returns the user’s current Bitcoin receive address.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/receive_address.html">coinbase.com/api/doc/1.0/accounts/receive_address.html</a>
   * @return The user’s current {@code CoinbaseAddress}.
   * @throws IOException
   */
  public CoinbaseAddress getCoinbaseReceiveAddress() throws IOException {

    final CoinbaseAddress receiveResult = coinbase.getReceiveAddress(exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return receiveResult;
  }

  /**
   * Authenticated resource that returns Bitcoin addresses a user has associated with their account.
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/addresses/index.html">coinbase.com/api/doc/1.0/addresses/index.html</a>
   * @return A {@code CoinbaseAddresses} wrapper around a collection of {@code CoinbaseAddress's} associated with
   *         the current user's account.
   * @throws IOException
   */
  public CoinbaseAddresses getCoinbaseAddresses() throws IOException {

    return getCoinbaseAddresses(null, null, null);
  }

  /**
   * Authenticated resource that returns Bitcoin addresses a user has associated with their account.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/addresses/index.html">coinbase.com/api/doc/1.0/addresses/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @param filter Optional String match to filter addresses. Matches the address itself and also if the use has set a ‘label’ on the address.
   *          No filter is applied if {@code filter} is null or empty.
   * @return A {@code CoinbaseAddresses} wrapper around a collection of {@code CoinbaseAddress's} associated with
   *         the current user's account.
   * @throws IOException
   */
  public CoinbaseAddresses getCoinbaseAddresses(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseAddresses receiveResult = coinbase.getAddresses(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return receiveResult;
  }

  /**
   * Authenticated resource that generates a new Bitcoin receive address for the user.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/generate_receive_address.html">coinbase.com/api/doc/1.0/accounts/generate_receive_address.html</a>
   * @return The user’s newly generated and current {@code CoinbaseAddress}.
   * @throws IOException
   */
  public CoinbaseAddress generateCoinbaseReceiveAddress() throws IOException {

    return generateCoinbaseReceiveAddress(null, null);
  }

  /**
   * Authenticated resource that generates a new Bitcoin receive address for the user.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/generate_receive_address.html">coinbase.com/api/doc/1.0/accounts/generate_receive_address.html</a>
   * @param callbackUrl Optional Callback URL to receive instant payment notifications whenever funds arrive to this address.
   * @param label Optional text label for the address which can be used to filter against when calling {@link #getCoinbaseAddresses}.
   * @return The user’s newly generated and current {@code CoinbaseAddress}.
   * @throws IOException
   */
  public CoinbaseAddress generateCoinbaseReceiveAddress(final String callbackUrl, final String label) throws IOException {

    final CoinbaseAddressCallback callbackUrlParam = new CoinbaseAddressCallback(callbackUrl, label);
    final CoinbaseAddress generateReceiveAddress = coinbase.generateReceiveAddress(callbackUrlParam, exchangeSpecification.getApiKey(), signatureCreator, getNonce());

    return handleResponse(generateReceiveAddress);
  }

  /**
   * Authenticated resource which returns all related changes to an account. This is an alternative to the {@code getCoinbaseTransactions} API call.
   * It is designed to be faster and provide more detail so you can generate an overview/summary of individual account changes.
   * This is a paged resource and will return 30 results representing the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/account_changes/index.html">coinbase.com/api/doc/1.0/account_changes/index.html</a>
   * @return The current user, balance, and the most recent account changes.
   * @throws IOException
   */
  public CoinbaseAccountChanges getCoinbaseAccountChanges() throws IOException {

    return getCoinbaseAccountChanges(null);
  }

  /**
   * Authenticated resource which returns all related changes to an account. This is an alternative to the {@code getCoinbaseTransactions} API call.
   * It is designed to be faster and provide more detail so you can generate an overview/summary of individual account changes.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/account_changes/index.html">coinbase.com/api/doc/1.0/account_changes/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return The current user, balance, and the most recent account changes.
   * @throws IOException
   */
  public CoinbaseAccountChanges getCoinbaseAccountChanges(final Integer page) throws IOException {

    final CoinbaseAccountChanges accountChanges = coinbase.getAccountChanges(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return accountChanges;
  }

  /**
   * Authenticated resource that returns contacts the user has previously sent to or received from.
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/contacts/index.html">coinbase.com/api/doc/1.0/contacts/index.html</a>
   * @return {@code CoinbaseContacts} the user has previously sent to or received from.
   * @throws IOException
   */
  public CoinbaseContacts getCoinbaseContacts() throws IOException {

    return getCoinbaseContacts(null, null, null);
  }

  /**
   * Authenticated resource that returns contacts the user has previously sent to or received from.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/contacts/index.html">coinbase.com/api/doc/1.0/contacts/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @param filter Optional String match to filter addresses. Matches the address itself and also if the use has set a ‘label’ on the address.
   *          No filter is applied if {@code filter} is null or empty.
   * @return {@code CoinbaseContacts} the user has previously sent to or received from.
   * @throws IOException
   */
  public CoinbaseContacts getCoinbaseContacts(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseContacts contacts = coinbase.getContacts(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return contacts;
  }

  /**
   * Authenticated resource which returns the user’s most recent transactions. Sorted in descending order by creation date.
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/index.html">coinbase.com/api/doc/1.0/transactions/index.html</a>
   * @return The current user's most recent {@code CoinbaseTransactions}.
   * @throws IOException
   */
  public CoinbaseTransactions getCoinbaseTransactions() throws IOException {

    return getCoinbaseTransactions(null);
  }

  /**
   * Authenticated resource which returns the user’s most recent transactions. Sorted in descending order by creation date.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/index.html">coinbase.com/api/doc/1.0/transactions/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return The current user's most recent {@code CoinbaseTransactions}.
   * @throws IOException
   */
  public CoinbaseTransactions getCoinbaseTransactions(final Integer page) throws IOException {

    final CoinbaseTransactions transactions = coinbase.getTransactions(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return transactions;
  }

  /**
   * Authenticated resource which returns the details of an individual transaction.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/show.html">coinbase.com/api/doc/1.0/transactions/show.html</a>
   * @param transactionIdOrIdemField
   * @return
   * @throws IOException
   */
  public CoinbaseTransaction getCoinbaseTransaction(final String transactionIdOrIdemField) throws IOException {

    final CoinbaseTransaction transaction = coinbase.getTransactionDetails(transactionIdOrIdemField, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(transaction);
  }

  /**
   * Authenticated resource which lets the user request money from a Bitcoin address.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/request_money.html">coinbase.com/api/doc/1.0/transactions/request_money.html</a>
   * @param transactionRequest
   * @return A pending {@code CoinbaseTransaction} representing the desired {@code CoinbaseRequestMoneyRequest}.
   * @throws IOException
   */
  public CoinbaseTransaction requestMoneyCoinbaseRequest(final CoinbaseRequestMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.requestMoney(new CoinbaseTransaction(transactionRequest), exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(pendingTransaction);
  }

  /**
   * Authenticated resource which lets you send money to an email or Bitcoin address.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/send_money.html">coinbase.com/api/doc/1.0/transactions/send_money.html</a>
   * @param transactionRequest
   * @return A completed {@code CoinbaseTransaction} representing the desired {@code CoinbaseSendMoneyRequest}.
   * @throws IOException
   */
  public CoinbaseTransaction sendMoneyCoinbaseRequest(final CoinbaseSendMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.sendMoney(new CoinbaseTransaction(transactionRequest), exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(pendingTransaction);
  }

  /**
   * Authenticated resource which lets the user resend a money request.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/resend_request.html">coinbase.com/api/doc/1.0/transactions/resend_request.html</a>
   * @param transactionId
   * @return true if resending the request was successful.
   * @throws IOException
   */
  public CoinbaseBaseResponse resendCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.resendRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  /**
   * Authenticated resource which lets a user complete a money request. Money requests can only be completed by the sender (not the recipient).
   * Remember that the sender in this context is the user who is sending money (not sending the request itself).
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/complete_request.html">coinbase.com/api/doc/1.0/transactions/complete_request.html</a>
   * @param transactionId
   * @return The {@code CoinbaseTransaction} representing the completed {@code CoinbaseSendMoneyRequest}.
   * @throws IOException
   */
  public CoinbaseTransaction completeCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseTransaction response = coinbase.completeRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  /**
   * Authenticated resource which lets a user cancel a money request. Money requests can be canceled by the sender or the recipient.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/cancel_request.html">coinbase.com/api/doc/1.0/transactions/cancel_request.html</a>
   * @param transactionId
   * @return true if canceling the request was successful.
   * @throws IOException
   */
  public CoinbaseBaseResponse cancelCoinbaseRequest(final String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.cancelRequest(transactionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(response);
  }

  /**
   * Authenticated resource that creates a payment button, page, or iFrame to accept Bitcoin on your website.
   * This can be used to accept Bitcoin for an individual item or to integrate with your existing shopping cart solution.
   * For example, you could create a new payment button for each shopping cart on your website, setting the total
   * and order number in the button at checkout.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/buttons/create.html">coinbase.com/api/doc/1.0/buttons/create.html</a>
   * @param button A {@code CoinbaseButton} containing the desired button configuration for Coinbase to create.
   * @return newly created {@code CoinbaseButton}.
   * @throws IOException
   */
  public CoinbaseButton createCoinbaseButton(final CoinbaseButton button) throws IOException {

    final CoinbaseButton createdButton = coinbase.createButton(button, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdButton);
  }

  /**
   * Authenticated resource which returns a merchant’s orders that they have received. Sorted in descending order by creation date.
   * This is a paged resource and will return the first page by default, use {@link #getCoinbaseOrders(Integer page)} to
   * retrieve additional pages.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/index.html">coinbase.com/api/doc/1.0/orders/index.html</a>
   * @return
   * @throws IOException
   */
  public CoinbaseOrders getCoinbaseOrders() throws IOException {

    return getCoinbaseOrders(null);
  }

  /**
   * Authenticated resource which returns a merchant’s orders that they have received. Sorted in descending order by creation date.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/index.html">coinbase.com/api/doc/1.0/orders/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return
   * @throws IOException
   */
  public CoinbaseOrders getCoinbaseOrders(final Integer page) throws IOException {

    final CoinbaseOrders orders = coinbase.getOrders(page, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return orders;
  }

  /**
   * Authenticated resource which returns order details for a specific order id or merchant custom.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/show.html">coinbase.com/api/doc/1.0/orders/show.html</a>
   * @param orderIdOrCustom
   * @return
   * @throws IOException
   */
  public CoinbaseOrder getCoinbaseOrder(final String orderIdOrCustom) throws IOException {

    final CoinbaseOrder order = coinbase.getOrder(orderIdOrCustom, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(order);
  }

  /**
   * Authenticated resource which lets you generate an order associated with a button. After generating an order,
   * you can send Bitcoin to the address associated with the order to complete the order.
   * The status of this newly created order will be ‘new’.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/buttons/create_order.html">coinbase.com/api/doc/1.0/buttons/create_order.html</a>
   * @param code The code of the button for which you wish to create an order.
   * @return The newly created {@code CoinbaseOrder}.
   * @throws IOException
   */
  public CoinbaseOrder createCoinbaseOrder(final String code) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(code, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdOrder);
  }
  
  /**
   * Authenticated resource which returns an order for a new button.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/create.html">coinbase.com/api/doc/1.0/orders/create.html</a>
   * @param button A {@code CoinbaseButton} containing information to create a one time order.
   * @return The newly created {@code CoinbaseOrder}.
   * @throws IOException
   */
  public CoinbaseOrder createCoinbaseOrder(final CoinbaseButton button) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(button, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return handleResponse(createdOrder);
  }

  /**
   * Authenticated resource that lets you list all your recurring payments (scheduled buys, sells, and subscriptions you’ve created with merchants).
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/index.html">coinbase.com/api/doc/1.0/recurring_payments/index.html</a>
   * @return
   * @throws IOException
   */
  public CoinbaseRecurringPayments getCoinbaseRecurringPayments() throws IOException {

    return getCoinbaseRecurringPayments(null, null);
  }

  /**
   * Authenticated resource that lets you list all your recurring payments (scheduled buys, sells, and subscriptions you’ve created with merchants).
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/index.html">coinbase.com/api/doc/1.0/recurring_payments/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @return
   * @throws IOException
   */
  public CoinbaseRecurringPayments getCoinbaseRecurringPayments(final Integer page, final Integer limit) throws IOException {

    final CoinbaseRecurringPayments recurringPayments = coinbase.getRecurringPayments(page, limit, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return recurringPayments;
  }

  /**
   * Authenticated resource that lets you show an individual recurring payment.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/show.html">coinbase.com/api/doc/1.0/recurring_payments/show.html</a>
   * @param recurringPaymentId
   * @return
   * @throws IOException
   */
  public CoinbaseRecurringPayment getCoinbaseRecurringPayment(final String recurringPaymentId) throws IOException {

    final CoinbaseRecurringPayment recurringPayment = coinbase.getRecurringPayment(recurringPaymentId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return recurringPayment;
  }

  /**
   * Authenticated resource that lets you (as a merchant) list all the subscriptions customers have made with you. 
   * This call returns {@code CoinbaseSubscription} objects where you are the merchant.
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/index.html">coinbase.com/api/doc/1.0/subscribers/index.html</a>
   * @return
   * @throws IOException
   */
  public CoinbaseSubscriptions getCoinbaseSubscriptions() throws IOException {

    return getCoinbaseSubscriptions(null, null);
  }

  /**
   * Authenticated resource that lets you (as a merchant) list all the subscriptions customers have made with you. 
   * This call returns {@code CoinbaseSubscription} objects where you are the merchant.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/index.html">coinbase.com/api/doc/1.0/subscribers/index.html</a>
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @return
   * @throws IOException
   */
  public CoinbaseSubscriptions getCoinbaseSubscriptions(final Integer page, final Integer limit) throws IOException {

    final CoinbaseSubscriptions subscriptions = coinbase.getsSubscriptions(page, limit, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return subscriptions;
  }

  /**
   * Authenticated resource that lets you (as a merchant) show an individual subscription than a customer has created with you. 
   * This call returns a {@code CoinbaseSubscription} object where you are the merchant.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/show.html">coinbase.com/api/doc/1.0/subscribers/show.html</a>
   * @param subscriptionId
   * @return
   * @throws IOException
   */
  public CoinbaseSubscription getCoinbaseSubscription(final String subscriptionId) throws IOException {

    final CoinbaseSubscription subscription = coinbase.getsSubscription(subscriptionId, exchangeSpecification.getApiKey(), signatureCreator, getNonce());
    return subscription;
  }
}
