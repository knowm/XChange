package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.BittrexAdapters;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BittrexAccountService extends BittrexAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BittrexAdapters.adaptWallet(getBittrexAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdraw(currency.getCurrencyCode(), amount, address) ;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    return getBittrexDepositAddress(currency.toString());
  }
  
}