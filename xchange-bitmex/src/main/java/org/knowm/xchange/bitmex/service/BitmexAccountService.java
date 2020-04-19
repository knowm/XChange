package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.bitmex.BitmexAdapters.adaptCurrency;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
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
    return new BitmexTradeHistroyParams();
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BitmexAccount account = super.getBitmexAccountInfo();
    BitmexMarginAccount bitmexMarginAccount = getBitmexMarginAccountStatus();
    BigDecimal amount = bitmexMarginAccount.getAmount().divide(BigDecimal.valueOf(100_000_000L));

    List<Balance> balances = new ArrayList<>();
    balances.add(new Balance(Currency.BTC, amount));

    Wallet wallet =
        Wallet.Builder.from(balances)
            .id("margin")
            .features(EnumSet.of(Wallet.WalletFeature.MARGIN_TRADING, Wallet.WalletFeature.FUNDING))
            .maxLeverage(BigDecimal.valueOf(100))
            .currentLeverage(bitmexMarginAccount.getMarginLeverage())
            .build();

    return new AccountInfo(account.getUsername(), wallet);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    String currencyCode = adaptCurrency(currency);
    return requestDepositAddress(currencyCode);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {

    Currency currency = null;
    Integer count = null;
    Long start = null;

    if (params instanceof TradeHistoryParamCurrency) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency();
    } else {
      throw new ExchangeException("Currency must be supplied");
    }

    if (params instanceof TradeHistoryParamLimit) {
      count = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      start = ((TradeHistoryParamOffset) params).getOffset();
    }

    return getBitmexWalletHistory(currency, count, start).stream()
        .map(BitmexAdapters::adaptFundingRecord)
        .collect(Collectors.toList());
  }
}
