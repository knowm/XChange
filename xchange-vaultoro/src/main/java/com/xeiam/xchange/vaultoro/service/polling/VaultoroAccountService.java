package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.vaultoro.VaultoroAdapters;
import com.xeiam.xchange.vaultoro.dto.account.VaultoroBalance;

public class VaultoroAccountService extends VaultoroAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<VaultoroBalance> vaultoroBalances = super.getVaultoroBalances();
    return VaultoroAdapters.adaptVaultoroBalances(vaultoroBalances);
  }

  @Override
  public String requestDepositAddress(Currency arg0, String... arg1) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(Currency arg0, BigDecimal arg1, String arg2) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }
}