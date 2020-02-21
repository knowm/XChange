package org.knowm.xchange.gatehub.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gatehub.GatehubAdapters;
import org.knowm.xchange.gatehub.dto.Network;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * @author ObsessiveOrange
 */
public class GatehubAccountService extends GatehubAccountServiceRaw implements PollingAccountService {

  public GatehubAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException, ExchangeException {
    return new AccountInfo(new Wallet(this.walletAddress, GatehubAdapters.adaptBalances(getGatehubBalances())));
  }

  /**
   * Withdraws funds to the network native for the currency.
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException, ExchangeException {
    Network withdrawVia = Network.getNative(currency);
    return pay(amount, currency, address, withdrawVia).getUuid();
  }

  public String withdrawFundsToRipple(Currency currency, BigDecimal amount, String address) throws IOException, ExchangeException {
    return pay(amount, currency, address, Network.ripple).getUuid();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException, ExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
