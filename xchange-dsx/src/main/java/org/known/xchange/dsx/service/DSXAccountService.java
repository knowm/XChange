package org.known.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.known.xchange.dsx.DSXAdapters;
import org.known.xchange.dsx.dto.account.DSXAccountInfo;

/**
 * @author Mikhail Wall
 */
public class DSXAccountService extends DSXAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    DSXAccountInfo info = getDSXAccountInfo();
    return new AccountInfo(DSXAdapters.adaptWallet(info));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawCrypto(currency.toString(), address, amount, null);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return requestAddress(currency.toString(), Integer.parseInt(args[0]));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
