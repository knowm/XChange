package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
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
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    boolean useIntl =
        this.exchange
            .getExchangeSpecification()
            .getExchangeSpecificParametersItem("Use_Intl")
            .equals(true);
    String currencySymbol =
        OkCoinAdapters.adaptSymbol(
            new CurrencyPair(currency, useIntl ? Currency.USD : Currency.CNY));

    String fee;
    switch (currency.getCurrencyCode()) {
      case "BTC":
        fee = "0.0015";
        break;
      case "LTC":
      case "ETC":
      case "BTG":
      case "ZEC":
        fee = "0.001";
        break;
      case "ETH":
      case "XLM":
      case "QTUM":
        fee = "0.01";
        break;
      case "OMG":
        fee = "0.1";
        break;
      case "BCH":
        fee = "0.0001";
        break;
      case "XRP":
        fee = "0.15";
        break;
      case "DASH":
        fee = "0.002";
        break;
      case "NEO":
        fee = "0";
        break;
      case "AVT":
        fee = "1";
        break;
      case "EOS":
        fee = "1.5";
        break;
      case "ELF":
        fee = "3";
        break;
      case "XEM":
        fee = "4";
        break;
      case "FUN":
        fee = "40";
        break;
      case "MANA":
      case "USDT":
      case "RCN":
        fee = "20";
        break;
      case "SNT":
        fee = "50";
        break;
      default:
        throw new IllegalArgumentException("Unsupported withdraw currency " + currency);
    }

    // Defualt withdraw target is external address. Use withdraw function in OkCoinAccountServiceRaw
    // for internal withdraw
    OKCoinWithdraw result = withdraw(currencySymbol, address, amount, "address", fee);

    if (result != null) return result.getWithdrawId();

    return "";
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
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
    return new OkCoinFundingHistoryParams(null, null, null, null);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String symbol = null;
    if (params instanceof TradeHistoryParamCurrency
        && ((TradeHistoryParamCurrency) params).getCurrency() != null) {
      symbol = OkCoinAdapters.adaptSymbol(((TradeHistoryParamCurrency) params).getCurrency());
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
