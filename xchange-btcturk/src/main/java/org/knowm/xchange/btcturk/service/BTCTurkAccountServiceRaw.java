package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.BTCTurkSort;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;

/** @author mertguner */
public class BTCTurkAccountServiceRaw extends BTCTurkBaseService {

  public BTCTurkAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCTurkAccountBalance getBTCTurkBalance() throws IOException {

    final BTCTurkAccountBalance balance =
        btcTurk.getBalance(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    return balance;
  }

  public List<BTCTurkUserTransactions> getBTCTurkUserTransactions() throws IOException {
    return getBTCTurkUserTransactions(0, 25, BTCTurkSort.SORT_ASCENDING);
  }

  public List<BTCTurkUserTransactions> getBTCTurkUserTransactions(
      int offset, int limit, BTCTurkSort sort) throws IOException {

    return btcTurk.getUserTransactions(
        offset,
        limit,
        sort.toString(),
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator);
  }
}
