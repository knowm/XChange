package org.knowm.xchange.bitmex.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
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
    return new BitmexTradeHistoryParams();
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<BitmexWallet> bitmexWallet = getBitmexWallet(null);
    Wallet wallet = BitmexAdapters.toWallet(bitmexWallet);
    return new AccountInfo(wallet);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(currency.getCurrencyCode(), amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    String currencyCode = BitmexAdapters.toBitmexCode(currency);
    return requestDepositAddress(currencyCode);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {

    Currency currency = null;
    Integer count = null;
    Long start = null;

    if (params instanceof TradeHistoryParamCurrency) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency();
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
