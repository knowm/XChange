package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTCAdapters;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author cristian.lucaci
 */
public class LakeBTCAccountService extends LakeBTCAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    LakeBTCAccountInfoResponse response = super.getLakeBTCAccountInfo();
    return LakeBTCAdapters.adaptAccountInfo(response.getResult());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
