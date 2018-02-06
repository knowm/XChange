package org.knowm.xchange.bitbay.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.BitbayAdapters;
import org.knowm.xchange.bitbay.service.account.params.BitbayWithdrawFundsSwiftParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * @author Z. Dolezal
 */
public class BitbayAccountService extends BitbayAccountServiceRaw implements AccountService {

  public BitbayAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitbayAdapters.adaptAccountInfo(exchange.getExchangeSpecification().getUserName(), getBitbayAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount,
      String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if ( params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      transfer(defaultParams.currency, defaultParams.amount, defaultParams.address);
      return "Success";
    }
    else if ( params instanceof BitbayWithdrawFundsSwiftParams ) {
      BitbayWithdrawFundsSwiftParams bicParams = (BitbayWithdrawFundsSwiftParams) params;
      withdraw(bicParams.getCurrency(), bicParams.getAmount(), bicParams.getAccount(), bicParams.isExpress(), bicParams.getBic());
      return "Success";
    }

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency,
      String... args) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Currency currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency tradeHistoryParamCurrency = (TradeHistoryParamCurrency) params;
      currency = tradeHistoryParamCurrency.getCurrency();
    }

    Integer limit = 1000;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    return history(currency, limit);
  }

  public static class BitbayFundingHistory implements TradeHistoryParamCurrency, TradeHistoryParamLimit {

    private Currency currency;
    private Integer limit;

    public BitbayFundingHistory(Currency currency, Integer limit) {
      this.currency = currency;
      this.limit = limit;
    }

    public BitbayFundingHistory() {
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
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }
  }
}
