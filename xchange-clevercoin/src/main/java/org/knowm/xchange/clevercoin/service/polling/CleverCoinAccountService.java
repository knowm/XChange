package org.knowm.xchange.clevercoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.clevercoin.CleverCoinAdapters;
import org.knowm.xchange.clevercoin.dto.account.CleverCoinDepositAddress;
import org.knowm.xchange.clevercoin.dto.account.CleverCoinWithdrawal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinAccountService extends CleverCoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), CleverCoinAdapters.adaptWallet(getCleverCoinBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    final CleverCoinWithdrawal response = withdrawCleverCoinFunds(amount, address);
    return Integer.toString(response.getId());
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    final CleverCoinDepositAddress response = getCleverCoinBitcoinDepositAddress();
    return response.getDepositAddress();

  }
}
