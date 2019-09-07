package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitmexAccountService extends BitmexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexAccountService(BitmexExchange exchange) {

    super(exchange);
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new DefaultTradeHistoryParamCurrency();
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Set<WalletFeature> walletFeatures = new HashSet<>();
    walletFeatures.add(WalletFeature.MARGIN_TRADING);
    walletFeatures.add(WalletFeature.FUNDING);

    BitmexAccount account = super.getBitmexAccountInfo();
    BitmexMarginAccount bitmexMarginAccount = getBitmexMarginAccountStatus();
    BigDecimal amount = bitmexMarginAccount.getAmount().divide(BigDecimal.valueOf(100_000_000L));

    List<Balance> balances = new ArrayList<>();
    balances.add(new Balance(Currency.BTC, amount));

    Wallet wallet = new Wallet(balances,walletFeatures,BigDecimal.valueOf(100),bitmexMarginAccount.getMarginLeverage());
    AccountInfo accountInfo = new AccountInfo(account.getUsername(), wallet);
    return accountInfo;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    String currencyCode = currency.getCurrencyCode();

    // bitmex seems to use a lowercase 't' in XBT
    // can test this here - https://testnet.bitmex.com/api/explorer/#!/User/User_getDepositAddress
    // uppercase 'T' will return 'Unknown currency code'
    if (currencyCode.equals("XBT")) {
      currencyCode = "XBt";
    }
    return requestDepositAddress(currencyCode);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {

    Currency currency = null;

    if (params instanceof TradeHistoryParamCurrency) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency();

      if (currency.getCurrencyCode().equals("BTC") || currency.getCurrencyCode().equals("XBT")) {
        currency = new Currency("XBt");
      }
    } else {
      throw new ExchangeException("Currency must be supplied");
    }

    return getBitmexWalletHistory(currency).stream()
        .filter(
            w ->
                w.getTransactStatus().equals("Completed")
                    && (w.getTransactType().equals("Deposit")
                        || w.getTransactType().equals("Withdrawal")))
        .map(w -> BitmexAdapters.adaptFundingRecord(w))
        .collect(Collectors.toList());
  }
}
