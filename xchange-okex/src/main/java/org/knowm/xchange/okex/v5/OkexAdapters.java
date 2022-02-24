package org.knowm.xchange.okex.v5;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.v5.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.v5.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexInstrument;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexOrderbook;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexTrade;
import org.knowm.xchange.okex.v5.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAdapters {

  private static final String TRADING_WALLET_ID = "trading";
  private static final String FOUNDING_WALLET_ID = "founding";

  public static UserTrades adaptUserTrades(List<OkexOrderDetails> okexTradeHistory) {
    List<UserTrade> userTradeList = new ArrayList<>();

    okexTradeHistory.forEach(
        okexOrderDetails -> {
          userTradeList.add(
              new UserTrade.Builder()
                  .originalAmount(new BigDecimal(okexOrderDetails.getAmount()))
                  .instrument(new CurrencyPair(okexOrderDetails.getInstrumentId()))
                  .currencyPair(new CurrencyPair(okexOrderDetails.getInstrumentId()))
                  .price(new BigDecimal(okexOrderDetails.getAverageFilledPrice()))
                  .type(adaptOkexOrderSideToOrderType(okexOrderDetails.getSide()))
                  .id(okexOrderDetails.getOrderId())
                  .orderId(okexOrderDetails.getOrderId())
                  .timestamp(
                      Date.from(
                          Instant.ofEpochMilli(Long.parseLong(okexOrderDetails.getUpdateTime()))))
                  .feeAmount(new BigDecimal(okexOrderDetails.getFee()))
                  .feeCurrency(new Currency(okexOrderDetails.getFeeCurrency()))
                  .orderUserReference(okexOrderDetails.getClientOrderId())
                  .build());
        });

    return new UserTrades(userTradeList, Trades.TradeSortType.SortByTimestamp);
  }

  public static Order adaptOrder(OkexOrderDetails order) {
    return new LimitOrder(
        "buy".equals(order.getSide()) ? Order.OrderType.BID : Order.OrderType.ASK,
        new BigDecimal(order.getAmount()),
        new CurrencyPair(order.getInstrumentId()),
        order.getOrderId(),
        new Date(Long.parseLong(order.getCreationTime())),
        new BigDecimal(order.getPrice()),
        order.getAverageFilledPrice().isEmpty()
            ? BigDecimal.ZERO
            : new BigDecimal(order.getAverageFilledPrice()),
        new BigDecimal(order.getAccumulatedFill()),
        new BigDecimal(order.getFee()),
        "live".equals(order.getState())
            ? Order.OrderStatus.OPEN
            : Order.OrderStatus.valueOf(order.getState().toUpperCase(Locale.ENGLISH)),
        null);
  }

  public static OpenOrders adaptOpenOrders(List<OkexOrderDetails> orders) {
    List<LimitOrder> openOrders =
        orders.stream()
            .map(
                order ->
                    new LimitOrder(
                        "buy".equals(order.getSide()) ? Order.OrderType.BID : Order.OrderType.ASK,
                        new BigDecimal(order.getAmount()),
                        new CurrencyPair(order.getInstrumentId()),
                        order.getOrderId(),
                        new Date(Long.parseLong(order.getCreationTime())),
                        new BigDecimal(order.getPrice()),
                        order.getAverageFilledPrice().isEmpty()
                            ? BigDecimal.ZERO
                            : new BigDecimal(order.getAverageFilledPrice()),
                        new BigDecimal(order.getAccumulatedFill()),
                        new BigDecimal(order.getFee()),
                        "live".equals(order.getState())
                            ? Order.OrderStatus.OPEN
                            : Order.OrderStatus.valueOf(
                                order.getState().toUpperCase(Locale.ENGLISH)),
                        null))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  public static OkexAmendOrderRequest adaptAmendOrder(LimitOrder order) {
    return OkexAmendOrderRequest.builder()
        .instrumentId(adaptCurrencyPairId((CurrencyPair) order.getInstrument()))
        .orderId(order.getId())
        .amendedAmount(order.getOriginalAmount().toString())
        .amendedPrice(order.getLimitPrice().toString())
        .build();
  }

  public static OkexOrderRequest adaptOrder(LimitOrder order) {
    return OkexOrderRequest.builder()
        .instrumentId(adaptInstrumentId(order.getInstrument()))
        .tradeMode(order.getInstrument() instanceof CurrencyPair ? "cash" : "cross")
        .side(order.getType() == Order.OrderType.BID ? "buy" : "sell")
        .posSide(null) // PosSide should come as a input from an extended LimitOrder class to
        // support Futures/Swap capabilities of Okex, till then it should be null to
        // perform "net" orders
        .orderType("limit")
        .amount(order.getOriginalAmount().toString())
        .price(order.getLimitPrice().toString())
        .build();
  }

  public static OrderBook adaptOrderBook(
      OkexResponse<List<OkexOrderbook>> okexOrderbook, Instrument instrument) {

    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    okexOrderbook
        .getData()
        .get(0)
        .getAsks()
        .forEach(
            okexAsk ->
                asks.add(
                    adaptOrderbookOrder(
                        okexAsk.getVolume(), okexAsk.getPrice(), instrument, OrderType.ASK)));

    okexOrderbook
        .getData()
        .get(0)
        .getBids()
        .forEach(
            okexBid ->
                bids.add(
                    adaptOrderbookOrder(
                        okexBid.getVolume(), okexBid.getPrice(), instrument, OrderType.BID)));

    return new OrderBook(Date.from(Instant.now()), asks, bids);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount, BigDecimal price, Instrument instrument, Order.OrderType orderType) {

    return new LimitOrder(orderType, amount, instrument, "", null, price);
  }

  public static String adaptCurrencyPairId(Instrument instrument) {
    return instrument.toString().replace('/', '-');
  }

  public static String adaptInstrumentId(Instrument instrument) {
    return adaptCurrencyPairId(instrument);
  }

  public static String adaptCurrencyPairId(CurrencyPair currencyPair) {
    return currencyPair.toString().replace('/', '-');
  }

  public static Trades adaptTrades(List<OkexTrade> okexTrades, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    okexTrades.forEach(
        okexTrade ->
            trades.add(
                new Trade.Builder()
                    .id(okexTrade.getTradeId())
                    .instrument(instrument)
                    .originalAmount(okexTrade.getSz())
                    .price(okexTrade.getPx())
                    .timestamp(okexTrade.getTs())
                    .type(adaptOkexOrderSideToOrderType(okexTrade.getSide()))
                    .build()));

    return new Trades(trades);
  }

  public static Order.OrderType adaptOkexOrderSideToOrderType(String okexOrderSide) {

    return okexOrderSide.equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  private static Currency adaptCurrency(OkexCurrency currency) {
    return new Currency(currency.getCurrency());
  }

  public static CurrencyPair adaptCurrencyPair(OkexInstrument instrument) {
    return new CurrencyPair(instrument.getBaseCurrency(), instrument.getQuoteCurrency());
  }

  private static int numberOfDecimals(BigDecimal value) {
    double d = value.doubleValue();
    return -(int) Math.round(Math.log10(d));
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
          ExchangeMetaData exchangeMetaData,
          List<OkexInstrument> instruments,
          List<OkexCurrency> currs,
          List<OkexTradeFee> tradeFee) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
        exchangeMetaData.getCurrencyPairs() == null
            ? new HashMap<>()
            : exchangeMetaData.getCurrencyPairs();

    Map<Currency, CurrencyMetaData> currencies =
        exchangeMetaData.getCurrencies() == null
            ? new HashMap<>()
            : exchangeMetaData.getCurrencies();

    String makerFee = "0.5";
    if (tradeFee != null && !tradeFee.isEmpty()) {
      makerFee = tradeFee.get(0).getMaker();
    }

    for (OkexInstrument instrument : instruments) {
      if (!"live".equals(instrument.getState())) {
        continue;
      }
      CurrencyPair pair = adaptCurrencyPair(instrument);

      CurrencyPairMetaData staticMetaData = currencyPairs.get(pair);
      int priceScale = numberOfDecimals(new BigDecimal(instrument.getTickSize()));

      currencyPairs.put(
          pair,
          new CurrencyPairMetaData(
              new BigDecimal(makerFee).negate(),
              new BigDecimal(instrument.getMinSize()),
              null,
              null,
              null,
              null,
              priceScale,
              null,
              staticMetaData != null ? staticMetaData.getFeeTiers() : null,
              null,
              pair.counter,
              true));
    }

    if (currs != null) {
      currs.stream()
          .forEach(
              currency ->
                  currencies.put(
                      adaptCurrency(currency),
                      new CurrencyMetaData(
                          null,
                          new BigDecimal(currency.getMaxFee()),
                          new BigDecimal(currency.getMinWd()),
                          currency.isCanWd() && currency.isCanDep()
                              ? WalletHealth.ONLINE
                              : WalletHealth.OFFLINE)));
    }

    return new ExchangeMetaData(
        currencyPairs,
        currencies,
        exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData.getPrivateRateLimits(),
        true);
  }

  public static Wallet adaptOkexBalances(List<OkexWalletBalance> okexWalletBalanceList) {
    List<Balance> balances = new ArrayList<>();
    if (!okexWalletBalanceList.isEmpty()) {
      OkexWalletBalance okexWalletBalance = okexWalletBalanceList.get(0);
      balances =
          Arrays.stream(okexWalletBalance.getDetails())
              .map(
                  detail ->
                      new Balance.Builder()
                          .currency(new Currency(detail.getCurrency()))
                          .total(new BigDecimal(detail.getCashBalance()))
                          .available(checkForEmpty(detail.getAvailableBalance()))
                          .timestamp(new Date())
                          .build())
              .collect(Collectors.toList());
    }

    return Wallet.Builder.from(balances)
        .id(TRADING_WALLET_ID)
        .features(new HashSet<>(Collections.singletonList(Wallet.WalletFeature.TRADING)))
        .build();
  }

  public static Wallet adaptOkexAssetBalances(List<OkexAssetBalance> okexAssetBalanceList) {
    List<Balance> balances;
    balances =
        okexAssetBalanceList.stream()
            .map(
                detail ->
                    new Balance.Builder()
                        .currency(new Currency(detail.getCurrency()))
                        .total(new BigDecimal(detail.getBalance()))
                        .available(checkForEmpty(detail.getAvailableBalance()))
                        .timestamp(new Date())
                        .build())
            .collect(Collectors.toList());

    return Wallet.Builder.from(balances)
        .id(FOUNDING_WALLET_ID)
        .features(new HashSet<>(Collections.singletonList(Wallet.WalletFeature.FUNDING)))
        .build();
  }

  private static BigDecimal checkForEmpty(String value) {
    return StringUtils.isEmpty(value) ? null : new BigDecimal(value);
  }
}
