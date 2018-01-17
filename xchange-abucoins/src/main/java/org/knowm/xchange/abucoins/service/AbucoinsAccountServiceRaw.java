package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccounts;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethods;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Class providing a 1:1 proxy for the Abucoins account related
 * REST requests.</p>
 * 
 * <ul>
 * <li>{@link #getAbucoinsAccounts GET /accounts}</li>
 * <li>{@link #getAbucoinsAccount(String) GET /accounts/&lt;account-id&gt;}</li>
 * <li>{@link #getPaymentMethods() GET /payment-methods}</li>
 * <li>{@link #abucoinsWithdraw POST withdrawals/crypto}</li>
 * <li>{@link #abucoinsCryptoDeposit POST deposits/crypto}</li>
 * <ol>
 * @author bryant_harris
 */
public class AbucoinsAccountServiceRaw extends AbucoinsBaseService {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAccountServiceRaw.class);

  public AbucoinsAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  /**
   * Corresponds to <code>GET /accounts</code>
   * @return
   * @throws IOException
   */
  public AbucoinsAccount[] getAbucoinsAccounts() throws IOException {
    AbucoinsAccounts accounts = abucoinsAuthenticated.getAccounts(exchange.getExchangeSpecification().getApiKey(),
                                                                  signatureCreator,
                                                                  exchange.getExchangeSpecification().getPassword(),
                                                                  timestamp());
    
    if ( accounts.getAccounts().length == 1 && accounts.getAccounts()[0].getMessage() != null )
      throw new ExchangeException( accounts.getAccounts()[0].getMessage() );
    return accounts.getAccounts();
  }
  
  /**
   * Corresponds to <code>GET /accounts/&lt;account-id&gt;</code>
   * @param accountID
   * @return
   * @throws IOException
   */
  public AbucoinsAccount getAbucoinsAccount(String accountID) throws IOException {
    AbucoinsAccount account = abucoinsAuthenticated.getAccount(accountID,
                                                               exchange.getExchangeSpecification().getApiKey(),
                                                               signatureCreator,
                                                               exchange.getExchangeSpecification().getPassword(),
                                                               timestamp());
    if ( account.getMessage() != null )
      throw new ExchangeException( account.getMessage() );
    
    return account;
  }
  
  /**
   * Corresponds to <code>GET /payment-methods</code>
   * @return
   * @throws IOException
   */
  public AbucoinsPaymentMethod[] getPaymentMethods() throws IOException {
    AbucoinsPaymentMethods paymentMethods = abucoinsAuthenticated.getPaymentMethods(exchange.getExchangeSpecification().getApiKey(),
                                                                                    signatureCreator,
                                                                                    exchange.getExchangeSpecification().getPassword(),
                                                                                    timestamp());
    
    if ( paymentMethods.getPaymentMethods().length == 1 && paymentMethods.getPaymentMethods()[0].getMessage() != null )
      throw new ExchangeException(paymentMethods.getPaymentMethods()[0].getMessage());
    return paymentMethods.getPaymentMethods();
  }
  
  /**
   * Corresponds to <code>POST withdrawals/crypto</code>
   * @param withdrawRequest
   * @return
   * @throws IOException
   */
  public AbucoinsCryptoWithdrawal abucoinsWithdraw(AbucoinsCryptoWithdrawalRequest withdrawRequest) throws IOException {
    return abucoinsAuthenticated.cryptoWithdrawal(withdrawRequest,
                                                  exchange.getExchangeSpecification().getApiKey(),
                                                  signatureCreator,
                                                  exchange.getExchangeSpecification().getPassword(),
                                                  timestamp());
  }

  /**
   * Corresponds to <code>POST deposits/crypto</code>
   * @param cryptoRequest
   * @return
   * @throws IOException
   */
  public AbucoinsCryptoDeposit abucoinsCryptoDeposit(AbucoinsCryptoDepositRequest cryptoRequest) throws IOException {
    return abucoinsAuthenticated.cryptoDeposit(cryptoRequest,
                                               exchange.getExchangeSpecification().getApiKey(),
                                               signatureCreator,
                                               exchange.getExchangeSpecification().getPassword(),
                                               timestamp());
  }
  
  /**
   * Helper method that obtains the payment method for a given currency, based on the payment-method information
   * returned from abucoins.
   * @param currency
   * @return The type (string) of the payment method.
   * @throws IOException
   */
  public String abucoinsPaymentMethodForCurrency(String currency) throws IOException {
    String method = null;
    AbucoinsPaymentMethod[] paymentMethods = getPaymentMethods();
    for ( AbucoinsPaymentMethod apm : paymentMethods ) {
      if ( apm.getCurrency().equals(currency)) {
        method = apm.getType();
        break;
      }
    }
            
    if ( method == null )
      logger.warn("Unable to determine the payment method suitable for " + currency + " this will likely lead to an error");

    return method;
  }
}
