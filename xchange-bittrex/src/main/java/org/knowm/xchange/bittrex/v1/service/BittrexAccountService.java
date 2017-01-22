package org.knowm.xchange.bittrex.v1.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.v1.BittrexAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class BittrexAccountService extends BittrexAccountServiceRaw implements AccountService {

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

    return withdraw(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    return getBittrexDepositAddress(currency.toString());
  }

}