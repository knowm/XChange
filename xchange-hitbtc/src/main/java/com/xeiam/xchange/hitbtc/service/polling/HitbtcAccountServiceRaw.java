package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.*;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;

public class HitbtcAccountServiceRaw extends HitbtcBasePollingService<HitbtcAuthenticated> {

  public HitbtcAccountServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(HitbtcAuthenticated.class, exchangeSpecification, nonceFactory);
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
