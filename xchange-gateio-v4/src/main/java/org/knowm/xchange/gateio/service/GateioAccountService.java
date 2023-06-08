package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.service.params.DefaultGateioWithdrawFundsParams;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class GateioAccountService extends GateioAccountServiceRaw implements AccountService {

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
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    Validate.isInstanceOf(DefaultGateioWithdrawFundsParams.class, params, "Not enough parameters");
    DefaultGateioWithdrawFundsParams p = (DefaultGateioWithdrawFundsParams) params;

    GateioWithdrawalRequest gateioWithdrawalRequest = GateioAdapters.toGateioWithdrawalRequest(p);

    try {
      GateioWithdrawalRecord gateioWithdrawalRecord = withdraw(gateioWithdrawalRequest);
      return gateioWithdrawalRecord.getId();
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


}
