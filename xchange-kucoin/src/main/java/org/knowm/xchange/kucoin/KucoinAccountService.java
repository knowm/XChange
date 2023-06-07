package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.kucoin.dto.KucoinException;
import org.knowm.xchange.kucoin.dto.request.ApplyWithdrawApiRequest;
import org.knowm.xchange.kucoin.dto.request.CreateDepositAddressApiRequest;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;
import org.knowm.xchange.kucoin.dto.response.DepositAddressResponse;
import org.knowm.xchange.kucoin.service.params.KucoinWithdrawFundsParams;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  KucoinAccountService(KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    try {

      List<AccountBalancesResponse> accounts = getKucoinAccounts(null, null);

      // split balances by account type: main, trade, margin
      Map<String, List<Balance>> balancesByAccountType = accounts.stream()
          .collect(Collectors.groupingBy(
              AccountBalancesResponse::getType,
              Collectors.mapping(KucoinAdapters::adaptBalance, toList()))
          );

      // convert balances to wallets
      List<Wallet> wallets = new ArrayList<>();
      balancesByAccountType.forEach((accountType, balances) -> {
        Wallet wallet = Wallet.Builder
              .from(balances)
              .id(accountType)
              .name(accountType)
              .build();
        wallets.add(wallet);
      });

      return new AccountInfo(wallets);
    } catch (KucoinException e) {
      throw KucoinErrorAdapter.adapt(e);
    }
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new KucoinTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      Currency c = ((TradeHistoryParamCurrency) params).getCurrency();
      currency = c == null ? null : c.getCurrencyCode();
    }
    boolean withdrawals = true, deposits = true;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType p = (HistoryParamsFundingType) params;
      withdrawals = p.getType() == null || p.getType() == Type.WITHDRAWAL;
      deposits = p.getType() == null || p.getType() == Type.DEPOSIT;
    }

    Long startAt = null, endAt = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
      startAt = p.getStartTime() == null ? null : p.getStartTime().getTime();
      endAt = p.getEndTime() == null ? null : p.getEndTime().getTime();
    }
    List<FundingRecord> result = new ArrayList<>();
    if (withdrawals) {
      result.addAll(
          getWithdrawalsList(currency, null, startAt, endAt, null, null).getItems().stream()
              .map(KucoinAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    if (deposits) {
      result.addAll(
          getDepositList(currency, null, startAt, endAt, null, null).getItems().stream()
              .map(KucoinAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    return result;
  }


  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    Validate.isInstanceOf(KucoinWithdrawFundsParams.class, params);

    KucoinWithdrawFundsParams withdrawFundsParams = (KucoinWithdrawFundsParams) params;

    ApplyWithdrawApiRequest request = ApplyWithdrawApiRequest.builder()
        .currency(withdrawFundsParams.getCurrency().getCurrencyCode())
        .address(withdrawFundsParams.getAddress())
        .amount(withdrawFundsParams.getAmount())
        .memo(withdrawFundsParams.getAddressTag())
        .remark(withdrawFundsParams.getRemark())
        .chain(withdrawFundsParams.getChain())
        .build();

    try {
      return applyWithdraw(request).getWithdrawalId();
    } catch (KucoinException e) {
      throw KucoinErrorAdapter.adapt(e);
    }


  }


  /**
   * @param args Chain name
   */
  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String... args)
      throws IOException {
    Validate.isTrue(args.length == 1, "Chain name is missing");
    String chain = args[0];

    try {

      DepositAddressResponse response = getDepositAddress(currency.getCurrencyCode(), chain);

      return AddressWithTag.builder()
          .address(response.getAddress())
          .addressTag(response.getMemo())
          .build();

    } catch (KucoinException e) {
      throw KucoinErrorAdapter.adapt(e);
    }

  }


  public List<DepositAddressResponse> requestDepositAddressData(Currency currency)
      throws IOException {
    try {

      return getDepositAddresses(currency.getCurrencyCode());

    } catch (KucoinException e) {
      throw KucoinErrorAdapter.adapt(e);
    }

  }


  public DepositAddressResponse createDepositAddress(Currency currency, String chain) throws IOException {
    try {

      CreateDepositAddressApiRequest request = CreateDepositAddressApiRequest.builder()
          .currency(currency.getCurrencyCode().toUpperCase(Locale.ROOT))
          .chain(chain)
          .build();

      return createDepositAddress(request);

    } catch (KucoinException e) {
      throw KucoinErrorAdapter.adapt(e);
    }

  }

}
