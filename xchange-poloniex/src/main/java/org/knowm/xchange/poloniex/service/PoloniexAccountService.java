package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDepositsWithdrawalsResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountService extends PoloniexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    List<Balance> balances = getExchangeWallet();
    return new AccountInfo(new Wallet(balances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    return withdrawFunds(currency, amount, address, null);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return getDepositAddress(currency.toString());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    final DefaultTradeHistoryParamsTimeSpan params = new DefaultTradeHistoryParamsTimeSpan();
    params.setStartTime(new Date(System.currentTimeMillis() - 366L * 24 * 60 * 60 * 1000)); // just over one year
    params.setEndTime(new Date());
    return params;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, IOException{
    Date start = null;
    Date end = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      start = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      end = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }
    final PoloniexDepositsWithdrawalsResponse poloFundings = returnDepositsWithdrawals(start, end);
    return PoloniexAdapters.adaptFundingRecords(poloFundings);
  }
}
