package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Matija Mazi */
public class BTCMarketsAccountService extends BTCMarketsAccountServiceRaw
    implements AccountService {

  public BTCMarketsAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        BTCMarketsAdapters.adaptWallet(getBTCMarketsBalance()));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;
      return withdrawCrypto(
          defaultWithdrawFundsParams.getAddress(),
          defaultWithdrawFundsParams.getAmount(),
          defaultWithdrawFundsParams.getCurrency());
    }
    throw new IllegalStateException("Cannot process " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
