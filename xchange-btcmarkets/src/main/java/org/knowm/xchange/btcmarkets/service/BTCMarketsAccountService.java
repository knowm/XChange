package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * @author Matija Mazi
 */
public class BTCMarketsAccountService extends BTCMarketsAccountServiceRaw implements AccountService {

  public BTCMarketsAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), BTCMarketsAdapters.adaptWallet(getBTCMarketsBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
    throw new NotYetImplementedForExchangeException();
  }
}
