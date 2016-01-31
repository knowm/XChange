package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyAccountService extends CryptsyAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException, ExchangeException {

    return new AccountInfo(CryptsyAdapters.adaptWallet(getCryptsyAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException, ExchangeException {

    return makeCryptsyWithdrawal(address, amount).getReturnValue();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException, ExchangeException {
    return getCurrentCryptsyDepositAddresses().getReturnValue().get(currency);
    // return generateNewCryptsyDepositAddress(null, currency).getReturnValue().getAddress();
  }
}
