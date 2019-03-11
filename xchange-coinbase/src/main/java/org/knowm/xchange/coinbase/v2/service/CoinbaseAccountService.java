package org.knowm.xchange.coinbase.v2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public final class CoinbaseAccountService extends CoinbaseAccountServiceRaw
    implements AccountService {

  public CoinbaseAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    List<CoinbaseAccountData.CoinbaseAccount> coinbaseAccounts = getCoinbaseAccounts();
    for (CoinbaseAccountData.CoinbaseAccount coinbaseAccount : coinbaseAccounts) {
      CoinbaseAmount balance = coinbaseAccount.getBalance();
      Wallet wallet =
          new Wallet(
              coinbaseAccount.getId(),
              new Balance(Currency.getInstance(balance.getCurrency()), balance.getAmount()));
      wallets.add(wallet);
    }

    return new AccountInfo(wallets);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
