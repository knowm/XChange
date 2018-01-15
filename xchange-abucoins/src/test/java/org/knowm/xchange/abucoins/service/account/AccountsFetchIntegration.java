package org.knowm.xchange.abucoins.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.service.AbucoinsAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

/**
 * Not structured as a unit test because it requires API Key information to run properly.
 * @author bryant_harris
 */
public class AccountsFetchIntegration {
  public static final String ABUCOINS_PASSPHRASE = " -- replace with your passphrase --";
  public static final String ABUCOINS_KEY = " -- replace with your key --";
  public static final String ABUCOINS_SECRET = " -- replace with your secret --";
        
  public static void main(String[] args) throws Exception {
    AccountsFetchIntegration test = new AccountsFetchIntegration();
    test.accountsFetchTest();
  }
  
  public void accountsFetchTest() throws Exception {
    ExchangeSpecification exSpec = new AbucoinsExchange().getDefaultExchangeSpecification();

    exSpec.setPassword(ABUCOINS_PASSPHRASE);
    exSpec.setApiKey(ABUCOINS_KEY);
    exSpec.setSecretKey(ABUCOINS_SECRET);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange( exSpec );
      
    AbucoinsAccountService accountService = (AbucoinsAccountService) exchange.getAccountService();
    AbucoinsAccount[] accountInfo = accountService.getAbucoinsAccounts();
    assertThat(accountInfo).isNotNull();
    System.out.println(Arrays.asList(accountInfo));
    
    AccountInfo info = accountService.getAccountInfo();
    System.out.println(info);
    
    AbucoinsPaymentMethod[] paymentMethods = accountService.getPaymentMethods();
    assertThat(paymentMethods).isNotNull();
    System.out.println(Arrays.asList(paymentMethods));
    
    String address = accountService.requestDepositAddress(Currency.BTC);
    assertThat(address).isNotNull();
    System.out.println("BTC: Address " + address);
    
    address = accountService.requestDepositAddress(Currency.ETH);
    assertThat(address).isNotNull();
    System.out.println("ETH: Address " + address);
    
    address = accountService.requestDepositAddress(Currency.BCH);
    assertThat(address).isNotNull();
    System.out.println("BCH: Address " + address);
  }
}
