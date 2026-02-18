package org.knowm.xchange.bitfinex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeeResponse;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLendLevel;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderFlags;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexLedgerEntry;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexMovement;
import org.knowm.xchange.bitfinex.v2.dto.account.BitfinexWallet;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTickerFundingCurrency;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTickerTraidingPair;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexOrderDetails;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexPosition;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.Builder;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Slf4j
@UtilityClass
public class BitfinexAdapters {

  public final long CATEGORY_TRANSFER = 51L;
  private static final Pattern WALLET_TRANSFER_PATTERN =
      Pattern.compile(("transfer of .* from wallet .* to .* on wallet .*"));
  private static final Pattern SUB_ACCOUNT_TRANSFER_PATTERN =
      Pattern.compile(("transfer of .* from .* to exchange sa\\(.*\\).*"));
  private static final Pattern WITHDRAWAL_PATTERN = Pattern.compile((".* withdrawal .*"));

  private final ObjectMapper mapper = new ObjectMapper();

  private final AtomicBoolean warnedStopLimit = new AtomicBoolean();

  private final Map<String, String> STRING_TO_CURRENCY = new HashMap<>();

  public void putCurrencyMapping(String exchangeCurrencyId, String commonCurrencyId) {
    STRING_TO_CURRENCY.put(exchangeCurrencyId, commonCurrencyId);
  }

  /**
   * Each element in the response array contains a set of currencies that are at a given fee tier.
   * The API returns the fee per currency in each tier and does not make any promises that they are
   * all the same, so this adapter will use the fee per currency instead of the fee per tier.
   */
  public Map<Instrument, Fee> adaptDynamicTradingFees(
      BitfinexTradingFeeResponse[] responses, List<Instrument> currencyPairs) {
    Map<Instrument, Fee> result = new HashMap<>();
    for (BitfinexTradingFeeResponse response : responses) {
      BitfinexTradingFeeResponse.BitfinexTradingFeeResponseRow[] responseRows =
          response.getTradingFees();
      for (BitfinexTradingFeeResponse.BitfinexTradingFeeResponseRow responseRow : responseRows) {
        Currency currency = Currency.getInstance(responseRow.getCurrency());
        BigDecimal percentToFraction = BigDecimal.ONE.divide(BigDecimal.ONE.scaleByPowerOfTen(2));
        Fee fee =
            new Fee(
                responseRow.getMakerFee().multiply(percentToFraction),
                responseRow.getTakerFee().multiply(percentToFraction));
        for (Instrument pair : currencyPairs) {
          // Fee to trade for a currency is the fee to trade currency pairs with this base.
          // Fee is typically assessed in units counter.
          if (pair.getBase().equals(currency)) {
            if (result.put(pair, fee) != null) {
              throw new IllegalStateException(
                  "Fee for currency pair " + pair + " is overspecified");
            }
          }
        }
      }
    }
    return result;
  }

  public String adaptBitfinexCurrency(String bitfinexSymbol) {
    String currentValue = bitfinexSymbol;

    // mapping can be nested, e.g. USTF0 -> USDT -> USDt
    while (STRING_TO_CURRENCY.containsKey(currentValue)) {
      var newValue = STRING_TO_CURRENCY.get(currentValue);

      // avoid infinite loop e.g. FET -> FET
      if (newValue.equals(currentValue)) {
        return currentValue;
      } else {
        currentValue = newValue;
      }
    }

    return currentValue;
  }

  public CurrencyPair toXChangeCurrencyPair(CurrencyPair currencyPair) {
    return new CurrencyPair(
        toCurrency(currencyPair.getBase().getCurrencyCode()),
        toCurrency(currencyPair.getCounter().getCurrencyCode()));
  }

  public String adaptOrderType(OrderType type) {
    switch (type) {
      case BID:
      case EXIT_BID:
        return "buy";
      case ASK:
      case EXIT_ASK:
        return "sell";
    }

    throw new IllegalArgumentException(String.format("Unexpected type of order: %s", type));
  }

  public BitfinexOrderType adaptOrderFlagsToType(Set<Order.IOrderFlags> flags) {
    if (flags.contains(BitfinexOrderFlags.MARGIN)) {
      if (flags.contains(BitfinexOrderFlags.FILL_OR_KILL)) {
        return BitfinexOrderType.MARGIN_FILL_OR_KILL;
      } else if (flags.contains(BitfinexOrderFlags.TRAILING_STOP)) {
        return BitfinexOrderType.MARGIN_TRAILING_STOP;
      } else if (flags.contains(BitfinexOrderFlags.STOP)) {
        return BitfinexOrderType.MARGIN_STOP;
      } else {
        return BitfinexOrderType.MARGIN_LIMIT;
      }
    } else {
      if (flags.contains(BitfinexOrderFlags.FILL_OR_KILL)) {
        return BitfinexOrderType.FILL_OR_KILL;
      } else if (flags.contains(BitfinexOrderFlags.TRAILING_STOP)) {
        return BitfinexOrderType.TRAILING_STOP;
      } else if (flags.contains(BitfinexOrderFlags.STOP)) {
        return BitfinexOrderType.STOP;
      } else {
        return BitfinexOrderType.LIMIT;
      }
    }
  }

