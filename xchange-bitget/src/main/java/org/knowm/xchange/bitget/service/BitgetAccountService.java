package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.bitget.service.params.BitgetFundingHistoryParams;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitgetAccountService extends BitgetAccountServiceRaw implements AccountService {

  public BitgetAccountService(BitgetExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<BitgetBalanceDto> spotBalances = getBitgetBalances(null);
      Wallet wallet = BitgetAdapters.toWallet(spotBalances);
      return new AccountInfo(wallet);

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return BitgetFundingHistoryParams.builder().build();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    // return withdrawals and deposits combined
    return Stream.of(getBitgetDepositRecords(params), getBitgetWithdrawRecords(params))
        .flatMap(List::stream)
        .map(BitgetAdapters::toFundingRecord)
        .collect(Collectors.toList());
  }
}
