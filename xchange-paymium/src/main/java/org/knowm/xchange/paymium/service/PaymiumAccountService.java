package org.knowm.xchange.paymium.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.paymium.PaymiumAdapters;
import org.knowm.xchange.service.account.AccountService;

public class PaymiumAccountService extends PaymiumAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PaymiumAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(PaymiumAdapters.adaptWallet(getPaymiumBalances()));
  }
}
