package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;

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
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    
    return this.withdraw(
    		OkCoinAdapters.adaptSymbol(currency), 
    		address, 
    		amount, 
    		"address").getWithdrawId();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    
	if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams defaultParams = (RippleWithdrawFundsParams) params;
      return withdraw(
        OkCoinAdapters.adaptSymbol(defaultParams.getCurrency()),
        defaultParams.getAddress(),
        defaultParams.getAmount(),
        "address",
        null,
        defaultParams.getTag()).getWithdrawId();
      
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    JSONArray result = this.accountAPIService.getDepositAddress(OkCoinAdapters.adaptSymbol(currency));
    JSONObject object = result.getJSONObject(0);
    String address = object.getString("address");
    return address;
  }
  
  public BigDecimal getWithdrawalFee(Currency currency) {
	  return this.accountAPIService
			  .getWithdrawFee(OkCoinAdapters.adaptSymbol(currency))
			  .get(0)
			  .getMin_fee();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new OkCoinFundingHistoryParams(null, null, null, null);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String symbol = null;
    if (params instanceof TradeHistoryParamCurrency
        && ((TradeHistoryParamCurrency) params).getCurrency() != null) {

      symbol =
          OkCoinAdapters.adaptCurrencyToAccountRecordPair(
              ((TradeHistoryParamCurrency) params).getCurrency());
    }
    if (symbol == null) {
      throw new ExchangeException("Symbol must be supplied");
    }

    Integer pageLength = 50;
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

    FundingRecord.Type type = null;
    if (params instanceof HistoryParamsFundingType) {
      type = ((HistoryParamsFundingType) params).getType();
    }
    List<FundingRecord> result = new ArrayList<>();
    if (type == null || type == Type.DEPOSIT) {
      final OkCoinAccountRecords depositRecord =
          getAccountRecords(symbol, "0", String.valueOf(pageNumber), String.valueOf(pageLength));
      result.addAll(OkCoinAdapters.adaptFundingHistory(depositRecord, Type.DEPOSIT));
    }
    if (type == null || type == Type.WITHDRAWAL) {
      final OkCoinAccountRecords withdrawalRecord =
          getAccountRecords(symbol, "1", String.valueOf(pageNumber), String.valueOf(pageLength));
      result.addAll(OkCoinAdapters.adaptFundingHistory(withdrawalRecord, Type.WITHDRAWAL));
    }
    return result;
  }

  public static class OkCoinFundingHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamCurrency, HistoryParamsFundingType {

    private Currency currency;
    private FundingRecord.Type type;

    public OkCoinFundingHistoryParams(
        final Integer pageNumber,
        final Integer pageLength,
        final Currency currency,
        FundingRecord.Type type) {
      super(pageLength, pageNumber);
      this.currency = currency;
      this.type = type;
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
    public Type getType() {
      return type;
    }

    @Override
    public void setType(Type type) {
      this.type = type;
    }
  }
}
