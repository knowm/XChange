package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements PollingAccountService {

  public HitbtcAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    HitbtcBalance[] accountInfoRaw = getAccountInfoRaw();

    return HitbtcAdapters.adaptAccountInfo(accountInfoRaw);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

}
