package org.knowm.xchange.binance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinancePosition;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.service.BinanceTradeService.BinanceOrderFlags;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.instrument.Instrument;

public class BinanceAdapters {
  private static final DateTimeFormatter DATE_TIME_FMT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private BinanceAdapters() {}

  /**
   * Converts a datetime as string in time zone UTC to a Date object
   *
   * @param dateTime String that represents datetime in zone UTC
   * @return Date Object in time zone UTC
   */
  public static Date toDate(String dateTime) {
    return java.util.Date.from(Instant.from(toLocalDateTime(dateTime).atZone(ZoneId.of("UTC"))));
  }

  public static LocalDateTime toLocalDateTime(String dateTime) {
    return LocalDateTime.parse(dateTime, DATE_TIME_FMT);
  }

  public static String toSymbol(Instrument pair) {
    String symbol;

    if (pair.equals(CurrencyPair.IOTA_BTC)) {
      symbol = "IOTABTC";
    } else if(pair instanceof FuturesContract){
      symbol = ((FuturesContract) pair).getCurrencyPair().toString().replace("/","");
    } else if(pair instanceof OptionsContract) {
      symbol = ((OptionsContract) pair).getCurrencyPair().toString().replace("/","");
    } else {
      symbol = ((CurrencyPair)pair).base.getCurrencyCode() + ((CurrencyPair)pair).counter.getCurrencyCode();
    }
    return symbol;
  }

  public static String toSymbol(Currency currency) {
    if (Currency.IOT.equals(currency)) {
      return "IOTA";
    }
    return currency.getSymbol();
  }

