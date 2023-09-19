package org.knowm.xchange.bitflyer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.knowm.xchange.bitflyer.dto.account.BitflyerBalance;
import org.knowm.xchange.bitflyer.dto.account.BitflyerCoinHistory;
import org.knowm.xchange.bitflyer.dto.account.BitflyerDepositOrWithdrawal;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerChildOrderAcceptance;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerParentOrderAcceptance;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerQueryChildOrderResult;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerTradingCommission;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.instrument.Instrument;

public class BitflyerAdapters {
  private static Pattern CURRENCY_PATTERN = Pattern.compile("[A-Z]{3}");

  public static ExchangeMetaData adaptMetaData(List<BitflyerMarket> markets) {
    Map<Instrument, InstrumentMetaData> currencyPairs = new HashMap<>();
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
    return currencies.size() >= 2 ? new CurrencyPair(currencies.get(0), currencies.get(1)) : null;
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
      adaptedBalances.add(
          new Balance(
              Currency.getInstance(balance.getCurrencyCode()),
              balance.getAmount(),
              balance.getAvailable()));
    }

    return Wallet.Builder.from(adaptedBalances).build();
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
    BigDecimal last = ticker.getLtp();
    Date timestamp =
        ticker.getTimestamp() != null ? BitflyerUtils.parseDate(ticker.getTimestamp()) : null;

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .bid(bid)
        .ask(ask)
        .last(ask)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static List<FundingRecord> adaptFundingRecordsFromCoinHistory(
      List<BitflyerCoinHistory> coinHistory, FundingRecord.Type type) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (BitflyerCoinHistory history : coinHistory) retVal.add(adaptFundingRecord(history, type));

    return retVal;
  }

  public static List<FundingRecord> adaptFundingRecordsFromDepositHistory(
      List<BitflyerDepositOrWithdrawal> depositWithdrawls, FundingRecord.Type type) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (BitflyerDepositOrWithdrawal history : depositWithdrawls)
      retVal.add(adaptFundingRecord(history, type));

    return retVal;
  }

  public static FundingRecord adaptFundingRecord(
      BitflyerCoinHistory history, FundingRecord.Type type) {
    return FundingRecord.builder()
        .date(BitflyerUtils.parseDate(history.getEventDate()))
        .currency(new Currency(history.getCurrencyCode()))
        .amount(history.getAmount())
        .address(history.getAddress())
        .internalId(history.getID())
        .type(type)
        .status(adaptStatus(history.getStatus()))
        .balance(history.getAmount())
        .fee(add(history.getFee(), history.getAdditionalFee()))
        .build();
  }

  public static FundingRecord adaptFundingRecord(
      BitflyerDepositOrWithdrawal history, FundingRecord.Type type) {
    return FundingRecord.builder()
        .date(BitflyerUtils.parseDate(history.getEventDate()))
        .currency(new Currency(history.getCurrencyCode()))
        .amount(history.getAmount())
        .internalId(history.getID())
        .type(type)
        .status(adaptStatus(history.getStatus()))
        .balance(history.getAmount())
        .build();
  }

  public static OpenOrders adaptOpenOrdersFromChildOrderResults(
      List<BitflyerQueryChildOrderResult> queryResults) {
    return new OpenOrders(
        queryResults.stream()
            .map(
                result ->
                    new LimitOrder.Builder(
                            adaptSide(result.getSide()),
                            new CurrencyPair(result.getProductCode().replace("_", "/")))
                        .id(result.getChildOrderId())
                        .orderStatus(adaptOrderStatus(result.getChildOrderState()))
                        .timestamp(BitflyerUtils.parseDate(result.getChildOrderDate()))
                        .limitPrice(result.getPrice())
                        .averagePrice(result.getAveragePrice())
                        .originalAmount(result.getSize())
                        .remainingAmount(result.getOutstandingSize())
                        .cumulativeAmount(result.getExecutedSize())
                        .fee(result.getTotalCommission())
                        .build())
            .collect(Collectors.toList()));
  }

  public static Fee adaptTradingCommission(BitflyerTradingCommission commission) {
    return new Fee(commission.getCommissionRate(), commission.getCommissionRate());
  }

  public static String adaptOrderId(BitflyerChildOrderAcceptance orderAcceptance) {
    return orderAcceptance.getChildOrderAcceptanceId();
  }

  public static String adaptOrderId(BitflyerParentOrderAcceptance orderAcceptance) {
    return orderAcceptance.getParentOrderAcceptanceId();
  }

  private static Order.OrderType adaptSide(String side) {
    return "BUY".equals(side) ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  private static Order.OrderStatus adaptOrderStatus(String status) {
    if ("ACTIVE".equals(status)) return Order.OrderStatus.NEW;
    if ("COMPLETED".equals(status)) return Order.OrderStatus.FILLED;
    if ("CANCELED".equals(status)) return Order.OrderStatus.CANCELED;
    if ("EXPIRED".equals(status)) return Order.OrderStatus.EXPIRED;
    if ("REJECTED".equals(status)) return Order.OrderStatus.REJECTED;

    return Order.OrderStatus.UNKNOWN;
  }

  private static FundingRecord.Status adaptStatus(String status) {
    if (status.equals("COMPLETED")) return FundingRecord.Status.COMPLETE;
    if (status.equals("PENDING")) return FundingRecord.Status.PROCESSING;

    // ??
    return FundingRecord.Status.FAILED;
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
