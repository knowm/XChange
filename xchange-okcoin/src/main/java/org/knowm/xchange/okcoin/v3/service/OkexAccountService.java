package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.okcoin.OkexAdaptersV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRequest;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {

  public OkexAccountService(OkexExchangeV3 exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<OkexFundingAccountRecord> funding = super.fundingAccountInformation();
    Collection<Balance> fundingBalances =
        funding.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList());
    List<OkexSpotAccountRecord> spotTradingAccount = super.spotTradingAccount();
    Collection<Balance> tradingBalances =
        spotTradingAccount.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList());

    /*
     * commented out, since using this method we are running into [30014] Too Many Requests
    FuturesAccountsResponse futuresAccounts = super.getFuturesAccounts();
    Collection<Balance> futuresBalances =
        futuresAccounts.getInfo().getAccounts().entrySet().stream()
            .map(e -> OkexAdaptersV3.convert(e.getKey(), e.getValue()))
            .collect(Collectors.toList());

    List<SwapAccountInfo> swapAccounts = super.getSwapAccounts();
    Collection<Balance> swapBalances =
        swapAccounts.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList());*/
    return new AccountInfo(
        Wallet.Builder.from(fundingBalances)
            .id("Funding")
            .features(Stream.of(Wallet.WalletFeature.FUNDING).collect(Collectors.toSet()))
            .build(),
        Wallet.Builder.from(tradingBalances)
            .id("Trading")
            .features(Stream.of(Wallet.WalletFeature.TRADING).collect(Collectors.toSet()))
            .build());
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
      return withdrawFunds0(p.currency, p.amount, p.address, p.commission);
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds0(currency, amount, address, null);
  }

  private String withdrawFunds0(
      Currency currency, BigDecimal amount, String address, BigDecimal fee) throws IOException {
    if (fee == null) {
      fee = getStaticFee(currency.getCurrencyCode());
    }
    OkexWithdrawalRequest req =
        OkexWithdrawalRequest.builder()
            .currency(currency.getCurrencyCode())
            .amount(amount)
            .destination("4") // 4:others
            .fee(fee)
            .toAddress(address)
            .tradePwd(tradepwd)
            .build();
    OkexWithdrawalResponse withdrawal = withdrawal(req);
    return withdrawal.getWithdrawalId();
  }

  public BigDecimal getStaticFee(String currency) {
    CurrencyMetaData cmd =
        this.exchange.getExchangeMetaData().getCurrencies().get(Currency.getInstance(currency));
    if (cmd == null || cmd.getWithdrawalFee() == null) {
      throw new IllegalArgumentException("Unsupported withdraw currency " + currency);
    }
    return cmd.getWithdrawalFee();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    boolean withdrawals = true, deposits = true;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType p = (HistoryParamsFundingType) params;
      withdrawals = p.getType() == null || p.getType() == Type.WITHDRAWAL;
      deposits = p.getType() == null || p.getType() == Type.DEPOSIT;
    }

    List<FundingRecord> result = new ArrayList<>();
    if (withdrawals) {
      result.addAll(
          recentWithdrawalHistory().stream()
              .map(OkexAdaptersV3::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    if (deposits) {
      result.addAll(
          recentDepositHistory().stream()
              .map(OkexAdaptersV3::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    Collections.sort(result, (r1, r2) -> r1.getDate().compareTo(r2.getDate()));
    return result;
  }
}
