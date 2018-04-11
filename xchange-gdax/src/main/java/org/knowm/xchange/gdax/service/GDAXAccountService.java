package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.GdaxTransfer;
import org.knowm.xchange.gdax.dto.GdaxTransfers;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccount;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccountAddress;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXTradeHistoryParams;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class GDAXAccountService extends GDAXAccountServiceRaw implements AccountService {

  public GDAXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(GDAXAdapters.adaptAccountInfo(getGDAXAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      GDAXWithdrawCryptoResponse response =
          withdrawCrypto(
              defaultParams.getAddress(), defaultParams.getAmount(), defaultParams.getCurrency());
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
      throw new ExchangeException(
          "Cannot determine account id for currency " + currency.getCurrencyCode());
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
      if (account.isActive()
          && account.getType().equals("wallet")
          && accountCurrency.equals(currency)) {
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
  /*
   * Warning - this method makes several API calls. The reason is that the paging functionality
   * isn't implemented properly yet.
   *
   * <p>It honours TradeHistoryParamCurrency for filtering to a single ccy.
   */
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    int maxPageSize = 100;

    List<FundingRecord> fundingHistory = new ArrayList<>();

    for (GDAXAccount gdaxAccount : getGDAXAccountInfo()) {
      Currency currency = Currency.getInstance(gdaxAccount.getCurrency());

      if (params instanceof TradeHistoryParamCurrency) {
        Currency desiredCurrency = ((TradeHistoryParamCurrency) params).getCurrency();
        if (!desiredCurrency.equals(currency)) continue;
      }

      String accountId = gdaxAccount.getId();
      String profileId = gdaxAccount.getProfile_id();
      String createdAt = null; // use to get next page

      while (true) {
        GdaxTransfers transfers = transfers(accountId, profileId, maxPageSize, createdAt);
        if (transfers.isEmpty()) break;

        for (GdaxTransfer gdaxTransfer : transfers) {
          fundingHistory.add(GDAXAdapters.adaptFundingRecord(currency, gdaxTransfer));
        }

        createdAt = transfers.getHeader("cb-after");
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
