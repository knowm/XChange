package org.knowm.xchange.vaultoro.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.vaultoro.VaultoroAdapters;
import org.knowm.xchange.vaultoro.dto.account.VaultoroBalance;

public class VaultoroAccountService extends VaultoroAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    List<VaultoroBalance> vaultoroBalances = super.getVaultoroBalances();
    return VaultoroAdapters.adaptVaultoroBalances(vaultoroBalances);
  }

  @Override
  public String requestDepositAddress(Currency arg0, String... arg1) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(Currency arg0, BigDecimal arg1, String arg2) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
