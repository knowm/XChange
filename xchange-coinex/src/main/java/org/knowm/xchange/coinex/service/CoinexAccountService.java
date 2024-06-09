package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexErrorAdapter;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CoinexAccountService extends CoinexAccountServiceRaw implements AccountService {

  public CoinexAccountService(CoinexExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<CoinexBalanceInfo> spotBalances = getCoinexBalances();

      Wallet wallet = CoinexAdapters.toWallet(spotBalances);
      return new AccountInfo(wallet);

    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return null;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    return null;
  }
}
