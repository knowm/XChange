package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
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
      withdrawCrypto(
          defaultWithdrawFundsParams.getAddress(),
          defaultWithdrawFundsParams.getAmount(),
          defaultWithdrawFundsParams.getCurrency());
      // The BTCMarkets API doesn't return a useful value such as an id but the fixed value 'Pending
      // Authorization'
      // See https://github.com/BTCMarkets/API/issues/137
      // and
      // https://github.com/BTCMarkets/API/wiki/Fund-Transfer-API
      return null;
    }
    throw new IllegalStateException("Cannot process " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BTCMarketsTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    return BTCMarketsAdapters.adaptFundingHistory(super.fundtransferHistory());
  }
}
