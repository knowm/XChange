package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexErrorAdapter;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDepositsWithdrawalsResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Zach Holmes */
public class PoloniexAccountService extends PoloniexAccountServiceRaw implements AccountService {

  private static final String TRADING_WALLET_ID = "trading";

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      Wallet build =
          Wallet.Builder.from(PoloniexAdapters.adaptPoloniexBalances(getExchangeWallet()))
              .id(TRADING_WALLET_ID)
              .features(new HashSet<>(Collections.singletonList(Wallet.WalletFeature.TRADING)))
              .build();
      return new AccountInfo(build);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    // does not support XRP withdrawals, use RippleWithdrawFundsParams instead
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
      if (params instanceof RippleWithdrawFundsParams) {
        RippleWithdrawFundsParams xrpParams = (RippleWithdrawFundsParams) params;

        return withdraw(
            xrpParams.getCurrency(),
            xrpParams.getAmount(),
            xrpParams.getAddress(),
            xrpParams.getTag());
      }

      if (params instanceof DefaultWithdrawFundsParams) {
        DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;

        return withdraw(
            defaultParams.getCurrency(),
            defaultParams.getAmount(),
            defaultParams.getAddress(),
            null);
      }

      throw new IllegalStateException("Don't know how to withdraw: " + params);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    try {
      return getDepositAddress(currency.toString());
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    final DefaultTradeHistoryParamsTimeSpan params = new DefaultTradeHistoryParamsTimeSpan();
    params.setStartTime(
        new Date(System.currentTimeMillis() - 366L * 24 * 60 * 60 * 1000)); // just over one year
    params.setEndTime(new Date());
    return params;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      Date start = null;
      Date end = null;
      if (params instanceof TradeHistoryParamsTimeSpan) {
        start = ((TradeHistoryParamsTimeSpan) params).getStartTime();
        end = ((TradeHistoryParamsTimeSpan) params).getEndTime();
      }
      final PoloniexDepositsWithdrawalsResponse poloFundings =
          returnDepositsWithdrawals(start, end);
      return PoloniexAdapters.adaptFundingRecords(poloFundings);
    } catch (PoloniexException e) {
      throw PoloniexErrorAdapter.adapt(e);
    }
  }
}
