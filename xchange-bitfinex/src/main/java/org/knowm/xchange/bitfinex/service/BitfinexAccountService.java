package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.bitfinex.BitfinexErrorAdapter;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexFundingHistoryParams;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.MoneroWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

public class BitfinexAccountService extends BitfinexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountService(
      BitfinexExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      return BitfinexAdapters.toAccountInfo(getWallets());
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
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
    return BitfinexFundingHistoryParams.builder().build();
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
          limit = tradeHistoryParamLimit.getLimit();
        }
      }

      // if filtered by category, call ledgers endpoint
      if (params instanceof HistoryParamsFundingType) {
        Type fundingRecordType = ((HistoryParamsFundingType) params).getType();
        var category = BitfinexAdapters.toCategory(fundingRecordType);

        if (category != null) {
          return getLedgerEntries(currency, startTime, endTime, limit, category).stream()
              .map(BitfinexAdapters::toFundingRecord)
              .filter(fundingRecord -> fundingRecord.getType() == fundingRecordType)
              .collect(Collectors.toList());
        }
      }

      return BitfinexAdapters.adaptFundingHistory(
          getMovementHistory(currency, startTime, endTime, limit));
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    try {
      List<Instrument> allCurrencyPairs = exchange.getExchangeInstruments();
      return BitfinexAdapters.adaptDynamicTradingFees(
          getBitfinexDynamicTradingFees(), allCurrencyPairs);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }
}
