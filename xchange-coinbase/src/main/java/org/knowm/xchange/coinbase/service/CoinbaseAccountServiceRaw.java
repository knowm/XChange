package org.knowm.xchange.coinbase.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddress;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddressCallback;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddresses;
import org.knowm.xchange.coinbase.dto.account.CoinbaseContacts;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayment;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayments;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseRequestMoneyRequest;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseSendMoneyRequest;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransactions;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUsers;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrder;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrders;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;

/**
 * @author jamespedwards42
 */
class CoinbaseAccountServiceRaw extends CoinbaseBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Authenticated resource that shows the current user and their settings.
   *
   * @return A {@code CoinbaseUsers} wrapper around the current {@code CoinbaseUser} containing account settings.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/users/index.html">coinbase.com/api/doc/1.0/users/index.html</a>
   */
  public CoinbaseUsers getCoinbaseUsers() throws IOException {

    final CoinbaseUsers users = coinbase.getUsers(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return users;
  }

  /**
   * Authenticated resource that lets you update account settings for the current user. Use {@link #getCoinbaseUsers()} to retrieve the current user
   * first.
   *
   * @param user {@code CoinbaseUser} with new information to be updated.
   * @return The current {@code CoinbaseUser} with the requested updated account settings.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/users/update.html">coinbase.com/api/doc/1.0/users/update.html</a>
   */
  public CoinbaseUser updateCoinbaseUser(CoinbaseUser user) throws IOException {

    final CoinbaseUser updatedUser = coinbase.updateUser(user.getId(), user, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(updatedUser);
  }

  /**
   * Authenticated resource which claims a redeemable token for its address and Bitcoin.
   *
   * @param tokenId
   * @return True if the redemption was successful.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/tokens/redeem.html">coinbase.com/api/doc/1.0/tokens/redeem.html</a>
   */
  public boolean redeemCoinbaseToken(String tokenId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.redeemToken(tokenId, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(response).isSuccess();
  }

  /**
   * Authenticated resource that returns the user’s current account balance in BTC.
   *
   * @return A {@code CoinbaseAmount} wrapper around a {@code CoinbaseMoney} object representing the current user's balance.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/balance.html">coinbase.com/api/doc/1.0/accounts/balance.html</a>
   */
  public CoinbaseMoney getCoinbaseBalance() throws IOException {

    final CoinbaseMoney balance = coinbase.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return balance;
  }

  /**
   * Authenticated resource that returns the user’s current Bitcoin receive address.
   *
   * @return The user’s current {@code CoinbaseAddress}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/receive_address.html">coinbase.com/api/doc/1.0/accounts/receive_address.html</a>
   */
  public CoinbaseAddress getCoinbaseReceiveAddress() throws IOException {

    final CoinbaseAddress receiveResult = coinbase.getReceiveAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return receiveResult;
  }

  /**
   * Authenticated resource that returns Bitcoin addresses a user has associated with their account. This is a paged resource and will return the
   * first page by default.
   *
   * @return A {@code CoinbaseAddresses} wrapper around a collection of {@code CoinbaseAddress's} associated with the current user's account.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/addresses/index.html">coinbase.com/api/doc/1.0/addresses/index.html</a>
   */
  public CoinbaseAddresses getCoinbaseAddresses() throws IOException {

    return getCoinbaseAddresses(null, null, null);
  }

  /**
   * Authenticated resource that returns Bitcoin addresses a user has associated with their account.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @param filter Optional String match to filter addresses. Matches the address itself and also if the use has set a ‘label’ on the address. No
   * filter is applied if {@code filter} is null or empty.
   * @return A {@code CoinbaseAddresses} wrapper around a collection of {@code CoinbaseAddress's} associated with the current user's account.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/addresses/index.html">coinbase.com/api/doc/1.0/addresses/index.html</a>
   */
  public CoinbaseAddresses getCoinbaseAddresses(Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseAddresses receiveResult = coinbase.getAddresses(page, limit, filter, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return receiveResult;
  }

  /**
   * Authenticated resource that generates a new Bitcoin receive address for the user.
   *
   * @return The user’s newly generated and current {@code CoinbaseAddress}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/generate_receive_address.html">coinbase.com/api/doc/1.0/accounts/generate_receive_address
   * .html</a>
   */
  public CoinbaseAddress generateCoinbaseReceiveAddress() throws IOException {

    return generateCoinbaseReceiveAddress(null, null);
  }

  /**
   * Authenticated resource that generates a new Bitcoin receive address for the user.
   *
   * @param callbackUrl Optional Callback URL to receive instant payment notifications whenever funds arrive to this address.
   * @param label Optional text label for the address which can be used to filter against when calling {@link #getCoinbaseAddresses}.
   * @return The user’s newly generated and current {@code CoinbaseAddress}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/accounts/generate_receive_address.html">coinbase.com/api/doc/1.0/accounts/generate_receive_address
   * .html</a>
   */
  public CoinbaseAddress generateCoinbaseReceiveAddress(String callbackUrl, final String label) throws IOException {

    final CoinbaseAddressCallback callbackUrlParam = new CoinbaseAddressCallback(callbackUrl, label);
    final CoinbaseAddress generateReceiveAddress = coinbase.generateReceiveAddress(callbackUrlParam, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());

    return handleResponse(generateReceiveAddress);
  }

  /**
   * Authenticated resource which returns all related changes to an account. This is an alternative to the {@code getCoinbaseTransactions} API call.
   * It is designed to be faster and provide more detail so you can generate an overview/summary of individual account changes. This is a paged
   * resource and will return 30 results representing the first page by default.
   *
   * @return The current user, balance, and the most recent account changes.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/account_changes/index.html">coinbase.com/api/doc/1.0/account_changes/index.html</a>
   */
  public CoinbaseAccountChanges getCoinbaseAccountChanges() throws IOException {

    return getCoinbaseAccountChanges(null);
  }

  /**
   * Authenticated resource which returns all related changes to an account. This is an alternative to the {@code getCoinbaseTransactions} API call.
   * It is designed to be faster and provide more detail so you can generate an overview/summary of individual account changes.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return The current user, balance, and the most recent account changes.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/account_changes/index.html">coinbase.com/api/doc/1.0/account_changes/index.html</a>
   */
  public CoinbaseAccountChanges getCoinbaseAccountChanges(Integer page) throws IOException {

    final CoinbaseAccountChanges accountChanges = coinbase.getAccountChanges(page, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return accountChanges;
  }

  /**
   * Authenticated resource that returns contacts the user has previously sent to or received from. This is a paged resource and will return the first
   * page by default.
   *
   * @return {@code CoinbaseContacts} the user has previously sent to or received from.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/contacts/index.html">coinbase.com/api/doc/1.0/contacts/index.html</a>
   */
  public CoinbaseContacts getCoinbaseContacts() throws IOException {

    return getCoinbaseContacts(null, null, null);
  }

  /**
   * Authenticated resource that returns contacts the user has previously sent to or received from.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @param filter Optional String match to filter addresses. Matches the address itself and also if the use has set a ‘label’ on the address. No
   * filter is applied if {@code filter} is null or empty.
   * @return {@code CoinbaseContacts} the user has previously sent to or received from.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/contacts/index.html">coinbase.com/api/doc/1.0/contacts/index.html</a>
   */
  public CoinbaseContacts getCoinbaseContacts(Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseContacts contacts = coinbase.getContacts(page, limit, filter, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return contacts;
  }

  /**
   * Authenticated resource which returns the user’s most recent transactions. Sorted in descending order by creation date. This is a paged resource
   * and will return the first page by default.
   *
   * @return The current user's most recent {@code CoinbaseTransactions}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/index.html">coinbase.com/api/doc/1.0/transactions/index.html</a>
   */
  public CoinbaseTransactions getCoinbaseTransactions() throws IOException {

    return getCoinbaseTransactions(null);
  }

  /**
   * Authenticated resource which returns the user’s most recent transactions. Sorted in descending order by creation date.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return The current user's most recent {@code CoinbaseTransactions}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/index.html">coinbase.com/api/doc/1.0/transactions/index.html</a>
   */
  public CoinbaseTransactions getCoinbaseTransactions(Integer page) throws IOException {

    final CoinbaseTransactions transactions = coinbase.getTransactions(page, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return transactions;
  }

  /**
   * Authenticated resource which returns the details of an individual transaction.
   *
   * @param transactionIdOrIdemField
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/show.html">coinbase.com/api/doc/1.0/transactions/show.html</a>
   */
  public CoinbaseTransaction getCoinbaseTransaction(String transactionIdOrIdemField) throws IOException {

    final CoinbaseTransaction transaction = coinbase.getTransactionDetails(transactionIdOrIdemField, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return handleResponse(transaction);
  }

  /**
   * Authenticated resource which lets the user request money from a Bitcoin address.
   *
   * @param transactionRequest
   * @return A pending {@code CoinbaseTransaction} representing the desired {@code CoinbaseRequestMoneyRequest}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/request_money.html">coinbase.com/api/doc/1.0/transactions/request_money.html</a>
   */
  public CoinbaseTransaction requestMoneyCoinbaseRequest(CoinbaseRequestMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.requestMoney(new CoinbaseTransaction(transactionRequest),
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return handleResponse(pendingTransaction);
  }

  /**
   * Authenticated resource which lets you send money to an email or Bitcoin address.
   *
   * @param transactionRequest
   * @return A completed {@code CoinbaseTransaction} representing the desired {@code CoinbaseSendMoneyRequest}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/send_money.html">coinbase.com/api/doc/1.0/transactions/send_money.html</a>
   */
  public CoinbaseTransaction sendMoneyCoinbaseRequest(CoinbaseSendMoneyRequest transactionRequest) throws IOException {

    final CoinbaseTransaction pendingTransaction = coinbase.sendMoney(new CoinbaseTransaction(transactionRequest),
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return handleResponse(pendingTransaction);
  }

  /**
   * Authenticated resource which lets the user resend a money request.
   *
   * @param transactionId
   * @return true if resending the request was successful.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/resend_request.html">coinbase.com/api/doc/1.0/transactions/resend_request.html</a>
   */
  public CoinbaseBaseResponse resendCoinbaseRequest(String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.resendRequest(transactionId, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(response);
  }

  /**
   * Authenticated resource which lets a user complete a money request. Money requests can only be completed by the sender (not the recipient).
   * Remember that the sender in this context is the user who is sending money (not sending the request itself).
   *
   * @param transactionId
   * @return The {@code CoinbaseTransaction} representing the completed {@code CoinbaseSendMoneyRequest}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/complete_request.html">coinbase.com/api/doc/1.0/transactions/complete_request.html
   * </a>
   */
  public CoinbaseTransaction completeCoinbaseRequest(String transactionId) throws IOException {

    final CoinbaseTransaction response = coinbase.completeRequest(transactionId, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(response);
  }

  /**
   * Authenticated resource which lets a user cancel a money request. Money requests can be canceled by the sender or the recipient.
   *
   * @param transactionId
   * @return true if canceling the request was successful.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/transactions/cancel_request.html">coinbase.com/api/doc/1.0/transactions/cancel_request.html</a>
   */
  public CoinbaseBaseResponse cancelCoinbaseRequest(String transactionId) throws IOException {

    final CoinbaseBaseResponse response = coinbase.cancelRequest(transactionId, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(response);
  }

  /**
   * Authenticated resource that creates a payment button, page, or iFrame to accept Bitcoin on your website. This can be used to accept Bitcoin for
   * an individual item or to integrate with your existing shopping cart solution. For example, you could create a new payment button for each
   * shopping cart on your website, setting the total and order number in the button at checkout.
   *
   * @param button A {@code CoinbaseButton} containing the desired button configuration for Coinbase to create.
   * @return newly created {@code CoinbaseButton}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/buttons/create.html">coinbase.com/api/doc/1.0/buttons/create.html</a>
   */
  public CoinbaseButton createCoinbaseButton(CoinbaseButton button) throws IOException {

    final CoinbaseButton createdButton = coinbase.createButton(button, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(createdButton);
  }

  /**
   * Authenticated resource which returns a merchant’s orders that they have received. Sorted in descending order by creation date. This is a paged
   * resource and will return the first page by default, use {@link #getCoinbaseOrders(Integer page)} to retrieve additional pages.
   *
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/index.html">coinbase.com/api/doc/1.0/orders/index.html</a>
   */
  public CoinbaseOrders getCoinbaseOrders() throws IOException {

    return getCoinbaseOrders(null);
  }

  /**
   * Authenticated resource which returns a merchant’s orders that they have received. Sorted in descending order by creation date.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/index.html">coinbase.com/api/doc/1.0/orders/index.html</a>
   */
  public CoinbaseOrders getCoinbaseOrders(Integer page) throws IOException {

    final CoinbaseOrders orders = coinbase.getOrders(page, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return orders;
  }

  /**
   * Authenticated resource which returns order details for a specific order id or merchant custom.
   *
   * @param orderIdOrCustom
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/show.html">coinbase.com/api/doc/1.0/orders/show.html</a>
   */
  public CoinbaseOrder getCoinbaseOrder(String orderIdOrCustom) throws IOException {

    final CoinbaseOrder order = coinbase.getOrder(orderIdOrCustom, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(order);
  }

  /**
   * Authenticated resource which lets you generate an order associated with a button. After generating an order, you can send Bitcoin to the address
   * associated with the order to complete the order. The status of this newly created order will be ‘new’.
   *
   * @param code The code of the button for which you wish to create an order.
   * @return The newly created {@code CoinbaseOrder}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/buttons/create_order.html">coinbase.com/api/doc/1.0/buttons/create_order.html</a>
   */
  public CoinbaseOrder createCoinbaseOrder(String code) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(code, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(createdOrder);
  }

  /**
   * Authenticated resource which returns an order for a new button.
   *
   * @param button A {@code CoinbaseButton} containing information to create a one time order.
   * @return The newly created {@code CoinbaseOrder}.
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/orders/create.html">coinbase.com/api/doc/1.0/orders/create.html</a>
   */
  public CoinbaseOrder createCoinbaseOrder(CoinbaseButton button) throws IOException {

    final CoinbaseOrder createdOrder = coinbase.createOrder(button, exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    return handleResponse(createdOrder);
  }

  /**
   * Authenticated resource that lets you list all your recurring payments (scheduled buys, sells, and subscriptions you’ve created with merchants).
   * This is a paged resource and will return the first page by default.
   *
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/index.html">coinbase.com/api/doc/1.0/recurring_payments/index.html</a>
   */
  public CoinbaseRecurringPayments getCoinbaseRecurringPayments() throws IOException {

    return getCoinbaseRecurringPayments(null, null);
  }

  /**
   * Authenticated resource that lets you list all your recurring payments (scheduled buys, sells, and subscriptions you’ve created with merchants).
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/index.html">coinbase.com/api/doc/1.0/recurring_payments/index.html</a>
   */
  public CoinbaseRecurringPayments getCoinbaseRecurringPayments(Integer page, final Integer limit) throws IOException {

    final CoinbaseRecurringPayments recurringPayments = coinbase.getRecurringPayments(page, limit, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return recurringPayments;
  }

  /**
   * Authenticated resource that lets you show an individual recurring payment.
   *
   * @param recurringPaymentId
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/recurring_payments/show.html">coinbase.com/api/doc/1.0/recurring_payments/show.html</a>
   */
  public CoinbaseRecurringPayment getCoinbaseRecurringPayment(String recurringPaymentId) throws IOException {

    final CoinbaseRecurringPayment recurringPayment = coinbase.getRecurringPayment(recurringPaymentId,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return recurringPayment;
  }

  /**
   * Authenticated resource that lets you (as a merchant) list all the subscriptions customers have made with you. This call returns
   * {@code CoinbaseSubscription} objects where you are the merchant. This is a paged resource and will return the first page by default.
   *
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/index.html">coinbase.com/api/doc/1.0/subscribers/index.html</a>
   */
  public CoinbaseSubscriptions getCoinbaseSubscriptions() throws IOException {

    return getCoinbaseSubscriptions(null, null);
  }

  /**
   * Authenticated resource that lets you (as a merchant) list all the subscriptions customers have made with you. This call returns
   * {@code CoinbaseSubscription} objects where you are the merchant.
   *
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @param limit Optional parameter to limit the maximum number of results to return. Will return up to 25 results by default if null or less than 1.
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/index.html">coinbase.com/api/doc/1.0/subscribers/index.html</a>
   */
  public CoinbaseSubscriptions getCoinbaseSubscriptions(Integer page, final Integer limit) throws IOException {

    final CoinbaseSubscriptions subscriptions = coinbase.getsSubscriptions(page, limit, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return subscriptions;
  }

  /**
   * Authenticated resource that lets you (as a merchant) show an individual subscription than a customer has created with you. This call returns a
   * {@code CoinbaseSubscription} object where you are the merchant.
   *
   * @param subscriptionId
   * @return
   * @throws IOException
   * @see <a href="https://coinbase.com/api/doc/1.0/subscribers/show.html">coinbase.com/api/doc/1.0/subscribers/show.html</a>
   */
  public CoinbaseSubscription getCoinbaseSubscription(String subscriptionId) throws IOException {

    final CoinbaseSubscription subscription = coinbase.getsSubscription(subscriptionId, exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    return subscription;
  }
}
