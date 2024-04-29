package org.knowm.xchange.binance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinancePosition;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceFundingRate;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Symbol;
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
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRates;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
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

    return toSymbol(pair, false);
  }

  public static String toInverseSymbol(Instrument pair) {

    return toSymbol(pair, true);
  }

  public static Boolean isInverse(Instrument pair) {
    if (pair instanceof FuturesContract && pair.getCounter().equals(Currency.USD)) {
      return true;
    } else {
      return false;
    }
  }

  public static String toSymbol(Instrument pair, Boolean isInverse) {
    String symbol;

    if (pair.equals(CurrencyPair.IOTA_BTC)) {
      symbol = "IOTABTC";
    } else if (pair instanceof FuturesContract) {
      if (isInverse) {
        FuturesContract contract = (FuturesContract) pair;
        symbol = contract.getCurrencyPair().toString().replace("/", "");
        symbol = symbol + "_" + contract.getPrompt();
      } else {
        symbol = ((FuturesContract) pair).getCurrencyPair().toString().replace("/", "");
      }
    } else if (pair instanceof OptionsContract) {
      symbol = ((OptionsContract) pair).getCurrencyPair().toString().replace("/", "");
    } else {
      symbol =
          ((CurrencyPair) pair).base.getCurrencyCode()
              + ((CurrencyPair) pair).counter.getCurrencyCode();
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

  public static Instrument adaptSymbol(String symbol, boolean isFuture) {
    CurrencyPair currencyPair = BinanceExchange.toCurrencyPair(symbol);

    return (isFuture) ? new FuturesContract(currencyPair, "PERP") : currencyPair;
  }

  public static OpenOrders adaptOpenOrders(List<BinanceOrder> binanceOrders, boolean isFuture) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();
    binanceOrders.forEach(
        binanceOrder -> {
          Order order = BinanceAdapters.adaptOrder(binanceOrder, isFuture);
          if (order instanceof LimitOrder) {
            limitOrders.add((LimitOrder) order);
          } else {
            otherOrders.add(order);
          }
        });

    return new OpenOrders(limitOrders, otherOrders);
  }

  public static Order adaptOrder(BinanceOrder order, boolean isFuture) {
    OrderType type = convert(order.side);
    Instrument instrument = adaptSymbol(order.symbol, isFuture);
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

  public static Ticker toTicker(BinancePriceQuantity priceQuantity, Map<Instrument, BigDecimal> prices, boolean isFuture) {
    Instrument instrument = (isFuture) ? new FuturesContract(priceQuantity.getCurrencyPair(), "PERP") : priceQuantity.getCurrencyPair();
    return new Builder()
        .instrument(instrument)
        .last(prices.get(instrument))
        .ask(priceQuantity.getAskPrice())
        .askSize(priceQuantity.getAskQty())
        .bid(priceQuantity.getBidPrice())
        .bidSize(priceQuantity.getBidQty())
        .build();
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
            ? (order.getInstrument() instanceof CurrencyPair)
                ? org.knowm.xchange.binance.dto.trade.OrderType.STOP_LOSS
                : org.knowm.xchange.binance.dto.trade.OrderType.STOP_MARKET
            : (order.getInstrument() instanceof CurrencyPair)
                ? org.knowm.xchange.binance.dto.trade.OrderType.STOP_LOSS_LIMIT
                : org.knowm.xchange.binance.dto.trade.OrderType.STOP;
      case TAKE_PROFIT:
        return order.getLimitPrice() == null
            ? (order.getInstrument() instanceof CurrencyPair)
                ? org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT
                : org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT_MARKET
            : (order.getInstrument() instanceof CurrencyPair)
                ? org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT_LIMIT
                : org.knowm.xchange.binance.dto.trade.OrderType.TAKE_PROFIT;
      default:
        throw new IllegalStateException("Unexpected value: " + order.getIntention());
    }
  }

  public static Wallet adaptBinanceFutureWallet(
      BinanceFutureAccountInformation futureAccountInformation) {
    BigDecimal totalPositionsInUsd = BigDecimal.ZERO;

    for (BinancePosition position : futureAccountInformation.getPositions()) {
      if (position.getPositionAmt().abs().compareTo(BigDecimal.ZERO) > 0) {
        totalPositionsInUsd =
            totalPositionsInUsd.add(
                position.getPositionAmt().abs().multiply(position.getEntryPrice()));
      }
    }

    return new Wallet.Builder()
        .balances(
            Collections.singletonList(
                new Balance.Builder()
                    .currency(Currency.USD)
                    .total(futureAccountInformation.getTotalWalletBalance())
                    .build()))
        .id("futures")
        .currentLeverage(
            (totalPositionsInUsd.compareTo(BigDecimal.ZERO) != 0)
                ? totalPositionsInUsd.divide(
                    futureAccountInformation.getTotalWalletBalance(), MathContext.DECIMAL32)
                : BigDecimal.ZERO)
        .features(Collections.singleton(Wallet.WalletFeature.FUTURES_TRADING))
        .build();
  }

  public static Wallet adaptBinanceSpotWallet(BinanceAccountInformation binanceAccountInformation) {

    List<Balance> balances =
        binanceAccountInformation.balances.stream()
            .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
            .collect(Collectors.toList());

    return new Wallet.Builder()
        .balances(balances)
        .id("spot")
        .features(Collections.singleton(Wallet.WalletFeature.TRADING))
        .build();
  }

  public static List<OpenPosition> adaptOpenPositions(List<BinancePosition> binancePositions) {
    List<OpenPosition> openPositions = new ArrayList<>();

    for (BinancePosition position : binancePositions) {
      if (position.getPositionAmt().abs().compareTo(BigDecimal.ZERO) > 0) {
        openPositions.add(
            new OpenPosition.Builder()
                .size(position.getPositionAmt().abs())
                .type(
                    (position.getPositionAmt().compareTo(BigDecimal.ZERO) > 0)
                        ? OpenPosition.Type.LONG
                        : OpenPosition.Type.SHORT)
                .unRealisedPnl(position.getUnrealizedProfit())
                .price(position.getEntryPrice())
                .instrument(adaptSymbol(position.getSymbol(), true))
                .build());
      }
    }

    return openPositions;
  }

  public static UserTrades adaptUserTrades(List<BinanceTrade> binanceTrades, boolean isFuture) {
    List<UserTrade> trades =
        binanceTrades.stream()
            .map(
                t ->
                    UserTrade.builder()
                        .type(BinanceAdapters.convertType(t.isBuyer))
                        .originalAmount(t.qty)
                        .instrument(adaptSymbol(t.symbol, isFuture))
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
                .open(chartData.getOpen())
                .high(chartData.getHigh())
                .low(chartData.getLow())
                .close(chartData.getClose())
                .volume(chartData.getVolume())
                .quotaVolume(chartData.getQuoteAssetVolume())
                .build());
      }
      candleStickData = new CandleStickData(currencyPair, candleSticks);
    }

    return candleStickData;
  }

  public static void adaptFutureExchangeMetaData(
      ExchangeMetaData exchangeMetaData, BinanceExchangeInfo binanceExchangeInfo) {
    List<Symbol> futureSymbols = binanceExchangeInfo.getSymbols();

    for (Symbol futureSymbol : futureSymbols) {
      if (futureSymbol.getStatus().equals("TRADING")) { // Symbols which are trading
        int pairPrecision = 8;
        int amountPrecision = 8;

        BigDecimal minQty = null;
        BigDecimal maxQty = null;
        BigDecimal stepSize = null;

        BigDecimal counterMinQty = null;
        BigDecimal counterMaxQty = null;

        Instrument currentCurrencyPair =
            new FuturesContract(
                new CurrencyPair(futureSymbol.getBaseAsset() + "/" + futureSymbol.getQuoteAsset()),
                "PERP");

        for (Filter filter : futureSymbol.getFilters()) {
          switch (filter.getFilterType()) {
            case "PRICE_FILTER":
              pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getTickSize()));
              counterMaxQty = new BigDecimal(filter.getMaxPrice()).stripTrailingZeros();
              break;
            case "LOT_SIZE":
              amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getStepSize()));
              minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
              maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();
              stepSize = new BigDecimal(filter.getStepSize()).stripTrailingZeros();
              break;
            case "MIN_NOTIONAL":
              counterMinQty =
                  (filter.getMinNotional() != null)
                      ? new BigDecimal(filter.getMinNotional()).stripTrailingZeros()
                      : null;
              break;
          }
        }

        exchangeMetaData
            .getInstruments()
            .put(
                currentCurrencyPair,
                new InstrumentMetaData.Builder()
                    .minimumAmount(minQty)
                    .maximumAmount(maxQty)
                    .counterMinimumAmount(counterMinQty)
                    .counterMaximumAmount(counterMaxQty)
                    .volumeScale(amountPrecision)
                    .priceScale(pairPrecision)
                    .amountStepSize(stepSize)
                    .marketOrderEnabled(
                        Arrays.asList(futureSymbol.getOrderTypes()).contains("MARKET"))
                    .build());
      }
    }
  }

  public static ExchangeMetaData adaptExchangeMetaData(
      BinanceExchangeInfo binanceExchangeInfo, Map<String, AssetDetail> assetDetailMap) {
    // populate currency pair keys only, exchange does not provide any other metadata for download
    Map<Instrument, InstrumentMetaData> instruments = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    List<Symbol> symbols = binanceExchangeInfo.getSymbols();

    for (Symbol symbol : symbols) {
      if (symbol.getStatus().equals("TRADING")) { // Symbols which are trading
        int basePrecision = Integer.parseInt(symbol.getBaseAssetPrecision());
        int counterPrecision = Integer.parseInt(symbol.getQuotePrecision());
        int pairPrecision = 8;
        int amountPrecision = 8;

        BigDecimal minQty = null;
        BigDecimal maxQty = null;
        BigDecimal stepSize = null;

        BigDecimal counterMinQty = null;
        BigDecimal counterMaxQty = null;

        CurrencyPair currentCurrencyPair =
            new CurrencyPair(symbol.getBaseAsset(), symbol.getQuoteAsset());

        for (Filter filter : symbol.getFilters()) {
          switch (filter.getFilterType()) {
            case "PRICE_FILTER":
              pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getTickSize()));
              counterMaxQty = new BigDecimal(filter.getMaxPrice()).stripTrailingZeros();
              break;
            case "LOT_SIZE":
              amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getStepSize()));
              minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
              maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();
              stepSize = new BigDecimal(filter.getStepSize()).stripTrailingZeros();
              break;
            case "MIN_NOTIONAL":
              counterMinQty = new BigDecimal(filter.getMinNotional()).stripTrailingZeros();
              break;
          }
        }

        instruments.put(
            currentCurrencyPair,
            new InstrumentMetaData.Builder()
                .tradingFee(BigDecimal.valueOf(0.1))
                .minimumAmount(minQty)
                .maximumAmount(maxQty)
                .counterMinimumAmount(counterMinQty)
                .counterMaximumAmount(counterMaxQty)
                .volumeScale(amountPrecision)
                .priceScale(pairPrecision)
                .amountStepSize(stepSize)
                .marketOrderEnabled(Arrays.asList(symbol.getOrderTypes()).contains("MARKET"))
                .build());
        Currency baseCurrency = currentCurrencyPair.base;
        CurrencyMetaData baseCurrencyMetaData =
            BinanceAdapters.adaptCurrencyMetaData(
                currencies, baseCurrency, assetDetailMap, basePrecision);
        currencies.put(baseCurrency, baseCurrencyMetaData);

        Currency counterCurrency = currentCurrencyPair.counter;
        CurrencyMetaData counterCurrencyMetaData =
            BinanceAdapters.adaptCurrencyMetaData(
                currencies, counterCurrency, assetDetailMap, counterPrecision);
        currencies.put(counterCurrency, counterCurrencyMetaData);
      }
    }

    return new ExchangeMetaData(instruments, currencies, null, null, true);
  }

  private static int numberOfDecimals(String value) {
    return new BigDecimal(value).stripTrailingZeros().scale();
  }

  public static FundingRates adaptFundingRates(List<BinanceFundingRate> binanceFundingRates) {
    List<FundingRate> fundingRates = new ArrayList<>();

    binanceFundingRates.stream()
        .filter(binanceFundingRate -> binanceFundingRate.getNextFundingTime().getTime() != 0)
        .forEach(binanceFundingRate -> fundingRates.add(adaptFundingRate(binanceFundingRate)));

    return new FundingRates(fundingRates);
  }

  public static FundingRate adaptFundingRate(BinanceFundingRate binanceFundingRate) {
    return new FundingRate.Builder()
        .fundingRate1h(
            binanceFundingRate
                .getLastFundingRate()
                .divide(
                    BigDecimal.valueOf(8),
                    binanceFundingRate.getLastFundingRate().scale(),
                    RoundingMode.HALF_EVEN))
        .fundingRate8h(binanceFundingRate.getLastFundingRate())
        .instrument(binanceFundingRate.getInstrument())
        .fundingRateDate(binanceFundingRate.getNextFundingTime())
        .fundingRateEffectiveInMinutes(
            TimeUnit.MILLISECONDS.toMinutes(
                binanceFundingRate.getNextFundingTime().getTime()
                    - binanceFundingRate.getTime().getTime()))
        .build();
  }
}
