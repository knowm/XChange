package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXAdapters;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryEntry;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponse;
import org.knowm.xchange.anx.v2.dto.account.ANXWithdrawalResponseWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import si.mazi.rescu.IRestProxyFactory;

/**
 * XChange service to provide the following to {@link org.knowm.xchange.Exchange}:
 *
 * <ul>
 *   <li>ANX specific methods to handle account-related operations
 * </ul>
 */
public class ANXAccountService extends ANXAccountServiceRaw implements AccountService {

  /** Constructor */
  public ANXAccountService(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange, restProxyFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ANXAdapters.adaptAccountInfo(getANXAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {

    if (amount.scale() > ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE) {
      throw new IllegalArgumentException(
          "Amount scale exceed " + ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE);
    }

    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }

    ANXWithdrawalResponseWrapper wrapper = anxWithdrawFunds(currency.toString(), amount, address);
    return handleWithdrawalResponse(wrapper);
  }

  public String withdrawFunds(Currency currency, BigDecimal amount, String address, String tag)
      throws IOException {

    if (amount.scale() > ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE) {
      throw new IllegalArgumentException(
          "Amount scale exceed " + ANXUtils.VOLUME_AND_AMOUNT_MAX_SCALE);
    }

    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }

    if (tag == null) {
      throw new IllegalArgumentException("destinationTag cannot be null");
    }

    ANXWithdrawalResponseWrapper wrapper =
        anxWithdrawFunds(currency.toString(), amount, address, tag);
    return handleWithdrawalResponse(wrapper);
  }

  private String handleWithdrawalResponse(ANXWithdrawalResponseWrapper wrapper) {
    ANXWithdrawalResponse response = wrapper.getAnxWithdrawalResponse();

    // eg: {  "result": "error",  "data": {    "message": "min size, params, or available funds
    // problem."  }}
    if (wrapper.getResult().equals("error")) {
      throw new IllegalStateException("Failed to withdraw funds: " + response.getMessage());
    } else if (wrapper.getError() != null) { // does this ever happen?
      throw new IllegalStateException("Failed to withdraw funds: " + wrapper.getError());
    } else {
      return response.getTransactionId();
    }
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {

    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;
      return withdrawFunds(
          rippleWithdrawFundsParams.getCurrency(),
          rippleWithdrawFundsParams.getAmount(),
          rippleWithdrawFundsParams.getAddress(),
          rippleWithdrawFundsParams.getTag());
    }
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return anxRequestDepositAddress(currency.toString()).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new AnxFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<FundingRecord> results = new ArrayList<>();

    List<ANXWalletHistoryEntry> walletHistory = getWalletHistory(params);
    for (ANXWalletHistoryEntry entry : walletHistory) {

      if (!entry.getType().equalsIgnoreCase("deposit")
          && !entry.getType().equalsIgnoreCase("withdraw")) continue;

      // ANX returns the fee in a separate WalletHistoryEntry, but with the same transaction id.
      // merging the two into
      // a single FundingRecord
      ANXWalletHistoryEntry feeEntry =
          walletHistory.parallelStream()
              .filter(
                  anxWalletHistoryEntry ->
                      "fee".equalsIgnoreCase(anxWalletHistoryEntry.getType())
                          && ((entry.getTransactionId() != null
                                  && Objects.equals(
                                      anxWalletHistoryEntry.getTransactionId(),
                                      entry.getTransactionId()))
                              || (entry.getInfo() != null
                                  && Objects.equals(
                                      anxWalletHistoryEntry.getInfo(), entry.getInfo()))))
              .findFirst()
              .orElse(null);

      results.add(ANXAdapters.adaptFundingRecord(entry, feeEntry));
    }
    return results;
  }

  public static class AnxFundingHistoryParams
      implements TradeHistoryParamCurrency, TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {

    private Currency currency;
    private Integer pageNumber;
    private Integer pageLength; // not supported
    private Date startTime;
    private Date endTime;

    AnxFundingHistoryParams() {}

    public AnxFundingHistoryParams(Currency currency, Date startTime, Date endTime) {
      this.currency = currency;
      this.startTime = startTime;
      this.endTime = endTime;
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
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      // not supported, failed quietly
    }

    @Override
    public Integer getPageNumber() {
      return pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      if (pageNumber != null && pageNumber == 0)
        throw new IllegalStateException("Pages are '1' indexed");
      this.pageNumber = pageNumber;
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
  }
}