  public CurrencyPair adaptCurrencyPair(String bitfinexSymbol) {
    String tradableIdentifier;
    String transactionCurrency;
    int startIndex =
        bitfinexSymbol.startsWith("t") && Character.isUpperCase(bitfinexSymbol.charAt(1)) ? 1 : 0;
    if (bitfinexSymbol.contains(":")) {
      // ie 'dusk:usd' or 'btc:cnht'
      int idx = bitfinexSymbol.indexOf(":");
      tradableIdentifier = bitfinexSymbol.substring(startIndex, idx);
      transactionCurrency = bitfinexSymbol.substring(idx + 1);
    } else {
      tradableIdentifier = bitfinexSymbol.substring(startIndex, startIndex + 3);
      transactionCurrency = bitfinexSymbol.substring(startIndex + 3);
    }

    return new CurrencyPair(toCurrency(tradableIdentifier), toCurrency(transactionCurrency));
  }

  public OrderStatus adaptOrderStatus(BitfinexOrderStatusResponse order) {

    if (order.isCancelled()) return OrderStatus.CANCELED;
    else if (order.getExecutedAmount().compareTo(BigDecimal.ZERO) == 0) return OrderStatus.NEW;
    else if (order.getExecutedAmount().compareTo(order.getOriginalAmount()) < 0)
      return OrderStatus.PARTIALLY_FILLED;
    else if (order.getExecutedAmount().compareTo(order.getOriginalAmount()) == 0)
      return OrderStatus.FILLED;
    else return null;
  }

  public String adaptCurrencyPair(CurrencyPair pair) {
    return BitfinexUtils.toPairString(pair);
  }

  public OrderBook adaptOrderBook(BitfinexDepth btceDepth, Instrument instrument) {

    OrdersContainer asksOrdersContainer =
        adaptOrders(btceDepth.getAsks(), instrument, OrderType.ASK);
    OrdersContainer bidsOrdersContainer =
        adaptOrders(btceDepth.getBids(), instrument, OrderType.BID);

    return new OrderBook(
        new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(),
        bidsOrdersContainer.getLimitOrders());
  }

  public OrdersContainer adaptOrders(
      BitfinexLevel[] bitfinexLevels, Instrument instrument, OrderType orderType) {

    BigDecimal maxTimestamp = new BigDecimal(Long.MIN_VALUE);
    List<LimitOrder> limitOrders = new ArrayList<>(bitfinexLevels.length);

    for (BitfinexLevel bitfinexLevel : bitfinexLevels) {
      if (bitfinexLevel.getTimestamp().compareTo(maxTimestamp) > 0) {
        maxTimestamp = bitfinexLevel.getTimestamp();
      }

      Date timestamp = convertBigDecimalTimestampToDate(bitfinexLevel.getTimestamp());
      limitOrders.add(
          adaptOrder(
              bitfinexLevel.getAmount(),
              bitfinexLevel.getPrice(),
              instrument,
              orderType,
              timestamp));
    }

    long maxTimestampInMillis = maxTimestamp.multiply(new BigDecimal(1000L)).longValue();
    return new OrdersContainer(maxTimestampInMillis, limitOrders);
  }

  public LimitOrder adaptOrder(
      BigDecimal originalAmount,
      BigDecimal price,
      Instrument instrument,
      OrderType orderType,
      Date timestamp) {

    return new LimitOrder(orderType, originalAmount, instrument, "", timestamp, price);
  }

