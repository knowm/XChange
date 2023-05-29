package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.MoneroWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class GateioAccountService extends GateioAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountService(GateioExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    try {
      List<GateioCurrencyBalance> spotBalances = getSpotBalances(null);

      List<Balance> balances = spotBalances.stream()
          .map(balance -> Balance.builder()
              .currency(Currency.getInstance(balance.getCurrency()))
              .available(balance.getAvailable())
              .frozen(balance.getLocked())
              .build())
          .collect(Collectors.toList());

      Wallet wallet = Wallet.Builder
          .from(balances)
          .id("spot")
          .build();

      return new AccountInfo(wallet);

    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }


  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdraw(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams xrpParams = (RippleWithdrawFundsParams) params;
      return withdraw(
          xrpParams.getCurrency(),
          xrpParams.getAmount(),
          xrpParams.getAddress(),
          xrpParams.getTag());
    } else if (params instanceof MoneroWithdrawFundsParams) {
      MoneroWithdrawFundsParams xmrParams = (MoneroWithdrawFundsParams) params;
      return withdraw(
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
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return super.getGateioDepositAddress(currency).getBaseAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Date start = null;
    Date end = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      start = timeSpan.getStartTime();
      end = timeSpan.getEndTime();
    }
    GateioDepositsWithdrawals depositsWithdrawals = getDepositsWithdrawals(start, end);
    return GateioAdapters.adaptDepositsWithdrawals(depositsWithdrawals);
  }
}
