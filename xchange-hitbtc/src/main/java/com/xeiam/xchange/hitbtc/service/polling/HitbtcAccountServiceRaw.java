package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;

public class HitbtcAccountServiceRaw extends HitbtcBasePollingService<HitbtcAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   * @param nonceFactory
   */
  public HitbtcAccountServiceRaw(Exchange exchange, SynchronizedValueFactory<Long> nonceFactory) {

    super(HitbtcAuthenticated.class, exchange, nonceFactory);
  }

  public HitbtcBalance[] getAccountInfoRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcBalanceResponse hitbtcBalance = hitbtc.getHitbtcBalance(signatureCreator, valueFactory, apiKey);
      return hitbtcBalance.getBalances();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcBalanceResponse getAccountBaseInfoRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcBalanceResponse hitbtcBalanceResponse = hitbtc.getHitbtcBalance(signatureCreator, valueFactory, apiKey);
      return hitbtcBalanceResponse;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }
}
