package org.knowm.xchange.hitbtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.TransactionResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    HitbtcBalance[] walletRaw = getWalletRaw();

    return new AccountInfo(HitbtcAdapters.adaptWallet(walletRaw));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFundsRaw(currency, amount, address);
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

    return getDepositAddress(currency.toString());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    long offset = 0;
    long limit = 1000;
    String direction = "asc";

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    if (params instanceof TradeHistoryParamsSorted) {
      if (((TradeHistoryParamsSorted) params).getOrder().equals(TradeHistoryParamsSorted.Order.desc))
        direction = "desc";
    }

    List<TransactionResponse> transactions = transactions(offset, limit, direction);

    List<FundingRecord> records = new ArrayList<>();
    for (TransactionResponse transaction : transactions) {
      records.add(HitbtcAdapters.adapt(transaction));
    }
    return records;
  }

  public static class HitbtcFundingParams implements TradeHistoryParamLimit, TradeHistoryParamOffset, TradeHistoryParamsSorted {

    private Integer limit = 1000;
    private Long offset = 0L;
    private Order order = Order.asc;

    public HitbtcFundingParams() {
    }

    public HitbtcFundingParams(Integer limit, Long offset, Order order) {
      this.limit = limit;
      this.offset = offset;
      this.order = order;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public Order getOrder() {
      return order;
    }

    @Override
    public void setOrder(Order order) {
      this.order = order;
    }
  }
}
