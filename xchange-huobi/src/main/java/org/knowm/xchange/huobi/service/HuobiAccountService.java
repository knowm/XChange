package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.huobi.dto.account.HuobiAccount;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class HuobiAccountService extends HuobiAccountServiceRaw implements AccountService {

  public HuobiAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
	if (params instanceof DefaultWithdrawFundsParams) {
	  DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
	    return withdrawFunds(
	      defaultParams.getCurrency(), 
	      defaultParams.getAmount(), 
	      defaultParams.getAddress());
	  }
	throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return String.valueOf(createWithdraw(currency.toString(), amount, null, address, null));
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    HuobiAccount[] accounts = getAccounts();
    if (accounts.length == 0) {
      throw new ExchangeException("Account is not recognized.");
    }
    String accountID = String.valueOf(accounts[0].getId());
    return new AccountInfo(
        accountID,
        HuobiAdapters.adaptWallet(
            HuobiAdapters.adaptBalance(getHuobiBalance(accountID).getList())));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams tradeHistoryParams)
      throws IOException {
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... strings) throws IOException {
    return getDepositAddress(currency.toString());
  }
}
