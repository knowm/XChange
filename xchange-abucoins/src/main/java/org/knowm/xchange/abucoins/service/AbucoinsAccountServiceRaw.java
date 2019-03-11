package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccounts;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.abucoins.dto.account.AbucoinsDepositsHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethods;
import org.knowm.xchange.abucoins.dto.account.AbucoinsWithdrawalsHistory;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class providing a 1:1 proxy for the Abucoins account related REST requests.
 *
 * <ul>
 *   <li>{@link #getAbucoinsAccounts GET /accounts}
 *   <li>{@link #getAbucoinsAccount(String) GET /accounts/&lt;account-id&gt;}
 *   <li>{@link #getPaymentMethods() GET /payment-methods}
 *   <li>{@link #abucoinsWithdrawalsMake POST withdrawals/make}
 *   <li>{@link #abucoinsWithdrawalsHistory POST withdrawals/history}
 *   <li>{@link #abucoinsDepositMake POST deposits/make}
 *   <li>{@link #abucoinsDepositHistory POST deposits/history}
 *       <ol>
 *
 * @author bryant_harris
 */
public class AbucoinsAccountServiceRaw extends AbucoinsBaseService {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAccountServiceRaw.class);

  public AbucoinsAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Corresponds to <code>GET /accounts</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsAccount[] getAbucoinsAccounts() throws IOException {
    AbucoinsAccounts accounts =
        abucoinsAuthenticated.getAccounts(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());

    if (accounts.getAccounts().length == 1 && accounts.getAccounts()[0].getMessage() != null)
      throw new ExchangeException(accounts.getAccounts()[0].getMessage());
    return accounts.getAccounts();
  }

  /**
   * Corresponds to <code>GET /accounts/&lt;account-id&gt;</code>
   *
   * @param accountID
   * @return
   * @throws IOException
   */
  public AbucoinsAccount getAbucoinsAccount(String accountID) throws IOException {
    AbucoinsAccount account =
        abucoinsAuthenticated.getAccount(
            accountID,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());
    if (account.getMessage() != null) throw new ExchangeException(account.getMessage());

    return account;
  }

  /**
   * Corresponds to <code>GET /payment-methods</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsPaymentMethod[] getPaymentMethods() throws IOException {
    AbucoinsPaymentMethods paymentMethods =
        abucoinsAuthenticated.getPaymentMethods(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());

    if (paymentMethods.getPaymentMethods().length == 1
        && paymentMethods.getPaymentMethods()[0].getMessage() != null)
      throw new ExchangeException(paymentMethods.getPaymentMethods()[0].getMessage());
    return paymentMethods.getPaymentMethods();
  }

  /**
   * Corresponds to <code>POST withdrawals/crypto</code>
   *
   * @param withdrawRequest
   * @return
   * @throws IOException
   */
  public AbucoinsCryptoWithdrawal abucoinsWithdrawalsMake(
      AbucoinsCryptoWithdrawalRequest withdrawRequest) throws IOException {
    return abucoinsAuthenticated.withdrawalsMake(
        withdrawRequest,
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getExchangeSpecification().getPassword(),
        timestamp());
  }

  /**
   * Corresponds to <code>GET withdrawals/history</code>
   *
   * @param cryptoRequest
   * @return
   * @throws IOException
   */
  public AbucoinsWithdrawalsHistory abucoinsWithdrawalsHistory() throws IOException {
    AbucoinsWithdrawalsHistory history =
        abucoinsAuthenticated.withdrawalsHistory(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());
    if (history.getHistory().length > 0 && history.getHistory()[0].getMessage() != null)
      throw new ExchangeException(history.getHistory()[0].getMessage());

    return history;
  }

  /**
   * Corresponds to <code>POST deposits/make</code>
   *
   * @param cryptoRequest
   * @return
   * @throws IOException
   */
  public AbucoinsCryptoDeposit abucoinsDepositMake(AbucoinsCryptoDepositRequest cryptoRequest)
      throws IOException {
    return abucoinsAuthenticated.depositsMake(
        cryptoRequest,
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getExchangeSpecification().getPassword(),
        timestamp());
  }

  /**
   * Corresponds to <code>GET deposits/history</code>
   *
   * @param cryptoRequest
   * @return
   * @throws IOException
   */
  public AbucoinsDepositsHistory abucoinsDepositHistory() throws IOException {
    AbucoinsDepositsHistory history =
        abucoinsAuthenticated.depositsHistory(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());
    if (history.getHistory().length > 0 && history.getHistory()[0].getMessage() != null)
      throw new ExchangeException(history.getHistory()[0].getMessage());

    return history;
  }

  /**
   * Helper method that obtains the payment method for a given currency, based on the payment-method
   * information returned from abucoins.
   *
   * @param currency
   * @return The type (string) of the payment method.
   * @throws IOException
   */
  public String abucoinsPaymentMethodForCurrency(String currency) throws IOException {
    String method = null;
    AbucoinsPaymentMethod[] paymentMethods = getPaymentMethods();
    for (AbucoinsPaymentMethod apm : paymentMethods) {
      if (apm.getCurrency().equals(currency)) {
        method = apm.getType();
        break;
      }
    }

    if (method == null)
      logger.warn(
          "Unable to determine the payment method suitable for "
              + currency
              + " this will likely lead to an error");

    return method;
  }
}
