package org.knowm.xchange.gateio.service;

import org.apache.commons.lang3.Validate;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.*;
import org.knowm.xchange.gateio.service.params.GateioWithdrawFundsParams;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GateioAccountService extends GateioAccountServiceRaw implements AccountService {

  public GateioAccountService(GateioExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    try {
      List<GateioCurrencyBalance> spotBalances = getSpotBalances(null);

      List<Balance> balances =
          spotBalances.stream()
              .map(
                  balance ->
                      new Balance.Builder()
                          .currency(balance.getCurrency())
                          .available(balance.getAvailable())
                          .frozen(balance.getLocked())
                          .build())
              .collect(Collectors.toList());

      Wallet wallet =
          Wallet.Builder.from(balances)
              .id("spot")
              .features(EnumSet.of(Wallet.WalletFeature.TRADING))
              .build();

      return new AccountInfo(wallet);

    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    Validate.isInstanceOf(GateioWithdrawFundsParams.class, params);
    GateioWithdrawFundsParams p = (GateioWithdrawFundsParams) params;

    GateioWithdrawalRequest gateioWithdrawalRequest = GateioAdapters.toGateioWithdrawalRequest(p);

    try {
      GateioWithdrawalRecord gateioWithdrawalRecord = withdraw(gateioWithdrawalRequest);
      return gateioWithdrawalRecord.getId();
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      return getAccountBookRecords(params).stream()
          .map(GateioAdapters::toFundingRecords)
          .collect(Collectors.toList());
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  /**
   * set leverage for futures contract
   * leverage ≠ 0:
   * Isolated Margin Mode (Regardless of whether cross_leverage_limit is filled, this parameter will be ignored)
   * leverage = 0:
   * Cross Margin Mode (Use cross_leverage_limit to set the leverage multiple)
   *
   * @param instrument symbol to change leverage
   * @param leverage   leverage
   * @param args       cross_leverage
   * @return is successful
   */
  @Override
  public boolean setLeverage(Instrument instrument, int leverage, Object... args) throws IOException {
    if (instrument instanceof FuturesContract) {
      String settle = instrument.getCounter().getCurrencyCode().toLowerCase();
      String contract = GateioAdapters.toGateioInstrument(instrument);
      Integer cross_leverage;
      if (args != null && args.length > 0) {
        cross_leverage = (Integer) args[0];
        if (setLeverage(settle, contract, "0", String.valueOf(cross_leverage)).getCrossLeverageLimit().intValue() == cross_leverage)
          return true;
      } else {
        if (setLeverage(settle, contract, String.valueOf(leverage), null).getLeverage().intValue() == leverage)
          return true;
      }
      return false;
    } else throw new UnsupportedOperationException("Leverage is not supported for spot instruments");
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    try {
      Map<Instrument, Fee> fees = new HashMap<>();
      if (exchange.isFuturesEnabled()) {
        Map<String, GateioFuturesFee> futuresFees = getFuturesFee("usdt", null);
        futuresFees.forEach((contract, fee) -> {
          fees.put(
              GateioAdapters.fromGateioInstrument(contract, true),
              new Fee(fee.getMakerFee(), fee.getTakerFee()));
        });
      } else {
        GateioSpotFee spotFee = getSpotFee(null);
        exchange.getExchangeMetaData().getInstruments().keySet().forEach(instrument -> {
          fees.put(instrument,
              new Fee(spotFee.getMakerFee(), spotFee.getTakerFee()));
        });
      }
      return fees;
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

}
