package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAdapters;
import org.knowm.xchange.btcturk.dto.BTCTurkOperations;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * @author mertguner
 */
public class BTCTurkAccountService extends BTCTurkAccountServiceRaw implements AccountService {

  public BTCTurkAccountService(Exchange exchange) {
    super(exchange);
    // TODO Auto-generated constructor stub
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(BTCTurkAdapters.adaptWallet("Main", getBTCTurkBalance()));
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<FundingRecord> records = new ArrayList<>();

    List<BTCTurkUserTransactions> transactions = super.getBTCTurkUserTransactions();
    for (BTCTurkUserTransactions transaction : transactions) {
      if (!transaction.getOperation().equals(BTCTurkOperations.trade))
        records.add(BTCTurkAdapters.adaptTransaction(transaction));
    }

    return records;
  }
}
