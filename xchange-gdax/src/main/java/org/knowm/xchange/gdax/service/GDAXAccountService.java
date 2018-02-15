package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccount;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccountAddress;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXTradeHistoryParams;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GDAXAccountService extends GDAXAccountServiceRaw implements AccountService {

  public GDAXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(GDAXAdapters.adaptAccountInfo(getGDAXAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      GDAXWithdrawCryptoResponse response = withdrawCrypto(defaultParams.address, defaultParams.amount, defaultParams.currency);
      return response.id;
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  public String moveFunds(Currency currency, String address, BigDecimal amount) throws IOException {
    GDAXAccount[] accounts = getGDAXAccountInfo();
    String accountId = null;
    for (GDAXAccount account : accounts) {
      if (currency.getCurrencyCode().equals(account.getCurrency())) {
        accountId = account.getId();
      }
    }

    if (accountId == null) {
      throw new ExchangeException("Cannot determine account id for currency " + currency.getCurrencyCode());
    }

    GDAXSendMoneyResponse response = sendMoney(accountId, address, amount, currency);
    if (response.getData() != null) {
      return response.getData().getId();
    }

    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    GDAXCoinbaseAccount[] coinbaseAccounts = getCoinbaseAccounts();
    GDAXCoinbaseAccount depositAccount = null;

    for (GDAXCoinbaseAccount account : coinbaseAccounts) {
      Currency accountCurrency = Currency.getInstance(account.getCurrency());
      if (account.isActive() && account.getType().equals("wallet") && accountCurrency.equals(currency)) {
        depositAccount = account;
        break;
      }
    }

    GDAXCoinbaseAccountAddress depositAddress = getCoinbaseAccountAddress(depositAccount.getId());
    return depositAddress.getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new GDAXTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    List<FundingRecord> fundingHistory = new ArrayList<>();

    for (GDAXAccount gdaxAccount : getGDAXAccountInfo()) {
      String accountId = gdaxAccount.getId();

      Currency currency = Currency.getInstance(gdaxAccount.getCurrency());

      Map<Integer, Map> allForAccount = new HashMap<>();

      Integer lastId = null;
      while (true) {
        List<Map> ledger = ledger(accountId, lastId);
        if (ledger.isEmpty())
          break;

        for (Map map : ledger) {
          lastId = Integer.valueOf(map.get("id").toString());

          if (allForAccount.containsKey(lastId))
            throw new IllegalStateException("Should not happen");

          allForAccount.put(lastId, map);
        }
      }

      for (Map map : allForAccount.values()) {
        try {
          boolean isTransfer = map.get("type").toString().equals("transfer");
          if (!isTransfer)
            continue;

          Map details = (Map) map.get("details");

          FundingRecord.Type type;

          Object source = details.get("source");
          if (source != null && source.toString().equals("fork"))
            type = FundingRecord.Type.DEPOSIT;
          else if (details.get("transfer_type").toString().equals("deposit"))
            type = FundingRecord.Type.DEPOSIT;
          else if (details.get("transfer_type").toString().equals("withdraw"))
            type = FundingRecord.Type.WITHDRAWAL;
          else
            continue;

          Object transferId = details.get("transfer_id");

          fundingHistory.add(new FundingRecord(
              null,
              DateUtils.fromISO8601DateString(map.get("created_at").toString()),
              currency,
              new BigDecimal(map.get("amount").toString()),
              transferId == null ? null : transferId.toString(),
              null,
              type,
              FundingRecord.Status.COMPLETE,
              new BigDecimal(map.get("balance").toString()),
              null,
              null
          ));
        } catch (Exception e) {
          throw new IllegalStateException("Failed to parse: " + map, e);
        }
      }
    }

    return fundingHistory;
  }

  public static class GDAXMoveFundsParams implements WithdrawFundsParams {
    public final Currency currency;
    public final BigDecimal amount;
    public final String address;

    public GDAXMoveFundsParams(Currency currency, BigDecimal amount, String address) {
      this.currency = currency;
      this.amount = amount;
      this.address = address;
    }
  }
}
