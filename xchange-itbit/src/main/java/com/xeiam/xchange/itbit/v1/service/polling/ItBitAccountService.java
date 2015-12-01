package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class ItBitAccountService extends ItBitAccountServiceRaw implements PollingAccountService {

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
