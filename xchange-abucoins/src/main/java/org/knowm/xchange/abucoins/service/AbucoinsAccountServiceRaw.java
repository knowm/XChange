package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbucoinsAccountServiceRaw extends AbucoinsBaseService {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAccountServiceRaw.class);

  public AbucoinsAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  public AbucoinsAccount[] getAbucoinsAccounts() throws IOException {
    AbucoinsAccount[] account = abucoinsAuthenticated.getAccounts(exchange.getExchangeSpecification().getApiKey(),
                                                                  signatureCreator,
                                                                  exchange.getExchangeSpecification().getPassword(),
                                                                  signatureCreator.timestamp());
    return account;
  }
  
  public AbucoinsPaymentMethod[] getPaymentMethods() throws IOException {
    return abucoinsAuthenticated.getPaymentMethods(exchange.getExchangeSpecification().getApiKey(),
                                                   signatureCreator,
                                                   exchange.getExchangeSpecification().getPassword(),
                                                   signatureCreator.timestamp());
  }
  
  public AbucoinsCryptoWithdrawal abucoinsWithdraw(AbucoinsCryptoWithdrawalRequest withdrawRequest) throws IOException {
    return abucoinsAuthenticated.cryptoWithdrawal(exchange.getExchangeSpecification().getApiKey(),
                                                  signatureCreator,
                                                  exchange.getExchangeSpecification().getPassword(),
                                                  signatureCreator.timestamp(),
                                                  withdrawRequest);
  }
  
  public AbucoinsCryptoDeposit getAbucoinsCryptoDeposit(AbucoinsCryptoDepositRequest cryptoRequest) throws IOException {
    return abucoinsAuthenticated.cryptoDeposit(exchange.getExchangeSpecification().getApiKey(),
                                               signatureCreator,
                                               exchange.getExchangeSpecification().getPassword(),
                                               signatureCreator.timestamp(),
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
