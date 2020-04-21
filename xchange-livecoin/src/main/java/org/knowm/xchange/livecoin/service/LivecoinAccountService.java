package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinErrorAdapter;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class LivecoinAccountService extends LivecoinAccountServiceRaw implements AccountService {
  public LivecoinAccountService(
      LivecoinExchange exchange, Livecoin livecoin, ResilienceRegistries resilienceRegistries) {
    super(exchange, livecoin, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      return new AccountInfo(LivecoinAdapters.adaptWallet(balances(null)));
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
      if (!(params instanceof DefaultWithdrawFundsParams)) {
        throw new IllegalStateException("Unsupported params class " + params.getClass().getName());
      }
      return withdraw((DefaultWithdrawFundsParams) params);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    try {
      return walletAddress(currency);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      Date start = new Date(0);
      Date end = new Date();
      if (params instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
        start = tradeHistoryParamsTimeSpan.getStartTime();
        end = tradeHistoryParamsTimeSpan.getEndTime();
      }

      Long offset = 0L;
      if (params instanceof TradeHistoryParamOffset) {
        offset = ((TradeHistoryParamOffset) params).getOffset();
      }

      Integer limit = 100;
      if (params instanceof TradeHistoryParamLimit) {
        limit = ((TradeHistoryParamLimit) params).getLimit();
      }

      List<Map> fundingRaw = funding(start, end, limit, offset);
      return fundingRaw.stream()
          .map(LivecoinAdapters::adaptFundingRecord)
          .collect(Collectors.toList());
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public LivecoinFoundingHistoryParams createFundingHistoryParams() {
    return new LivecoinFoundingHistoryParams();
  }

  public static final class LivecoinFoundingHistoryParams
      implements TradeHistoryParamsTimeSpan, TradeHistoryParamOffset, TradeHistoryParamLimit {

    private Date startTime = new Date(0);
    private Date endTime = new Date();
    private Integer limit = 100;
    private Long offset = 0L;

    public Date getStartTime() {
      return startTime;
    }

    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    public Date getEndTime() {
      return endTime;
    }

    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    public Integer getLimit() {
      return limit;
    }

    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    public Long getOffset() {
      return offset;
    }

    public void setOffset(Long offset) {
      this.offset = offset;
    }
  }
}
