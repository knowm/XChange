package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.itbit.v1.ItBitAdapters;
import org.knowm.xchange.service.account.AccountService;

public class ItBitAccountService extends ItBitAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ItBitAdapters.adaptAccountInfo(getItBitAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawItBitFunds(currency.toString(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return requestItBitDepositAddress(currency.toString(), args);
  }
}
