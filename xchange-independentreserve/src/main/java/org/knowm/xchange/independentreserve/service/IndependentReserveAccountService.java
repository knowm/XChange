package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransaction;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveAccountService extends IndependentReserveAccountServiceRaw
    implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public IndependentReserveAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        IndependentReserveAdapters.adaptWallet(getIndependentReserveBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    withdrawDigitalCurrency(amount, address, "");
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new IndependentReserveTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    if (params instanceof IndependentReserveTradeHistoryParams) {
      IndependentReserveTradeHistoryParams historyParams =
          (IndependentReserveTradeHistoryParams) params;
      AccountInfo accountInfo = this.getAccountInfo();

      Set<Currency> currencies = new HashSet<>();
      if (historyParams.getCurrency() == null) {
        for (Wallet wallet : accountInfo.getWallets().values()) {
          currencies.addAll(wallet.getBalances().keySet());
        }
      } else {
        currencies.add(historyParams.getCurrency());
      }
      List<FundingRecord> fundingRecords = new ArrayList<>();

      for (Wallet wallet : accountInfo.getWallets().values()) {
        for (Map.Entry<Currency, Balance> e : wallet.getBalances().entrySet()) {
          if (currencies.contains(e.getKey())) {
            fundingRecords.addAll(
                IndependentReserveAdapters.adaptTransaction(
                    super.getTransactions(
                        wallet.getId(),
                        historyParams.startTime,
                        historyParams.endTime,
                        historyParams.transactionTypes,
                        historyParams.getPageNumber(),
                        historyParams.getPageLength())));
          }
        }
      }
      return fundingRecords;
    } else {
      throw new IllegalArgumentException(
          "Invalid TradeHistoryParams used as argument of getFundingHistory");
    }
  }

  public static class IndependentReserveTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParams, TradeHistoryParamCurrency, TradeHistoryParamsTimeSpan {

    private Currency currency;
    private Date startTime;
    private Date endTime;
    private IndependentReserveTransaction.Type[] transactionTypes;

    public IndependentReserveTradeHistoryParams() {
      transactionTypes =
          new IndependentReserveTransaction.Type[] {
            IndependentReserveTransaction.Type.Brokerage,
            IndependentReserveTransaction.Type.Deposit,
            IndependentReserveTransaction.Type.Withdrawal,
            IndependentReserveTransaction.Type.Trade
          };
      setPageLength(50);
      setPageNumber(1);
    }

    @Override
    public Currency getCurrency() {
      return currency;
    }

    @Override
    public void setCurrency(Currency currency) {
      this.currency = currency;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    public IndependentReserveTransaction.Type[] getTransactionTypes() {
      return transactionTypes;
    }

    public void setTransactionTypes(IndependentReserveTransaction.Type[] transactionTypes) {
      this.transactionTypes = transactionTypes;
    }
  }
}
