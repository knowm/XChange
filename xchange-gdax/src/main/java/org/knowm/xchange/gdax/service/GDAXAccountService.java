package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
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
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return new AccountInfo(GDAXAdapters.adaptAccountInfo(getCoinbaseExAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      GDAXWithdrawCryptoResponse response = withdrawCrypto(defaultParams.address, defaultParams.amount, defaultParams.currency);
      return response.id;
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  public String moveFunds(Currency currency, String address, BigDecimal amount) throws IOException {
    GDAXAccount[] accounts = getCoinbaseExAccountInfo();
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

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    List<FundingRecord> fundingHistory = new ArrayList<>();

    for (GDAXAccount gdaxAccount : getCoinbaseExAccountInfo()) {
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
        boolean isTransfer = map.get("type").toString().equals("transfer");
        if (!isTransfer)
          continue;

        Map details = (Map) map.get("details");

        String transferType = details.get("transfer_type").toString();

        FundingRecord.Type type;
        if (transferType.equals("deposit"))
          type = FundingRecord.Type.DEPOSIT;
        else if (transferType.equals("withdraw"))
          type = FundingRecord.Type.WITHDRAWAL;
        else
          continue;

        fundingHistory.add(new FundingRecord(
            null,
            DateUtils.fromISO8601DateString(map.get("created_at").toString()),
            currency,
            new BigDecimal(map.get("amount").toString()),
            details.get("transfer_id").toString(),
            null,
            type,
            FundingRecord.Status.COMPLETE,
            new BigDecimal(map.get("balance").toString()),
            null,
            null
        ));
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
