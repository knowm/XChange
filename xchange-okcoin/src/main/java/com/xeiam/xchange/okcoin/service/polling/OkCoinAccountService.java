package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.dto.account.OKCoinWithdraw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class OkCoinAccountService extends OkCoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinAccountService(Exchange exchange) {

    super(exchange);
    
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfo(getUserInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
      String okcoinCurrency = currency.compareTo(Currency.BTC) == 0  ? "btc_usd" : "ltc_usd";
      String fee =   currency.compareTo(Currency.BTC) == 0 ? "0.0001" : "0.001";
      OKCoinWithdraw result = withdraw(null, okcoinCurrency, address, amount, fee);      
       
      if(result != null)
           return result.getWithdrawId();
       
      return ""; 
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