  public static OrderType convert(OrderSide side) {
    switch (side) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        throw new RuntimeException("Not supported order side: " + side);
    }
  }

  public static OrderSide convert(OrderType type) {
    switch (type) {
      case ASK:
        return OrderSide.SELL;
      case BID:
        return OrderSide.BUY;
      default:
        throw new RuntimeException("Not supported order type: " + type);
    }
  }

  public static CurrencyPair convert(String symbol) {
    // Iterate by base currency priority at binance.
    for (Currency base : Arrays.asList(Currency.BTC, Currency.ETH, Currency.BNB, Currency.USDT)) {
      if (symbol.contains(base.toString())) {
        String counter = symbol.replace(base.toString(), "");
        return new CurrencyPair(base, new Currency(counter));
      }
    }
    throw new IllegalArgumentException("Could not parse currency pair from '" + symbol + "'");
  }

  public static long id(String id) {
    try {
      return Long.parseLong(id);
    } catch (Throwable e) {
      throw new IllegalArgumentException("Binance id must be a valid long number.", e);
    }
  }

  public static Order.OrderStatus adaptOrderStatus(OrderStatus orderStatus) {
    switch (orderStatus) {
      case NEW:
        return Order.OrderStatus.NEW;
      case FILLED:
        return Order.OrderStatus.FILLED;
      case EXPIRED:
        return Order.OrderStatus.EXPIRED;
      case CANCELED:
        return Order.OrderStatus.CANCELED;
      case REJECTED:
        return Order.OrderStatus.REJECTED;
      case PENDING_CANCEL:
        return Order.OrderStatus.PENDING_CANCEL;
      case PARTIALLY_FILLED:
        return Order.OrderStatus.PARTIALLY_FILLED;
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }

  public static OrderType convertType(boolean isBuyer) {
    return isBuyer ? OrderType.BID : OrderType.ASK;
  }

  public static Instrument adaptSymbol(String symbol) {
    int pairLength = symbol.length();
    if (symbol.endsWith("USDT")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "USDT");
    } else if (symbol.endsWith("USDC")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "USDC");
    } else if (symbol.endsWith("TUSD")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "TUSD");
    } else if (symbol.endsWith("USDS")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "USDS");
    } else if (symbol.endsWith("BUSD")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "BUSD");
    } else {
      return new CurrencyPair(
          symbol.substring(0, pairLength - 3), symbol.substring(pairLength - 3));
    }
  }

  public static OpenOrders adaptOpenOrders(List<BinanceOrder> binanceOrders){
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();
    binanceOrders.forEach(
            binanceOrder -> {
              Order order = BinanceAdapters.adaptOrder(binanceOrder);
              if (order instanceof LimitOrder) {
                limitOrders.add((LimitOrder) order);
              } else {
                otherOrders.add(order);
              }
            });

    return new OpenOrders(limitOrders, otherOrders);
  }

  public static Order adaptOrder(BinanceOrder order) {
    OrderType type = convert(order.side);
    Instrument instrument = adaptSymbol(order.symbol);
    Order.Builder builder;
    if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.MARKET)) {
      builder = new MarketOrder.Builder(type, instrument);
    } else if (order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT)
        || order.type.equals(org.knowm.xchange.binance.dto.trade.OrderType.LIMIT_MAKER)) {
      builder = new LimitOrder.Builder(type, instrument).limitPrice(order.price);
    } else {
      builder = new StopOrder.Builder(type, instrument).stopPrice(order.stopPrice);
    }
    builder
        .orderStatus(adaptOrderStatus(order.status))
        .originalAmount(order.origQty)
        .id(Long.toString(order.orderId))
        .timestamp(order.getTime())
        .cumulativeAmount(order.executedQty);
    if (order.executedQty.signum() != 0 && order.cummulativeQuoteQty.signum() != 0) {
      builder.averagePrice(
          order.cummulativeQuoteQty.divide(order.executedQty, MathContext.DECIMAL32));
    }
    if (order.clientOrderId != null) {
      builder.flag(BinanceOrderFlags.withClientId(order.clientOrderId));
    }
    return builder.build();
  }

  private static Ticker adaptPriceQuantity(BinancePriceQuantity priceQuantity) {
    return new Ticker.Builder()
        .currencyPair((CurrencyPair) adaptSymbol(priceQuantity.symbol))
        .instrument(adaptSymbol(priceQuantity.symbol))
        .ask(priceQuantity.askPrice)
        .askSize(priceQuantity.askQty)
        .bid(priceQuantity.bidPrice)
        .bidSize(priceQuantity.bidQty)
        .build();
  }

  public static List<Ticker> adaptPriceQuantities(List<BinancePriceQuantity> priceQuantities) {
    return priceQuantities.stream()
        .map(BinanceAdapters::adaptPriceQuantity)
        .collect(Collectors.toList());
  }

  static CurrencyMetaData adaptCurrencyMetaData(
      Map<Currency, CurrencyMetaData> currencies,
      Currency currency,
      Map<String, AssetDetail> assetDetailMap,
      int precision) {
    if (assetDetailMap != null) {
      AssetDetail asset = assetDetailMap.get(currency.getCurrencyCode());
      if (asset != null) {
        BigDecimal withdrawalFee = asset.getWithdrawFee().stripTrailingZeros();
        BigDecimal minWithdrawalAmount =
            new BigDecimal(asset.getMinWithdrawAmount()).stripTrailingZeros();
        WalletHealth walletHealth =
            getWalletHealth(asset.isDepositStatus(), asset.isWithdrawStatus());
        return new CurrencyMetaData(precision, withdrawalFee, minWithdrawalAmount, walletHealth);
      }
    }

    BigDecimal withdrawalFee = null;
    BigDecimal minWithdrawalAmount = null;
    if (currencies.containsKey(currency)) {
      CurrencyMetaData currencyMetaData = currencies.get(currency);
      withdrawalFee = currencyMetaData.getWithdrawalFee();
      minWithdrawalAmount = currencyMetaData.getMinWithdrawalAmount();
    }
    return new CurrencyMetaData(precision, withdrawalFee, minWithdrawalAmount);
  }

  private static WalletHealth getWalletHealth(boolean depositEnabled, boolean withdrawEnabled) {
    if (depositEnabled && withdrawEnabled) {
      return WalletHealth.ONLINE;
    }
    if (!depositEnabled && withdrawEnabled) {
      return WalletHealth.DEPOSITS_DISABLED;
    }
    if (depositEnabled) {
      return WalletHealth.WITHDRAWALS_DISABLED;
    }
    return WalletHealth.OFFLINE;
  }

  public static org.knowm.xchange.binance.dto.trade.OrderType adaptOrderType(StopOrder order) {

    if (order.getIntention() == null) {
      throw new IllegalArgumentException("Missing intention");
    }

    switch (order.getIntention()) {
      case STOP_LOSS:
        return order.getLimitPrice() == null
            ? org.knowm.xchange.binance.dto.trade.OrderType.STOP_LOSS
            : org.knowm.xchange.binance.dto.trade.OrderType.STOP_LOSS_LIMIT;
      case TAKE_PROFIT:
        return order.getLimitPrice() == null
            ? org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT
            : org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT_LIMIT;
      default:
        throw new IllegalStateException("Unexpected value: " + order.getIntention());
    }
  }

  public static List<Wallet> adaptBinanceWallets(BinanceFutureAccountInformation futureAccountInformation, BinanceAccountInformation binanceAccountInformation){
    List<Wallet> wallets = new ArrayList<>();

    BigDecimal totalPositionsInUsd = BigDecimal.ZERO;

    for (BinancePosition position : futureAccountInformation.getPositions()) {
        if(position.getPositionAmt().abs().compareTo(BigDecimal.ZERO) > 0){
          totalPositionsInUsd = totalPositionsInUsd.add(position.getPositionAmt().abs().multiply(position.getEntryPrice()));
        }
    }

    List<Balance> balances =
            binanceAccountInformation.balances.stream()
                    .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
                    .collect(Collectors.toList());

    wallets.add(Wallet.Builder.from(balances)
            .id("spot")
            .features(Collections.singleton(Wallet.WalletFeature.TRADING))
            .build());

    wallets.add(new Wallet.Builder()
            .balances(Collections.singletonList(new Balance.Builder().total(futureAccountInformation.getTotalWalletBalance()).build()))
            .id("futures")
            .currentLeverage(totalPositionsInUsd.divide(futureAccountInformation.getTotalWalletBalance(),MathContext.DECIMAL32))
            .features(Collections.singleton(Wallet.WalletFeature.FUTURES_TRADING))
            .build());

    return wallets;
  }

  public static List<OpenPosition> adaptOpenPositions(List<BinancePosition> binancePositions) {
    List<OpenPosition> openPositions = new ArrayList<>();

    for (BinancePosition position : binancePositions) {
      if(position.getPositionAmt().abs().compareTo(BigDecimal.ZERO) > 0){
        openPositions.add(new OpenPosition.Builder()
                .size(position.getPositionAmt().abs())
                .type((position.getPositionAmt().compareTo(BigDecimal.ZERO) > 0) ? OpenPosition.Type.LONG : OpenPosition.Type.SHORT)
                .unRealisedPnl(position.getUnrealizedProfit())
                .price(position.getEntryPrice())
                .instrument(adaptSymbol(position.getSymbol()))
                .build());
      }
    }

    return openPositions;
  }

  public static UserTrades adaptUserTrades(List<BinanceTrade> binanceTrades) {
    List<UserTrade> trades =
            binanceTrades.stream()
                    .map(
                            t ->
                                    new UserTrade.Builder()
                                            .type(BinanceAdapters.convertType(t.isBuyer))
                                            .originalAmount(t.qty)
                                            .instrument(adaptSymbol(t.symbol))
                                            .currencyPair((CurrencyPair) adaptSymbol(t.symbol))
                                            .instrument(adaptSymbol(t.symbol))
                                            .price(t.price)
                                            .timestamp(t.getTime())
                                            .id(Long.toString(t.id))
                                            .orderId(Long.toString(t.orderId))
                                            .feeAmount(t.commission)
                                            .feeCurrency(Currency.getInstance(t.commissionAsset))
                                            .build())
                    .collect(Collectors.toList());
    long lastId = binanceTrades.stream().map(t -> t.id).max(Long::compareTo).orElse(0L);
    return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
  }

  public static Trades adaptTrades(List<BinanceAggTrades> aggTrades, Instrument instrument) {
    List<Trade> trades =
            aggTrades.stream()
                    .map(
                            at ->
                                    new Trade.Builder()
                                            .type(BinanceAdapters.convertType(at.buyerMaker))
                                            .originalAmount(at.quantity)
                                            .instrument(instrument)
                                            .price(at.price)
                                            .timestamp(at.getTimestamp())
                                            .id(Long.toString(at.aggregateTradeId))
                                            .build())
                    .collect(Collectors.toList());
    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  /**
   * @param klines
   * @param currencyPair
   * @return
   */
  public static CandleStickData adaptBinanceCandleStickData(
      List<BinanceKline> klines, CurrencyPair currencyPair) {

    CandleStickData candleStickData = null;
    if (klines.size() != 0) {
      List<CandleStick> candleSticks = new ArrayList<>();
      for (BinanceKline chartData : klines) {
        candleSticks.add(
            new CandleStick.Builder()
                .timestamp(new Date(chartData.getCloseTime()))
                .open(chartData.getOpenPrice())
                .high(chartData.getHighPrice())
                .low(chartData.getLowPrice())
                .close(chartData.getClosePrice())
                .volume(chartData.getVolume())
                .quotaVolume(chartData.getQuoteAssetVolume())
                .build());
      }
      candleStickData = new CandleStickData(currencyPair, candleSticks);
    }

    return candleStickData;
  }
}
