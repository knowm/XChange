package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.BitfinexErrorAdapter;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.utils.DateUtils;

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

    return new AccountInfo(BitfinexAdapters.adaptWallets(getBitfinexAccountInfo()));
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
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    try {
      // determine withdrawal type
      String type = BitfinexUtils.convertToBitfinexWithdrawalType(currency.toString());
      // Bitfinex withdeawal can be from different type of wallets    *
      // we have to use one of these for now: Exchange -
      // to be able to withdraw instantly after trading for example
      // The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
      String walletSelected = "exchange";
      // We have to convert XChange currencies to Bitfinex currencies: can be “bitcoin”, “litecoin”
      // or
      // “ether” or “tether” or “wire”.
      return withdraw(type, walletSelected, amount, address);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  /**
   * Used for XRP withdrawals
   *
   * @param currency
   * @param amount
   * @param address
   * @param tagOrPaymentId
   * @return
   * @throws IOException
   */
  public String withdrawFunds(
      Currency currency, BigDecimal amount, String address, String tagOrPaymentId)
      throws IOException {
    try {
      // determine withdrawal type
      String type = BitfinexUtils.convertToBitfinexWithdrawalType(currency.toString());
      // Bitfinex withdeawal can be from different type of wallets    *
      // we have to use one of these for now: Exchange -
      // to be able to withdraw instantly after trading for example
      // The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
      String walletSelected = "exchange";
      // We have to convert XChange currencies to Bitfinex currencies: can be “bitcoin”, “litecoin”
      // or
      // “ether” or “tether” or “wire”.
      return withdraw(type, walletSelected, amount, address, tagOrPaymentId);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
      if (params instanceof RippleWithdrawFundsParams) {
        RippleWithdrawFundsParams xrpParams = (RippleWithdrawFundsParams) params;
        return withdrawFunds(
            xrpParams.getCurrency(),
            xrpParams.getAmount(),
            xrpParams.getAddress(),
            xrpParams.getTag());
      } else if (params instanceof MoneroWithdrawFundsParams) {
        MoneroWithdrawFundsParams xmrParams = (MoneroWithdrawFundsParams) params;
        return withdrawFunds(
            xmrParams.getCurrency(),
            xmrParams.getAmount(),
            xmrParams.getAddress(),
            xmrParams.getPaymentId());
      } else if (params instanceof DefaultWithdrawFundsParams) {
        DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
        return withdrawFunds(
            defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
      }

      throw new IllegalStateException("Don't know how to withdraw: " + params);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    try {
      final BitfinexDepositAddressResponse response =
          super.requestDepositAddressRaw(currency.getCurrencyCode());
      return response.getAddress();
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitfinexFundingHistoryParams(null, null, null, null);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      String currency = null;
      Long startTime = null;
      Long endTime = null;
      Integer limit = null;

      if (params instanceof TradeHistoryParamCurrencyPair
          && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
        currency =
            BitfinexAdapters.adaptCurrencyPair(
                ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
      }

      if (params instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan paramsTimeSpan = (TradeHistoryParamsTimeSpan) params;
        startTime = DateUtils.toMillisNullSafe(paramsTimeSpan.getStartTime());
        endTime = DateUtils.toMillisNullSafe(paramsTimeSpan.getEndTime());
      }

      if (params instanceof TradeHistoryParamLimit) {
        TradeHistoryParamLimit tradeHistoryParamLimit = (TradeHistoryParamLimit) params;
        if (tradeHistoryParamLimit.getLimit() != null) {
          limit = Integer.valueOf(tradeHistoryParamLimit.getLimit());
        }
      }

      return BitfinexAdapters.adaptFundingHistory(
          getMovementHistory(currency, startTime, endTime, limit));
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    try {
      List<CurrencyPair> allCurrencyPairs = exchange.getExchangeSymbols();
      return BitfinexAdapters.adaptDynamicTradingFees(
          getBitfinexDynamicTradingFees(), allCurrencyPairs);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  public static class BitfinexFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamCurrency, TradeHistoryParamLimit {

    private Integer limit;
    private Currency currency;

    public BitfinexFundingHistoryParams(
        final Date startTime, final Date endTime, final Integer limit, final Currency currency) {

      super(startTime, endTime);

      this.limit = limit;
      this.currency = currency;
    }

    @Override
    public Currency getCurrency() {
      return this.currency;
    }

    @Override
    public void setCurrency(Currency currency) {
      this.currency = currency;
    }

    @Override
    public Integer getLimit() {
      return this.limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }
  }
}
