package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.kraken.dto.account.LedgerType;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencies;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KrakenAccountService extends KrakenAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), KrakenAdapters.adaptWallet(getKrakenBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdraw(null, currency.toString(), address, amount).getRefid();
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
    KrakenDepositAddress[] depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin", false);
    return KrakenAdapters.adaptKrakenDepositAddress(depositAddresses);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new KrakenFundingHistoryParams(null, null, null, new Currency[]{Currency.BTC, Currency.USD});
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Date startTime = null;
    Date endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
      startTime = timeSpanParam.getStartTime();
      endTime = timeSpanParam.getEndTime();
    }

    Long offset = null;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    Currency[] currencies = null;
    if (params instanceof TradeHistoryParamCurrencies) {
      final TradeHistoryParamCurrencies currenciesParam = (TradeHistoryParamCurrencies) params;
      if (currenciesParam.getCurrencies() != null) {
        currencies = currenciesParam.getCurrencies();
      }
    }

    LedgerType ledgerType = null;
    if (params instanceof HistoryParamsFundingType) {
      final FundingRecord.Type type = ((HistoryParamsFundingType) params).getType();
      ledgerType = type == FundingRecord.Type.DEPOSIT ? LedgerType.DEPOSIT : type == FundingRecord.Type.WITHDRAWAL ? LedgerType.WITHDRAWAL : null;
    }
    return KrakenAdapters.adaptFundingHistory(getKrakenLedgerInfo(ledgerType, startTime, endTime, offset, currencies));
  }

  public static class KrakenFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamOffset, TradeHistoryParamCurrencies, HistoryParamsFundingType {

    private Long offset;
    private Currency[] currencies;
    private FundingRecord.Type type;

    public KrakenFundingHistoryParams(final Date startTime, final Date endTime, final Long offset, final Currency... currencies) {
      super(startTime, endTime);
      this.offset = offset;
      this.currencies = currencies;
    }

    @Override
    public void setOffset(final Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setCurrencies(Currency[] currencies) {
      this.currencies = currencies;
    }

    @Override
    public Currency[] getCurrencies() {
      return this.currencies;
    }

    @Override
    public FundingRecord.Type getType() {
      return type;
    }

    @Override
    public void setType(FundingRecord.Type type) {
      this.type = type;
    }
  }

}
