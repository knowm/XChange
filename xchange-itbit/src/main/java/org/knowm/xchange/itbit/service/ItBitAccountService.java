package org.knowm.xchange.itbit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.itbit.ItBitAdapters;
import org.knowm.xchange.itbit.dto.ItBitFunding;
import org.knowm.xchange.itbit.dto.ItBitFundingHistoryResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class ItBitAccountService extends ItBitAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return ItBitAdapters.adaptAccountInfo(getItBitAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {

    return withdrawItBitFunds(currency.toString(), amount, address);
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
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return requestItBitDepositAddress(currency.toString(), args);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    int page = 1;
    int perPage = 50;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging tradeHistoryParamPaging = (TradeHistoryParamPaging) params;
      perPage = tradeHistoryParamPaging.getPageLength();
      page = tradeHistoryParamPaging.getPageNumber();
    }

    List<FundingRecord> fundingRecords = new ArrayList<>();

    ItBitFundingHistoryResponse funding = getFunding(page, perPage);
    if (funding.fundingHistory != null) {
      for (ItBitFunding itBitFunding : funding.fundingHistory) {
        FundingRecord fundingRecord = ItBitAdapters.adapt(itBitFunding);
        fundingRecords.add(fundingRecord);
      }
    }
    return fundingRecords;
  }

  public static class ItBitFundingParams implements TradeHistoryParamPaging {

    private Integer pageLength = 50;
    private Integer pageNumber = 1;

    @Override
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.pageLength = pageLength;
    }

    @Override
    public Integer getPageNumber() {
      return pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      this.pageNumber = pageNumber;
    }
  }
}
