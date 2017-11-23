package org.knowm.xchange.bitfinex.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BitfinexAccountService extends BitfinexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BitfinexAdapters.adaptWallet(getBitfinexAccountInfo()));
  }

  /**
   * Withdrawal support
   *
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    //determine withdrawal type
    String type = BitfinexUtils.convertToBitfinexWithdrawalType(currency.toString());
    //Bitfinex withdeawal can be from different type of wallets    *
    // we have to use one of these for now: Exchange -
    //to be able to withdraw instantly after trading for example
    //The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
    String walletSelected = "exchange";
    //We have to convert XChange currencies to Bitfinex currencies: can be “bitcoin”, “litecoin” or “ether” or “tether” or “wire”.
    return withdraw(type, walletSelected, amount, address);
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
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    final BitfinexDepositAddressResponse response = super.requestDepositAddressRaw(currency.getCurrencyCode());
    return response.getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitfinexFundingHistoryParams(null, null, null, Currency.BTC);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String currency = null;
    if (params instanceof TradeHistoryParamCurrency && ((TradeHistoryParamCurrency) params).getCurrency() != null) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency().getCurrencyCode();
    } else {
      throw new ExchangeException("Currency must be supplied");
    }

    Date startTime = null;
    Date endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }

    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
      limit = limitParams.getLimit();
    }

    return BitfinexAdapters.adaptFundingHistory(getDepositWithdrawalHistory(currency, null, startTime, endTime, limit));
  }

  public static class BitfinexFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamCurrency, TradeHistoryParamLimit {

    private Integer limit;
    private Currency currency;

    public BitfinexFundingHistoryParams(final Date startTime, final Date endTime, final Integer limit, final Currency currency) {

      super(startTime, endTime);

      this.limit = limit;
      this.currency = currency;
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
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Integer getLimit() {
      return this.limit;
    }
  }
}
