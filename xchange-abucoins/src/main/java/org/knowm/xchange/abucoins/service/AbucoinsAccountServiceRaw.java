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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Class providing a 1:1 proxy for the Abucoins account related
 * REST requests.</p>
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
      throw new IOException( accounts.getAccounts()[0].getMessage() );
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
      throw new IOException( account.getMessage() );
    
    return account;
  }
  
  public AbucoinsPaymentMethod[] getPaymentMethods() throws IOException {
    AbucoinsPaymentMethods paymentMethods = abucoinsAuthenticated.getPaymentMethods(exchange.getExchangeSpecification().getApiKey(),
                                                                                    signatureCreator,
                                                                                    exchange.getExchangeSpecification().getPassword(),
                                                                                    timestamp());
    
    if ( paymentMethods.getPaymentMethods().length == 1 && paymentMethods.getPaymentMethods()[0].getMessage() != null )
      throw new IOException(paymentMethods.getPaymentMethods()[0].getMessage());
    return paymentMethods.getPaymentMethods();
  }
  
  public AbucoinsCryptoWithdrawal abucoinsWithdraw(AbucoinsCryptoWithdrawalRequest withdrawRequest) throws IOException {
    return abucoinsAuthenticated.cryptoWithdrawal(exchange.getExchangeSpecification().getApiKey(),
                                                  signatureCreator,
                                                  exchange.getExchangeSpecification().getPassword(),
                                                  timestamp(),
                                                  withdrawRequest);
  }

  public AbucoinsCryptoDeposit getAbucoinsCryptoDeposit(AbucoinsCryptoDepositRequest cryptoRequest) throws IOException {
    return abucoinsAuthenticated.cryptoDeposit(exchange.getExchangeSpecification().getApiKey(),
                                               signatureCreator,
                                               exchange.getExchangeSpecification().getPassword(),
                                               timestamp(),
                                               cryptoRequest);
  }
  
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
