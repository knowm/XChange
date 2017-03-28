package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/**
 * Author: brox Since: 2/6/14
 */

public class CexIOAccountService extends CexIOAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), CexIOAdapters.adaptWallet(getCexIOAccountInfo()));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();

  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
    throw new NotYetImplementedForExchangeException();
  }
}
