package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXAdapters;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryEntry;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponse;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponseWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * <p>
 * XChange service to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class ANXAccountService extends ANXAccountServiceRaw implements AccountService {

  /**
   * Constructor
   */
  public ANXAccountService(BaseExchange baseExchange) {

    super(baseExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ANXAdapters.adaptAccountInfo(getANXAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    if (amount.scale() > ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE) {
      throw new IllegalArgumentException("Amount scale exceed " + ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE);
    }

    if (address == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }

    ANXWithdrawalResponseWrapper wrapper = anxWithdrawFunds(currency.toString(), amount, address);
    ANXWithdrawalResponse response = wrapper.getAnxWithdrawalResponse();

    //eg: {  "result": "error",  "data": {    "message": "min size, params, or available funds problem."  }}
    if (wrapper.getResult().equals("error")) {
      throw new IllegalStateException("Failed to withdraw funds: " + response.getMessage());
    } else if (wrapper.getError() != null) {//does this ever happen?
      throw new IllegalStateException("Failed to withdraw funds: " + wrapper.getError());
    } else {
      return response.getTransactionId();
    }
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return anxRequestDepositAddress(currency.toString()).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<FundingRecord> results = new ArrayList<>();

    List<ANXWalletHistoryEntry> walletHistory = getWalletHistory(params);
    for (ANXWalletHistoryEntry entry : walletHistory) {

      if (!entry.getType().equalsIgnoreCase("deposit") && !entry.getType().equalsIgnoreCase("withdraw"))
        continue;

      results.add(ANXAdapters.adaptFundingRecord(entry));
    }
    return results;
  }

  public static class AnxFundingHistoryParams implements TradeHistoryParamCurrency, TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {

    private Currency currency;
    private Integer pageNumber;
    private Integer pageLength;//not supported
    private Date startTime;
    private Date endTime;

    public AnxFundingHistoryParams() {
    }

    public AnxFundingHistoryParams(Currency currency, Date startTime, Date endTime) {
      this.currency = currency;
      this.startTime = startTime;
      this.endTime = endTime;
    }

    @Override
    public void setCurrency(Currency currency) {
      this.currency = currency;
    }

    @Override
    public Currency getCurrency() {
      return currency;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      //not supported, failed quietly
    }

    @Override
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      if (pageNumber != null && pageNumber == 0)
        throw new IllegalStateException("Pages are '1' indexed");
      this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageNumber() {
      return pageNumber;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }
  }
}
