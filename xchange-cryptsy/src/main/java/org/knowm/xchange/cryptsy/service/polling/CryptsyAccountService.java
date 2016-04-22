package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.polling.account.PollingAccountService;

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
