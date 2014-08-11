package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;

public class HitbtcAccountServiceRaw extends HitbtcBasePollingService<HitbtcAuthenticated> {

  public HitbtcAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(HitbtcAuthenticated.class, exchangeSpecification);
  }

  public HitbtcBalance[] getAccountInfoRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcBalanceResponse hitbtcBalance = hitbtc.getHitbtcBalance(signatureCreator, valueFactory, apiKey);
    return hitbtcBalance.getBalances();
  }

  public HitbtcBalanceResponse getAccountBaseInfoRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcBalanceResponse hitbtcBalanceResponse = hitbtc.getHitbtcBalance(signatureCreator, valueFactory, apiKey);
    return hitbtcBalanceResponse;
  }
}
