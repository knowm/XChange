package org.knowm.xchange.okex;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.meta.*;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.dto.account.OkexPosition;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.dto.marketdata.*;
import org.knowm.xchange.okex.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.dto.trade.OkexOrderRequest;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAdapters {

  private static final String TRADING_WALLET_ID = "trading";
  private static final String FOUNDING_WALLET_ID = "founding";

  public static UserTrades adaptUserTrades(List<OkexOrderDetails> okexTradeHistory) {
    List<UserTrade> userTradeList = new ArrayList<>();

    okexTradeHistory.forEach(
        okexOrderDetails ->
            userTradeList.add(
                new UserTrade.Builder()
                    .originalAmount(new BigDecimal(okexOrderDetails.getAmount()))
                    .instrument(
                        adaptOkexInstrumentIdToInstrument(okexOrderDetails.getInstrumentId()))
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
                    .build()));

    return new UserTrades(userTradeList, Trades.TradeSortType.SortByTimestamp);
  }

  public static Order adaptOrder(OkexOrderDetails order) {
    return new LimitOrder(
        "buy".equals(order.getSide()) ? Order.OrderType.BID : Order.OrderType.ASK,
        new BigDecimal(order.getAmount()),
        adaptOkexInstrumentIdToInstrument(order.getInstrumentId()),
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
                        adaptOkexInstrumentIdToInstrument(order.getInstrumentId()),
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
        .instrumentId(adaptInstrumentToOkexInstrumentId(order.getInstrument()))
        .orderId(order.getId())
        .amendedAmount(order.getOriginalAmount().toString())
        .amendedPrice(order.getLimitPrice().toString())
        .build();
  }

  public static OkexOrderRequest adaptOrder(LimitOrder order) {
    return OkexOrderRequest.builder()
        .instrumentId(adaptInstrumentToOkexInstrumentId(order.getInstrument()))
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

  public static LimitOrder adaptLimitOrder(
      OkexPublicOrder okexPublicOrder, Instrument instrument, OrderType orderType) {
    return adaptOrderbookOrder(
        okexPublicOrder.getVolume(), okexPublicOrder.getPrice(), instrument, orderType);
  }

  public static LimitOrder adaptLimitOrder(
      OkexPublicOrder okexPublicOrder,
      Instrument instrument,
      OrderType orderType,
      BigDecimal multiplier) {
    return adaptOrderbookOrder(
        okexPublicOrder.getVolume(), okexPublicOrder.getPrice(), instrument, orderType, multiplier);
  }

  public static OrderBook adaptOrderBook(
      List<OkexOrderbook> okexOrderbooks, Instrument instrument) {
    List<LimitOrder> asks = Collections.synchronizedList(new ArrayList<>());
    List<LimitOrder> bids = Collections.synchronizedList(new ArrayList<>());
    synchronized (asks) {
      okexOrderbooks
          .get(0)
          .getAsks()
          .forEach(
              okexAsk ->
                  asks.add(
                      adaptOrderbookOrder(
                          okexAsk.getVolume(), okexAsk.getPrice(), instrument, OrderType.ASK)));
      //                  asks.add(adaptLimitOrder(okexAsk, instrument, OrderType.ASK)));
    }
    synchronized (bids) {
      okexOrderbooks
          .get(0)
          .getBids()
          .forEach(
              okexBid ->
                  bids.add(
                      adaptOrderbookOrder(
                          okexBid.getVolume(), okexBid.getPrice(), instrument, OrderType.BID)));
    }
    return new OrderBook(
        Date.from(java.time.Instant.ofEpochMilli(Long.parseLong(okexOrderbooks.get(0).getTs()))),
        asks,
        bids);
  }

  public static OrderBook adaptOrderBook(
      List<OkexPublicOrder> okexAsks,
      List<OkexPublicOrder> okexBids,
      Instrument instrument,
      Date date) {
    List<LimitOrder> asks = Collections.synchronizedList(new ArrayList<>());
    List<LimitOrder> bids = Collections.synchronizedList(new ArrayList<>());
    okexAsks.forEach(
        okexAsk ->
            asks.add(
                adaptOrderbookOrder(
                    okexAsk.getVolume(), okexAsk.getPrice(), instrument, OrderType.ASK)));
    okexBids.forEach(
        okexBid ->
            bids.add(
                adaptOrderbookOrder(
                    okexBid.getVolume(), okexBid.getPrice(), instrument, OrderType.BID)));
    return new OrderBook(date, asks, bids);
  }

  public static OrderBook adaptOrderBook(
      List<OkexOrderbook> okexOrderbooks, Instrument instrument, BigDecimal multiplier) {
    List<LimitOrder> asks = Collections.synchronizedList(new ArrayList<>());
    List<LimitOrder> bids = Collections.synchronizedList(new ArrayList<>());
    synchronized (asks) {
      okexOrderbooks
          .get(0)
          .getAsks()
          .forEach(
              okexAsk ->
                  asks.add(
                      adaptOrderbookOrder(
                          okexAsk.getVolume().multiply(multiplier),
                          okexAsk.getPrice(),
                          instrument,
                          OrderType.ASK)));
      //                  asks.add(adaptLimitOrder(okexAsk, instrument, OrderType.ASK)));
    }
    synchronized (bids) {
      okexOrderbooks
          .get(0)
          .getBids()
          .forEach(
              okexBid ->
                  bids.add(
                      adaptOrderbookOrder(
                          okexBid.getVolume().multiply(multiplier),
                          okexBid.getPrice(),
                          instrument,
                          OrderType.BID)));
    }
    return new OrderBook(
        Date.from(java.time.Instant.ofEpochMilli(Long.parseLong(okexOrderbooks.get(0).getTs()))),
        asks,
        bids);
  }

  public static OrderBook adaptOrderBook(
      OkexResponse<List<OkexOrderbook>> okexOrderbook, Instrument instrument) {
    return adaptOrderBook(okexOrderbook.getData(), instrument);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount, BigDecimal price, Instrument instrument, Order.OrderType orderType) {

    return new LimitOrder(orderType, amount, instrument, "", null, price);
  }

  public static LimitOrder adaptOrderbookOrder(
      BigDecimal amount,
      BigDecimal price,
      Instrument instrument,
      Order.OrderType orderType,
      BigDecimal multiplier) {

    return new LimitOrder(orderType, amount.multiply(multiplier), instrument, "", null, price);
  }

  public static Ticker adaptTicker(OkexTicker okexTicker) {
    return new Ticker.Builder()
        .instrument(adaptOkexInstrumentIdToInstrument(okexTicker.getInstrumentId()))
        .open(okexTicker.getOpen24h())
        .last(okexTicker.getLast())
        .bid(okexTicker.getBidPrice())
        .ask(okexTicker.getAskPrice())
        .high(okexTicker.getHigh24h())
        .low(okexTicker.getLow24h())
        // .vwap(null)
        .volume(okexTicker.getVolume24h())
        .quoteVolume(okexTicker.getVolumeCurrency24h())
        .timestamp(okexTicker.getTimestamp())
        .bidSize(okexTicker.getBidSize())
        .askSize(okexTicker.getAskSize())
        .percentageChange(null)
        .build();
  }

  public static Instrument adaptOkexInstrumentIdToInstrument(String instrumentId) {
    String[] tokens = instrumentId.split("-");
    if (tokens.length == 2) {
      // SPOT or Margin
      return new CurrencyPair(tokens[0], tokens[1]);
    } else if (tokens.length == 3) {
      // Future Or Swap
      return new FuturesContract(instrumentId.replace("-", "/"));
    } else if (tokens.length == 5) {
      // Option
      return new OptionsContract(instrumentId.replace("-", "/"));
    }
    return null;
  }

  public static String adaptInstrumentToOkexInstrumentId(Instrument instrument) {
    return instrument.toString().replace('/', '-');
  }

  public static String adaptInstrumentToOkexInstrumentId(Instrument instrument, Object args) {
    return instrument.toString().replace('/', '-') + "-" + args.toString();
  }

  public static String subscriptionName(Instrument instrument) {
    return instrument.toString().replace('/', '-');
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
    if (!instrument.getBaseCurrency().isEmpty())
      return new CurrencyPair(instrument.getBaseCurrency(), instrument.getQuoteCurrency());
    // for futurecurrency
    else {
      int index = instrument.getInstrumentId().indexOf("-");
      return new CurrencyPair(
          instrument.getInstrumentId().substring(0, index),
          instrument.getInstrumentId().substring(index + 1));
    }
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

    Map<FuturesContract, DerivativeMetaData> futures =
        exchangeMetaData.getFutures() == null ? new HashMap<>() : exchangeMetaData.getFutures();

    Map<OptionsContract, DerivativeMetaData> options = new HashMap<>();
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
      int priceScale = numberOfDecimals(new BigDecimal(instrument.getTickSize()));
      CurrencyPair pair = adaptCurrencyPair(instrument);
      CurrencyPairMetaData staticMetaData = currencyPairs.get(pair);
      if (instrument.getInstrumentType().equalsIgnoreCase("swap")) {
        DerivativeMetaData derivativeMetaData =
            new DerivativeMetaData(
                new BigDecimal(makerFee).negate(),
                new BigDecimal(instrument.getMinSize()),
                null,
                null,
                priceScale,
                staticMetaData != null ? staticMetaData.getFeeTiers() : null,
                instrument.getContractValue().isEmpty()
                    ? null
                    : new BigDecimal(instrument.getContractValue()),
                null);
        String[] parts = instrument.getInstrumentId().split("-");
        String p = parts[0] + "/" + parts[1] + "/SWAP";
        futures.put(new FuturesContract(p), derivativeMetaData);
      } else {
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
                instrument.getContractValue().isEmpty()
                    ? null
                    : new BigDecimal(instrument.getContractValue()),
                pair.counter,
                true));
      }
    }

    if (currs != null) {
      currs.forEach(
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
        futures,
        options,
        exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData.getPrivateRateLimits(),
        true);
    //    return new ExchangeMetaData(currencyPairs, currencies,
    // exchangeMetaData.getPublicRateLimits(), exchangeMetaData.getPrivateRateLimits(),
    //            true);
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

  public static CandleStickData adaptCandleStickData(
      List<OkexCandleStick> okexCandleStickList, CurrencyPair currencyPair) {
    CandleStickData candleStickData = null;
    if (!okexCandleStickList.isEmpty()) {
      List<CandleStick> candleStickList = new ArrayList<>();
      for (OkexCandleStick okexCandleStick : okexCandleStickList) {
        candleStickList.add(
            new CandleStick.Builder()
                .timestamp(new Date(okexCandleStick.getTimestamp()))
                .open(new BigDecimal(okexCandleStick.getOpenPrice()))
                .high(new BigDecimal(okexCandleStick.getHighPrice()))
                .low(new BigDecimal(okexCandleStick.getLowPrice()))
                .close(new BigDecimal(okexCandleStick.getClosePrice()))
                .volume(new BigDecimal(okexCandleStick.getVolume()))
                .quotaVolume(new BigDecimal(okexCandleStick.getVolumeCcy()))
                .build());
      }
      candleStickData = new CandleStickData(currencyPair, candleStickList);
    }
    return candleStickData;
  }

  public static OpenPositions adaptOpenPositions(OkexResponse<List<OkexPosition>> positions) {
    List<OpenPosition> openPositions = new ArrayList<>();

    positions
        .getData()
        .forEach(
            okexPosition ->
                openPositions.add(
                    new OpenPosition.Builder()
                        .instrument(
                            adaptOkexInstrumentIdToInstrument(okexPosition.getInstrumentId()))
                        .liquidationPrice(okexPosition.getLiquidationPrice())
                        .price(okexPosition.getAverageOpenPrice())
                        // TODO: Okx size needs a fixed ctVal in order to be correct. This ctVal
                        // needs to be saved for every pair when calling metadata
                        .type(adaptOpenPositionType(okexPosition))
                        .unRealisedPnl(okexPosition.getUnrealizedPnL())
                        .build()));
    return new OpenPositions(openPositions);
  }

  public static OpenPosition.Type adaptOpenPositionType(OkexPosition okexPosition) {
    switch (okexPosition.getPositionSide()) {
      case "long":
        return OpenPosition.Type.LONG;
      case "short":
        return OpenPosition.Type.SHORT;
      case "net":
        return (okexPosition.getPosition().compareTo(BigDecimal.ZERO) >= 0)
            ? OpenPosition.Type.LONG
            : OpenPosition.Type.SHORT;
      default:
        throw new UnsupportedOperationException();
    }
  }
}
