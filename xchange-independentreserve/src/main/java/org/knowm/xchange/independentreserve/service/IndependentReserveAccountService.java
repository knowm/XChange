package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.independentreserve.dto.IndependentReserveHttpStatusException;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransaction;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.MoneroWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
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
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getDigitalCurrencyDepositAddress(currency.getCurrencyCode());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    withdrawDigitalCurrency(amount, address, "", currency.getCurrencyCode(), null);
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {

    if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new IllegalStateException("Don't know how to withdraw: " + params);
    }
    String destinationTag = null;
    if (params instanceof RippleWithdrawFundsParams) {
      destinationTag = ((RippleWithdrawFundsParams) params).getTag();
    }
    if (destinationTag == null && params instanceof MoneroWithdrawFundsParams) {
      destinationTag = ((MoneroWithdrawFundsParams) params).getPaymentId();
    }

    DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
    withdrawDigitalCurrency(
        defaultParams.getAmount(),
        defaultParams.getAddress(),
        "",
        defaultParams.getCurrency().getCurrencyCode(),
        destinationTag);
    return null;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new IndependentReserveTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof IndependentReserveTradeHistoryParams)) {
      throw new IllegalArgumentException(
          "Invalid TradeHistoryParams used as argument of getFundingHistory");
    }
    IndependentReserveTradeHistoryParams historyParams =
        (IndependentReserveTradeHistoryParams) params;
    final IndependentReserveBalance bal = getIndependentReserveBalance();
    final Currency currency = historyParams.getCurrency();
    return bal.getIndependentReserveAccounts().stream()
        .filter(
            acc ->
                currency == null
                    || currency.getCurrencyCode().equalsIgnoreCase(acc.getCurrencyCode()))
        .map(
            acc -> {
              try {
                return getTransactions(
                        acc.getAccountGuid(),
                        historyParams.startTime,
                        historyParams.endTime,
                        historyParams.transactionTypes,
                        historyParams.getPageNumber(),
                        historyParams.getPageLength())
                    .getIndependentReserveTranasactions()
                    .stream()
                    .map(IndependentReserveAdapters::adaptTransaction);
              } catch (IndependentReserveHttpStatusException | IOException e) {
                throw new ExchangeException(e);
              }
            })
        .flatMap(Function.identity())
        .collect(Collectors.toList());
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
