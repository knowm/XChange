package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class OkCoinAccountService extends OkCoinAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfo(getUserInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    String okcoinCurrency = currency == Currency.BTC ? "btc_usd" : "btc_ltc";

    OKCoinWithdraw result = withdraw(null, okcoinCurrency, address, amount);

    if (result != null)
      return result.getWithdrawId();

    return "";
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
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new OkCoinFundingHistoryParams(null, null, null, CurrencyPair.BTC_CNY);
  }

  @Override
  public List<FundingRecord> getFundingHistory(
      TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    String symbol = null;
    if (params instanceof TradeHistoryParamCurrency && ((TradeHistoryParamCurrency) params).getCurrency() != null) {
      symbol = OkCoinAdapters.adaptSymbol(((TradeHistoryParamCurrency) params).getCurrency());
    }
    if (symbol == null) {
      if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
        symbol = OkCoinAdapters.adaptSymbol(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
      }
    }

    if (symbol == null) {
      throw new ExchangeException("Symbol must be supplied");
    }

    Integer pageLength = null;
    Integer pageNumber = null;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      if (pagingParams.getPageLength() != null) {
        pageLength = pagingParams.getPageLength();
        if (pageLength > 50) {
          pageLength = 50;
        }
      }
      pageNumber = pagingParams.getPageNumber() != null ? pagingParams.getPageNumber() : 1;
    }

    final OkCoinAccountRecords depositRecord = getAccountRecords(symbol, "0", String.valueOf(pageNumber), String.valueOf(pageLength));
    final OkCoinAccountRecords withdrawalRecord = getAccountRecords(symbol, "1", String.valueOf(pageNumber), String.valueOf(pageLength));
    final OkCoinAccountRecords[] okCoinAccountRecordsList = new OkCoinAccountRecords[]{depositRecord, withdrawalRecord};
    return OkCoinAdapters.adaptFundingHistory(okCoinAccountRecordsList);
  }

  public static class OkCoinFundingHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamCurrency, TradeHistoryParamCurrencyPair {

    private Currency currency;
    private CurrencyPair currencyPair;

    public OkCoinFundingHistoryParams(final Integer pageNumber, final Integer pageLength, final Currency currency, final CurrencyPair currencyPair) {
      super(pageLength, pageNumber);
      this.currency = currency;
      this.currencyPair = currencyPair;
    }

    @Override
    public void setCurrency(Currency currency) {
      this.currency = currency;
    }

    @Override
    public Currency getCurrency() {
      return this.currency;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return this.currencyPair;
    }
  }

}
