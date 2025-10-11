package org.knowm.xchange.dase.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dase.DaseAdapters;
import org.knowm.xchange.dase.dto.account.ApiGetAccountTxnsOutput;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class DaseAccountService extends DaseAccountServiceRaw implements AccountService {

  public DaseAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new DaseFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Integer limit = null;
    String before = null;
    if (params instanceof DaseFundingHistoryParams) {
      DaseFundingHistoryParams p = (DaseFundingHistoryParams) params;
      limit = p.getLimit();
      before = p.getBefore();
    }
    ApiGetAccountTxnsOutput resp = getAccountTransactions(limit, before);
    return DaseAdapters.adaptFundingRecords(resp == null ? null : resp.getTransactions());
  }

  public static class DaseFundingHistoryParams implements TradeHistoryParams {
    private Integer limit;
    private String before;

    public Integer getLimit() {
      return limit;
    }

    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    public String getBefore() {
      return before;
    }

    public void setBefore(String before) {
      this.before = before;
    }
  }

  public AccountInfo getAccountInfo() throws IOException {
    DaseUserProfile profile = getUserProfile();
    DaseBalancesResponse balances = getDaseBalances();
    return DaseAdapters.adaptAccountInfo(
        profile == null ? null : profile.getPortfolioId(), balances);
  }
}
