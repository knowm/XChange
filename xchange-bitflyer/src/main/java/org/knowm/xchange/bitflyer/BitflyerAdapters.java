package org.knowm.xchange.bitflyer;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBalance;
import org.knowm.xchange.bitflyer.dto.account.BitflyerCoinHistory;
import org.knowm.xchange.bitflyer.dto.account.BitflyerDepositOrWithdrawal;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Builder;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class BitflyerAdapters {
  private static Pattern CURRENCY_PATTERN = Pattern.compile("[A-Z]{3}");

  public static ExchangeMetaData adaptMetaData(List<BitflyerMarket> markets) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (BitflyerMarket market : markets) {
      CurrencyPair pair = adaptCurrencyPair(market.getProductCode());
      currencyPairs.put(pair, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, false);
  }

  public static CurrencyPair adaptCurrencyPair(String productCode) {
    Matcher matcher = CURRENCY_PATTERN.matcher(productCode);
    List<String> currencies = new ArrayList<>();
    while (matcher.find()) {
      currencies.add(matcher.group());
    }
    return currencies.size() >= 2 ? CurrencyPair.build(currencies.get(0), currencies.get(1)) : null;
  }

  /**
   * Adapts a list of BitflyerBalance objects to Wallet.
   *
   * @param balances Some BitflyerBalances from the API
   * @return A Wallet with balances in it
   */
  public static Wallet adaptAccountInfo(List<BitflyerBalance> balances) {
    List<Balance> adaptedBalances = new ArrayList<>(balances.size());

    for (BitflyerBalance balance : balances) {
      BigDecimal total = balance.getAmount();
      BigDecimal available = balance.getAvailable();
      adaptedBalances.add(
          new Balance.Builder()
              .setCurrency(Currency.valueOf(balance.getCurrencyCode()))
              .setTotal(total)
              .setAvailable(available)
              .setFrozen(total.add(available.negate()))
              .createBalance());
    }

    return Wallet.build(adaptedBalances);
  }

  /**
   * Adapts a BitflyerTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitflyerTicker ticker, CurrencyPair currencyPair) {

    BigDecimal bid = ticker.getBestBid();
    BigDecimal ask = ticker.getBestAsk();
    BigDecimal volume = ticker.getVolume();
    Date timestamp =
        ticker.getTimestamp() != null ? BitflyerUtils.parseDate(ticker.getTimestamp()) : null;

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .bid(bid)
        .ask(ask)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static List<FundingRecord> adaptFundingRecordsFromCoinHistory(
      List<BitflyerCoinHistory> coinHistory, Type type) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (BitflyerCoinHistory history : coinHistory) retVal.add(adaptFundingRecord(history, type));

    return retVal;
  }

  public static List<FundingRecord> adaptFundingRecordsFromDepositHistory(
      List<BitflyerDepositOrWithdrawal> depositWithdrawls, Type type) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (BitflyerDepositOrWithdrawal history : depositWithdrawls)
      retVal.add(adaptFundingRecord(history, type));

    return retVal;
  }

  public static FundingRecord adaptFundingRecord(BitflyerCoinHistory history, Type type) {
    return new Builder()
        .setDate(BitflyerUtils.parseDate(history.getEventDate()))
        .setCurrency(Currency.valueOf(history.getCurrencyCode()))
        .setAmount(history.getAmount())
        .setAddress(history.getAddress())
        .setInternalId(history.getID())
        .setType(type)
        .setStatus(adaptStatus(history.getStatus()))
        .setBalance(history.getAmount())
        .setFee(add(history.getFee(), history.getAdditionalFee()))
        .build();
  }

  public static FundingRecord adaptFundingRecord(BitflyerDepositOrWithdrawal history, Type type) {
    return new Builder()
        .setDate(BitflyerUtils.parseDate(history.getEventDate()))
        .setCurrency(Currency.valueOf(history.getCurrencyCode()))
        .setAmount(history.getAmount())
        .setInternalId(history.getID())
        .setType(type)
        .setStatus(adaptStatus(history.getStatus()))
        .setBalance(history.getAmount())
        .build();
  }

  private static Status adaptStatus(String status) {
    if (status.equals("COMPLETED")) return Status.COMPLETE;
    if (status.equals("PENDING")) return Status.PROCESSING;

    // ??
    return Status.FAILED;
  }

  private static BigDecimal add(BigDecimal a, BigDecimal b) {
    BigDecimal a1 = a == null ? BigDecimal.ZERO : a;
    BigDecimal b1 = b == null ? BigDecimal.ZERO : b;

    return a1.add(b1);
  }

  public static void main(String[] args) {
    adaptCurrencyPair("BTC_JPY");
    adaptCurrencyPair("BTCJPY22DEC2017");
    adaptCurrencyPair("FX_BTC_JPY");
  }
}
