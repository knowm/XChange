package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.BitVcAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BitVcAccountService extends BitVcAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitVcAdapters.adaptAccountInfo(getBitVcAccountInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) {

    throw new NotAvailableFromExchangeException();
  }

}