  public List<FixedRateLoanOrder> adaptFixedRateLoanOrders(
      BitfinexLendLevel[] orders, String currency, String orderType, String id) {

    List<FixedRateLoanOrder> loanOrders = new ArrayList<>(orders.length);

    for (BitfinexLendLevel order : orders) {
      if ("yes".equalsIgnoreCase(order.getFrr())) {
        continue;
      }

      // Bid orderbook is reversed order. Insert at reversed indices
      if (orderType.equalsIgnoreCase("loan")) {
        loanOrders.add(
            0,
            adaptFixedRateLoanOrder(
                currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      } else {
        loanOrders.add(
            adaptFixedRateLoanOrder(
                currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      }
    }

    return loanOrders;
  }

  public FixedRateLoanOrder adaptFixedRateLoanOrder(
      String currency,
      BigDecimal amount,
      int dayPeriod,
      String direction,
      String id,
      BigDecimal rate) {

    OrderType orderType = direction.equalsIgnoreCase("loan") ? OrderType.BID : OrderType.ASK;

    return new FixedRateLoanOrder(orderType, currency, amount, dayPeriod, id, null, rate);
  }

  public List<FloatingRateLoanOrder> adaptFloatingRateLoanOrders(
      BitfinexLendLevel[] orders, String currency, String orderType, String id) {

    List<FloatingRateLoanOrder> loanOrders = new ArrayList<>(orders.length);

    for (BitfinexLendLevel order : orders) {
      if ("no".equals(order.getFrr())) {
        continue;
      }

      // Bid orderbook is reversed order. Insert at reversed indices
      if (orderType.equalsIgnoreCase("loan")) {
        loanOrders.add(
            0,
            adaptFloatingRateLoanOrder(
                currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      } else {
        loanOrders.add(
            adaptFloatingRateLoanOrder(
                currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      }
    }

    return loanOrders;
  }

  public FloatingRateLoanOrder adaptFloatingRateLoanOrder(
      String currency,
      BigDecimal amount,
      int dayPeriod,
      String direction,
      String id,
      BigDecimal rate) {

    OrderType orderType = direction.equalsIgnoreCase("loan") ? OrderType.BID : OrderType.ASK;

    return new FloatingRateLoanOrder(orderType, currency, amount, dayPeriod, id, null, rate);
  }

  public Trade adaptTrade(BitfinexTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date =
        DateUtils.fromMillisUtc(trade.getTimestamp() * 1000L); // Bitfinex uses Unix timestamps
    final String tradeId = String.valueOf(trade.getTradeId());
    return Trade.builder()
        .type(orderType)
        .originalAmount(amount)
        .instrument(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  public Trades adaptTrades(BitfinexTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (BitfinexTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public Ticker adaptTicker(BitfinexTicker bitfinexTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitfinexTicker.getLast_price();
    BigDecimal bid = bitfinexTicker.getBid();
    BigDecimal bidSize = bitfinexTicker.getBidSize();
    BigDecimal ask = bitfinexTicker.getAsk();
    BigDecimal askSize = bitfinexTicker.getAskSize();
    BigDecimal high = bitfinexTicker.getHigh();
    BigDecimal low = bitfinexTicker.getLow();
    BigDecimal volume = bitfinexTicker.getVolume();

    Date timestamp = DateUtils.fromMillisUtc((long) (bitfinexTicker.getTimestamp() * 1000L));

    return new Ticker.Builder()
        .instrument(currencyPair)
        .last(last)
        .bid(bid)
        .bidSize(bidSize)
        .ask(ask)
        .askSize(askSize)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public List<Wallet> adaptWallets(BitfinexBalancesResponse[] response) {

    Map<String, Map<String, BigDecimal[]>> walletsBalancesMap = new HashMap<>();

    // for each currency we have multiple balances types: exchange, trading, deposit.
    // each of those may be partially frozen/available
    for (BitfinexBalancesResponse balance : response) {
      String walletId = balance.getType();

      if (!walletsBalancesMap.containsKey(walletId)) {
        walletsBalancesMap.put(walletId, new HashMap<>());
      }
      Map<String, BigDecimal[]> balancesByCurrency =
          walletsBalancesMap.get(walletId); // {total, available}

      String currencyName = adaptBitfinexCurrency(balance.getCurrency());
      BigDecimal[] balanceDetail = balancesByCurrency.get(currencyName);
      if (balanceDetail == null) {
        balanceDetail = new BigDecimal[] {balance.getAmount(), balance.getAvailable()};
      } else {
        balanceDetail[0] = balanceDetail[0].add(balance.getAmount());
        balanceDetail[1] = balanceDetail[1].add(balance.getAvailable());
      }
      balancesByCurrency.put(currencyName, balanceDetail);
    }

    List<Wallet> wallets = new ArrayList<>();
    for (Entry<String, Map<String, BigDecimal[]>> walletData : walletsBalancesMap.entrySet()) {
      Map<String, BigDecimal[]> balancesByCurrency = walletData.getValue();

      List<Balance> balances = new ArrayList<>(balancesByCurrency.size());
      for (Entry<String, BigDecimal[]> entry : balancesByCurrency.entrySet()) {
        String currencyName = entry.getKey();
        BigDecimal[] balanceDetail = entry.getValue();
        BigDecimal balanceTotal = balanceDetail[0];
        BigDecimal balanceAvailable = balanceDetail[1];
        balances.add(
            new Balance(Currency.getInstance(currencyName), balanceTotal, balanceAvailable));
      }
      wallets.add(Wallet.Builder.from(balances).id(walletData.getKey()).build());
    }

    return wallets;
  }

  public OpenOrders adaptOrders(BitfinexOrderStatusResponse[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> hiddenOrders = new ArrayList<>();

    for (BitfinexOrderStatusResponse order : activeOrders) {

      OrderType orderType = order.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      OrderStatus status = adaptOrderStatus(order);
      CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
      Date timestamp = convertBigDecimalTimestampToDate(order.getTimestamp());

      Supplier<MarketOrder> marketOrderCreator =
          () ->
              new MarketOrder(
                  orderType,
                  order.getOriginalAmount(),
                  currencyPair,
                  String.valueOf(order.getId()),
                  timestamp,
                  order.getAvgExecutionPrice(),
                  order.getExecutedAmount(),
                  null,
                  status);

      Supplier<LimitOrder> limitOrderCreator =
          () ->
              new LimitOrder(
                  orderType,
                  order.getOriginalAmount(),
                  currencyPair,
                  String.valueOf(order.getId()),
                  timestamp,
                  order.getPrice(),
                  order.getAvgExecutionPrice(),
                  order.getExecutedAmount(),
                  null,
                  status);

      Supplier<StopOrder> stopOrderCreator =
          () ->
              new StopOrder(
                  orderType,
                  order.getOriginalAmount(),
                  currencyPair,
                  String.valueOf(order.getId()),
                  timestamp,
                  order.getPrice(),
                  null,
                  order.getAvgExecutionPrice(),
                  order.getExecutedAmount(),
                  status);

      LimitOrder limitOrder = null;
      StopOrder stopOrder = null;
      MarketOrder marketOrder = null;

      Optional<BitfinexOrderType> bitfinexOrderType =
          Arrays.stream(BitfinexOrderType.values())
              .filter(v -> v.getValue().equals(order.getType()))
              .findFirst();

      if (bitfinexOrderType.isPresent()) {
        switch (bitfinexOrderType.get()) {
          case FILL_OR_KILL:
            limitOrder = limitOrderCreator.get();
            limitOrder.addOrderFlag(BitfinexOrderFlags.FILL_OR_KILL);
            break;
          case MARGIN_FILL_OR_KILL:
            limitOrder = limitOrderCreator.get();
            limitOrder.addOrderFlag(BitfinexOrderFlags.FILL_OR_KILL);
            limitOrder.addOrderFlag(BitfinexOrderFlags.MARGIN);
            break;
          case MARGIN_LIMIT:
            limitOrder = limitOrderCreator.get();
            limitOrder.addOrderFlag(BitfinexOrderFlags.MARGIN);
            break;
          case MARGIN_STOP:
            stopOrder = stopOrderCreator.get();
            stopOrder.addOrderFlag(BitfinexOrderFlags.STOP);
            stopOrder.addOrderFlag(BitfinexOrderFlags.MARGIN);
            break;
          case MARGIN_STOP_LIMIT:
            stopLimitWarning();
            stopOrder = stopOrderCreator.get();
            stopOrder.addOrderFlag(BitfinexOrderFlags.STOP);
            stopOrder.addOrderFlag(BitfinexOrderFlags.MARGIN);
            break;
          case MARGIN_TRAILING_STOP:
            limitOrder = limitOrderCreator.get();
            limitOrder.addOrderFlag(BitfinexOrderFlags.TRAILING_STOP);
            limitOrder.addOrderFlag(BitfinexOrderFlags.MARGIN);
            break;
          case STOP:
            stopOrder = stopOrderCreator.get();
            stopOrder.addOrderFlag(BitfinexOrderFlags.STOP);
            break;
          case STOP_LIMIT:
            stopLimitWarning();
            stopOrder = stopOrderCreator.get();
            stopOrder.addOrderFlag(BitfinexOrderFlags.STOP);
            break;
          case TRAILING_STOP:
            limitOrder = limitOrderCreator.get();
            limitOrder.addOrderFlag(BitfinexOrderFlags.TRAILING_STOP);
            break;
          case LIMIT:
            limitOrder = limitOrderCreator.get();
            break;
          case MARGIN_MARKET:
          case MARKET:
            marketOrder = marketOrderCreator.get();
            break;
          default:
            log.warn(
                "Unhandled Bitfinex order type [{}]. Defaulting to limit order", order.getType());
            limitOrder = limitOrderCreator.get();
            break;
        }
      } else {
        log.warn("Unknown Bitfinex order type [{}]. Defaulting to limit order", order.getType());
        limitOrder = limitOrderCreator.get();
      }

      if (limitOrder != null) {
        limitOrders.add(limitOrder);
      } else if (stopOrder != null) {
        hiddenOrders.add(stopOrder);
      } else if (marketOrder != null) {
        hiddenOrders.add(marketOrder);
      }
    }

    return new OpenOrders(limitOrders, hiddenOrders);
  }

  private void stopLimitWarning() {
    if (warnedStopLimit.compareAndSet(false, true)) {
      log.warn(
          "Found a stop-limit order. Bitfinex v1 API does not return limit prices for stop-limit "
              + "orders so these are returned as stop-at-market orders. This warning will only appear "
              + "once.");
    }
  }

  public UserTrades adaptTradeHistory(BitfinexTradeResponse[] trades, String symbol) {

    List<UserTrade> pastTrades = new ArrayList<>(trades.length);
    CurrencyPair currencyPair = adaptCurrencyPair(symbol);

    for (BitfinexTradeResponse trade : trades) {
      OrderType orderType = trade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      Date timestamp = convertBigDecimalTimestampToDate(trade.getTimestamp());
      final BigDecimal fee = trade.getFeeAmount() == null ? null : trade.getFeeAmount().negate();
      pastTrades.add(
          UserTrade.builder()
              .type(orderType)
              .originalAmount(trade.getAmount())
              .instrument(currencyPair)
              .price(trade.getPrice())
              .timestamp(timestamp)
              .id(trade.getTradeId())
              .orderId(trade.getOrderId())
              .feeAmount(fee)
              .feeCurrency(Currency.getInstance(trade.getFeeCurrency()))
              .build());
    }

    return new UserTrades(pastTrades, TradeSortType.SortByTimestamp);
  }

  public UserTrades adaptTradeHistoryV2(
      List<org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexTrade> bitfinexTrades) {

    List<UserTrade> pastTrades = new ArrayList<>(bitfinexTrades.size());

    for (org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexTrade bitfinexTrade : bitfinexTrades) {
      OrderType orderType =
          bitfinexTrade.getExecAmount().signum() >= 0 ? OrderType.BID : OrderType.ASK;
      BigDecimal amount =
          bitfinexTrade.getExecAmount().signum() == -1
              ? bitfinexTrade.getExecAmount().negate()
              : bitfinexTrade.getExecAmount();
      final BigDecimal fee =
          bitfinexTrade.getFee() != null ? bitfinexTrade.getFee().negate() : null;
      pastTrades.add(
          UserTrade.builder()
              .type(orderType)
              .originalAmount(amount)
              .instrument(bitfinexTrade.getSymbol())
              .price(bitfinexTrade.getExecPrice())
              .timestamp(toDate(bitfinexTrade.getTimestamp()))
              .id(bitfinexTrade.getId())
              .orderId(bitfinexTrade.getOrderId())
              .feeAmount(fee)
              .feeCurrency(bitfinexTrade.getFeeCurrency())
              .build());
    }

    return new UserTrades(pastTrades, TradeSortType.SortByTimestamp);
  }

  public Date toDate(Instant instant) {
    return Optional.ofNullable(instant).map(Date::from).orElse(null);
  }

  private Date convertBigDecimalTimestampToDate(BigDecimal timestamp) {

    BigDecimal timestampInMillis = timestamp.multiply(new BigDecimal("1000"));
    return new Date(timestampInMillis.longValue());
  }

  public ExchangeMetaData adaptMetaData(
      List<CurrencyPair> currencyPairs, ExchangeMetaData metaData) {

    Map<Instrument, InstrumentMetaData> pairsMap = metaData.getInstruments();
    Map<Currency, CurrencyMetaData> currenciesMap = metaData.getCurrencies();

    // Remove pairs that are no-longer in use
    pairsMap.keySet().retainAll(currencyPairs);

    // Remove currencies that are no-longer in use
    Set<Currency> currencies =
        currencyPairs.stream()
            .flatMap(pair -> Stream.of(pair.getBase(), pair.getCounter()))
            .collect(Collectors.toSet());
    currenciesMap.keySet().retainAll(currencies);

    // Add missing pairs and currencies
    for (CurrencyPair c : currencyPairs) {
      if (!pairsMap.containsKey(c)) {
        pairsMap.put(c, null);
      }

      if (!currenciesMap.containsKey(c.getBase())) {
        currenciesMap.put(
            c.getBase(),
            new CurrencyMetaData(
                2,
                null)); // When missing, add default meta-data with scale of 2 (Bitfinex's minimal
        // scale)
      }
      if (!currenciesMap.containsKey(c.getCounter())) {
        currenciesMap.put(c.getCounter(), new CurrencyMetaData(2, null));
      }
    }

    return metaData;
  }

  public List<FundingRecord> adaptFundingHistory(List<BitfinexMovement> bitfinexMovementHistories) {
    final List<FundingRecord> fundingRecords = new ArrayList<>();
    for (BitfinexMovement bitfinexMovement : bitfinexMovementHistories) {

      FundingRecord.Type type =
          bitfinexMovement.getAmount().compareTo(BigDecimal.ZERO) < 0
              ? FundingRecord.Type.WITHDRAWAL
              : FundingRecord.Type.DEPOSIT;

      FundingRecord.Status status =
          FundingRecord.Status.resolveStatus(bitfinexMovement.getStatus());
      if (status == null
          && bitfinexMovement
              .getStatus()
              .equalsIgnoreCase("CANCELED")) // there's a spelling mistake in the protocol
      status = FundingRecord.Status.CANCELLED;

      BigDecimal amount = bitfinexMovement.getAmount().abs();
      BigDecimal fee = bitfinexMovement.getFees().abs();
      if (fee != null && type.isOutflowing()) {
        // The amount reported form Bitfinex on a withdrawal is without the fee, so it has to be
        // added to get the full amount withdrawn from the wallet
        // Deposits don't seem to have fees, but it seems reasonable to assume that the reported
        // value is the full amount added to the wallet
        amount = amount.add(fee);
      }

      FundingRecord fundingRecordEntry =
          FundingRecord.builder()
              .address(bitfinexMovement.getDestinationAddress())
              .date(toDate(bitfinexMovement.getMtsUpdated()))
              .currency(bitfinexMovement.getCurrency())
              .amount(amount)
              .internalId(bitfinexMovement.getId())
              .blockchainTransactionHash(bitfinexMovement.getTransactionId())
              .type(type)
              .status(status)
              .fee(fee)
              .build();

      fundingRecords.add(fundingRecordEntry);
    }
    return fundingRecords;
  }

  public List<FundingRecord> adaptFundingHistory(
      BitfinexDepositWithdrawalHistoryResponse[] bitfinexDepositWithdrawalHistoryResponses) {
    final List<FundingRecord> fundingRecords = new ArrayList<>();
    for (BitfinexDepositWithdrawalHistoryResponse responseEntry :
        bitfinexDepositWithdrawalHistoryResponses) {
      String address = responseEntry.getAddress();
      String description = responseEntry.getDescription();
      Currency currency = Currency.getInstance(responseEntry.getCurrency());

      FundingRecord.Status status = FundingRecord.Status.resolveStatus(responseEntry.getStatus());
      if (status == null
          && responseEntry
              .getStatus()
              .equalsIgnoreCase("CANCELED")) // there's a spelling mistake in the protocol
      status = FundingRecord.Status.CANCELLED;

      String txnId = null;
      if (status == null || !status.equals(FundingRecord.Status.CANCELLED)) {
        /*
        sometimes the description looks like this (with the txn hash in it):
        "description":"a9d387cf5d9df58ff2ac4a338e0f050fd3857cf78d1dbca4f33619dc4ccdac82","address":"1Enx...

        and sometimes like this (with the address in it as well as the txn hash):
        "description":"3AXVnDapuRiAn73pjKe7gukLSx5813oFyn, txid: aa4057486d5f73747167beb9949a0dfe17b5fc630499a66af075abdaf4986987","address":"3AX...

        and sometimes when cancelled
        "description":"3LFVTLFZoDDzLCcLGDDQ7MNkk4YPe26Yva, expired","address":"3LFV...
         */

        String cleanedDescription =
            description.replace(",", "").replace("txid:", "").trim().toLowerCase();

        // Address will only be present for crypto payments. It will be null for all fiat payments
        if (address != null) {
          cleanedDescription = cleanedDescription.replace(address.toLowerCase(), "").trim();
        }

        // check its just some hex characters, and if so lets assume its the txn hash
        if (cleanedDescription.matches("^(0x)?[0-9a-f]+$")) {
          txnId = cleanedDescription;
        }
      }

      FundingRecord fundingRecordEntry =
          FundingRecord.builder()
              .address(address)
              .date(responseEntry.getTimestamp())
              .currency(currency)
              .amount(responseEntry.getAmount())
              .internalId(String.valueOf(responseEntry.getId()))
              .blockchainTransactionHash(txnId)
              .type(responseEntry.getType())
              .status(status)
              .description(description)
              .build();

      fundingRecords.add(fundingRecordEntry);
    }
    return fundingRecords;
  }

  public class OrdersContainer {

    private final long timestamp;
    private final List<LimitOrder> limitOrders;

    /**
     * Constructor
     *
     * @param timestamp The timestamp for the data fetched.
     * @param limitOrders The orders.
     */
    public OrdersContainer(long timestamp, List<LimitOrder> limitOrders) {

      this.timestamp = timestamp;
      this.limitOrders = limitOrders;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public List<LimitOrder> getLimitOrders() {

      return limitOrders;
    }
  }

  ////// v2

  public String adaptCurrencyPairsToTickersParam(Collection<CurrencyPair> currencyPairs) {
    return currencyPairs == null || currencyPairs.isEmpty()
        ? "ALL"
        : currencyPairs.stream()
            .map(BitfinexAdapters::adaptCurrencyPair)
            .collect(Collectors.joining(","));
  }

  public Ticker adaptTicker(
      org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker bitfinexTicker) {

    BigDecimal last = bitfinexTicker.getLastPrice();
    BigDecimal bid = bitfinexTicker.getBid();
    BigDecimal bidSize = bitfinexTicker.getBidSize();
    BigDecimal ask = bitfinexTicker.getAsk();
    BigDecimal askSize = bitfinexTicker.getAskSize();
    BigDecimal high = bitfinexTicker.getHigh();
    BigDecimal low = bitfinexTicker.getLow();
    BigDecimal volume = bitfinexTicker.getVolume();
    BigDecimal percentageChange =
        bitfinexTicker.getDailyChangePerc() != null
            ? bitfinexTicker.getDailyChangePerc().scaleByPowerOfTen(2)
            : null;

    CurrencyPair currencyPair =
        CurrencyPairDeserializer.getCurrencyPairFromString(bitfinexTicker.getSymbol().substring(1));

    CurrencyPair adoptedCurrencyPair = toXChangeCurrencyPair(currencyPair);

    return new Ticker.Builder()
        .instrument(adoptedCurrencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .bidSize(bidSize)
        .askSize(askSize)
        .percentageChange(percentageChange)
        .build();
  }

  public Trade adaptPublicTrade(BitfinexPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType();
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp());
    final String tradeId = String.valueOf(trade.getTradeId());
    return Trade.builder()
        .type(orderType)
        .originalAmount(amount == null ? null : amount.abs())
        .instrument(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  public Trades adaptPublicTrades(BitfinexPublicTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (BitfinexPublicTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptPublicTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker[] adoptBitfinexTickers(
      List<ArrayNode> tickers) throws IOException {

    return tickers.stream()
        .map(
            array -> {
              // tBTCUSD -> traiding pair
              // fUSD -> funding currency
              try {
                String symbol = array.get(0).asText();
                switch (symbol.charAt(0)) {
                  case 't':
                    return mapper.treeToValue(array, BitfinexTickerTraidingPair.class);
                  case 'f':
                    return mapper.treeToValue(array, BitfinexTickerFundingCurrency.class);
                  default:
                    throw new RuntimeException(
                        "Invalid symbol <" + symbol + ">, it must start with 't' or 'f'.");
                }
              } catch (JsonProcessingException e) {
                throw new RuntimeException("Could not convert ticker.", e);
              }
            })
        .toArray(org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker[]::new);
  }

  public AccountInfo toAccountInfo(List<BitfinexWallet> bitfinexWallet) {
    Map<BitfinexWallet.Type, List<Balance>> balancesByWalletType =
        bitfinexWallet.stream()
            .collect(
                Collectors.groupingBy(
                    BitfinexWallet::getWalletType,
                    Collectors.mapping(BitfinexAdapters::toBalance, Collectors.toList())));

    List<Wallet> wallets = new ArrayList<>();
    balancesByWalletType.forEach(
        (key, value) ->
            wallets.add(Builder.from(value).id(key.toString().toLowerCase(Locale.ROOT)).build()));

    return new AccountInfo(wallets);
  }

  public Currency toCurrency(String bitfinexCurrency) {
    return Currency.getInstance(adaptBitfinexCurrency(bitfinexCurrency));
  }

  public Balance toBalance(BitfinexWallet bitfinexWallet) {
    return new Balance.Builder()
        .currency(bitfinexWallet.getCurrency())
        .available(bitfinexWallet.getAvailableBalance())
        .total(bitfinexWallet.getBalance())
        .build();
  }

  public LimitOrder toLimitOrder(BitfinexOrderDetails bitfinexOrderDetails) {
    OrderType orderType =
        bitfinexOrderDetails.getAmount().signum() > 0 ? OrderType.BID : OrderType.ASK;
    return new LimitOrder.Builder(orderType, bitfinexOrderDetails.getCurrencyPair())
        .id(String.valueOf(bitfinexOrderDetails.getId()))
        .userReference(String.valueOf(bitfinexOrderDetails.getClientOrderId()))
        .limitPrice(bitfinexOrderDetails.getPrice())
        .originalAmount(bitfinexOrderDetails.getAmountOrig().abs())
        .cumulativeAmount(bitfinexOrderDetails.getAmount().abs())
        .timestamp(Date.from(bitfinexOrderDetails.getCreatedAt()))
        .orderStatus(bitfinexOrderDetails.getStatus())
        .averagePrice(bitfinexOrderDetails.getPriceAvg())
        .build();
  }

  public Order toOrder(BitfinexOrderDetails bitfinexOrderDetails) {
    OrderType orderType =
        bitfinexOrderDetails.getAmountOrig().signum() > 0 ? OrderType.BID : OrderType.ASK;
    Order.Builder builder;
    switch (bitfinexOrderDetails.getType()) {
      case EXCHANGE_MARKET:
      case MARKET:
        builder = new MarketOrder.Builder(orderType, bitfinexOrderDetails.getCurrencyPair());
        break;
      case EXCHANGE_LIMIT:
      case LIMIT:
        builder =
            new LimitOrder.Builder(orderType, bitfinexOrderDetails.getCurrencyPair())
                .limitPrice(bitfinexOrderDetails.getPrice());
        break;
      default:
        throw new IllegalArgumentException("Can't map " + bitfinexOrderDetails.getType());
    }

    if (bitfinexOrderDetails.getStatus() == OrderStatus.FILLED) {
      // in filled orders original amount always corresponds to asset amount, thus buy orders have
      // to be mapped differently
      if (orderType == OrderType.BID) {
        BigDecimal filledAmount =
            bitfinexOrderDetails.getAmountOrig().multiply(bitfinexOrderDetails.getPriceAvg()).abs();
        builder.originalAmount(filledAmount);
        builder.cumulativeAmount(filledAmount);
      } else {
        builder.originalAmount(bitfinexOrderDetails.getAmountOrig().abs());
        builder.cumulativeAmount(bitfinexOrderDetails.getAmountOrig().abs());
      }
    } else {
      builder.originalAmount(bitfinexOrderDetails.getAmountOrig().abs());
      builder.cumulativeAmount(BigDecimal.ZERO);
    }

    return builder
        .id(String.valueOf(bitfinexOrderDetails.getId()))
        .userReference(String.valueOf(bitfinexOrderDetails.getClientOrderId()))
        .timestamp(Date.from(bitfinexOrderDetails.getCreatedAt()))
        .orderStatus(bitfinexOrderDetails.getStatus())
        .averagePrice(bitfinexOrderDetails.getPriceAvg())
        .build();
  }

  public FundingRecord toFundingRecord(BitfinexLedgerEntry bitfinexLedgerEntry) {
    return FundingRecord.builder()
        .internalId(String.valueOf(bitfinexLedgerEntry.getId()))
        .currency(bitfinexLedgerEntry.getCurrency())
        .type(toFundingRecordType(bitfinexLedgerEntry.getDescription()))
        .balance(bitfinexLedgerEntry.getBalance())
        .amount(bitfinexLedgerEntry.getAmount())
        .date(toDate(bitfinexLedgerEntry.getTimestamp()))
        .description(bitfinexLedgerEntry.getDescription())
        .build();
  }

  public FundingRecord.Type toFundingRecordType(String description) {
    if (StringUtils.isEmpty(description)) {
      return null;
    }

    var value = description.toLowerCase(Locale.ROOT);

    if (value.startsWith("trading fees")) {
      return Type.ORDER_FEE;
    }
    if (value.startsWith("exchange")) {
      return Type.TRADE;
    }
    if (value.startsWith("deposit")) {
      return Type.DEPOSIT;
    }
    if (WITHDRAWAL_PATTERN.matcher(value).matches()) {
      return Type.WITHDRAWAL;
    }
    if (value.startsWith("crypto withdrawal fee")) {
      return Type.WITHDRAWAL_FEE;
    }
    if (SUB_ACCOUNT_TRANSFER_PATTERN.matcher(value).matches()) {
      return Type.INTERNAL_SUB_ACCOUNT_TRANSFER;
    }
    if (WALLET_TRANSFER_PATTERN.matcher(value).matches()) {
      return Type.INTERNAL_WALLET_TRANSFER;
    }
    return null;
  }

  public Long toCategory(Type fundingRecordType) {
    switch (fundingRecordType) {
      case INTERNAL_SUB_ACCOUNT_TRANSFER:
      case INTERNAL_WALLET_TRANSFER:
        return CATEGORY_TRANSFER;
      default:
        return null;
    }
  }

  public OpenPosition toOpenPosition(BitfinexPosition bitfinexPosition) {
    return OpenPosition.builder()
        .instrument(new FuturesContract(bitfinexPosition.getCurrencyPair(), "PERP"))
        .id(bitfinexPosition.getPositionId())
        .type(bitfinexPosition.getType())
        .marginMode(MarginMode.CROSS)
        .size(bitfinexPosition.getAmount().abs())
        .price(bitfinexPosition.getBasePrice())
        .liquidationPrice(bitfinexPosition.getPriceLiq())
        .unRealisedPnl(bitfinexPosition.getPl())
        .createdAt(bitfinexPosition.getCreatedAt())
        .updatedAt(bitfinexPosition.getUpdatedAt())
        .build();
  }
}
