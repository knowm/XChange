package org.knowm.xchange.bitmex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderDescription;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderResponse;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderStatus;
import org.knowm.xchange.bitmex.dto.trade.BitmexPrivateExecution;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

@Slf4j
@UtilityClass
public class BitmexAdapters {

  private final Map<String, Instrument> SYMBOL_TO_INSTRUMENT = new HashMap<>();
  private final Map<Instrument, String> INSTRUMENT_TO_SYMBOL = new HashMap<>();
  private final BigDecimal SATOSHIS_BY_BTC = BigDecimal.valueOf(100_000_000L);

  public void putSymbolMapping(String symbol, Instrument instrument) {
    SYMBOL_TO_INSTRUMENT.put(symbol, instrument);
    INSTRUMENT_TO_SYMBOL.put(instrument, symbol);
  }

  public OrderBook toOrderBook(List<BitmexPublicOrder> bitmexPublicOrders) {
    Map<OrderType, List<LimitOrder>> orders = bitmexPublicOrders.stream()
        .map(BitmexAdapters::toLimitOrder)
        .collect(Collectors.groupingBy(Order::getType));

    return new OrderBook(null, orders.get(OrderType.ASK), orders.get(OrderType.BID));
  }

  public Trades adaptTrades(List<BitmexPublicTrade> trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.size());
    for (int i = 0; i < trades.size(); i++) {
      BitmexPublicTrade trade = trades.get(i);
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    long lastTid = trades.size() > 0 ? (trades.get(0).getTime().getTime()) : 0;
    // long lastTid = 0L;
    return new Trades(tradeList, lastTid, TradeSortType.SortByTimestamp);
  }

  public LimitOrder toLimitOrder(BitmexPublicOrder order) {
    return new LimitOrder(order.getOrderType(), order.getSize(), order.getInstrument(),
        order.getId(), toDate(order.getUpdatedAt()), order.getPrice());
  }

  public LimitOrder adaptOrder(BitmexPrivateOrder rawOrder) {
    OrderType type = rawOrder.getSide() == BitmexSide.BUY ? OrderType.BID : OrderType.ASK;

    Instrument instrument = toInstrument(rawOrder.getSymbol());

    return new LimitOrder(
        type,
        rawOrder.getVolume(),
        instrument,
        rawOrder.getId(),
        rawOrder.getTimestamp(),
        rawOrder.getPrice(),
        rawOrder.getAvgPx(),
        rawOrder.getCumQty(),
        null,
        BitmexAdapters.adaptOrderStatus(rawOrder.getOrderStatus()));
  }

  public Ticker toTicker(BitmexTicker bitmexTicker) {
    if (bitmexTicker == null) {
      return null;
    }

    BigDecimal percentageChange =
        bitmexTicker.getLastChangePcnt() != null
            ? bitmexTicker.getLastChangePcnt().scaleByPowerOfTen(2)
            : null;

    return new Ticker.Builder()
        .instrument(bitmexTicker.getInstrument())
        .open(bitmexTicker.getOpenValue())
        .last(bitmexTicker.getLastPrice())
        .bid(bitmexTicker.getBidPrice())
        .ask(bitmexTicker.getAskPrice())
        .high(bitmexTicker.getHighPrice())
        .low(bitmexTicker.getLowPrice())
        .vwap(bitmexTicker.getVwap())
        .volume(bitmexTicker.getAssetVolume24h())
        .quoteVolume(bitmexTicker.getQuoteVolume24h())
        .timestamp(toDate(bitmexTicker.getTimestamp()))
        .percentageChange(percentageChange)
        .build();
  }

  public Trade adaptTrade(BitmexPublicTrade bitmexPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(bitmexPublicTrade.getSide());
    BigDecimal originalAmount = bitmexPublicTrade.getSize();
    Date timestamp = bitmexPublicTrade.getTime();
    // Date timestamp = adaptTimestamp(bitmexPublicTrade.getTime());
    // new Date((long) (bitmexPublicTrade.getTime()));

    return new Trade.Builder()
        .type(type)
        .originalAmount(originalAmount)
        .currencyPair(currencyPair)
        .price(bitmexPublicTrade.getPrice())
        .timestamp(timestamp)
        .id(String.valueOf(timestamp.getTime()))
        .build();
  }

  public Wallet adaptWallet(Map<String, BigDecimal> bitmexWallet) {

    List<Balance> balances = new ArrayList<>(bitmexWallet.size());
    for (Entry<String, BigDecimal> balancePair : bitmexWallet.entrySet()) {
      Currency currency = new Currency(balancePair.getKey());
      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return Wallet.Builder.from(balances).build();
  }

  public OpenOrders adaptOpenOrders(Map<String, BitmexOrder> bitmexOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Entry<String, BitmexOrder> bitmexOrderEntry : bitmexOrders.entrySet()) {
      BitmexOrder bitmexOrder = bitmexOrderEntry.getValue();
      BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();

      if (!"limit".equals(orderDescription.getOrderType().toString())) {
        // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
        // ignore anything but a plain limit order for now
        continue;
      }

      limitOrders.add(adaptLimitOrder(bitmexOrder, bitmexOrderEntry.getKey()));
    }
    return new OpenOrders(limitOrders);
  }

  public LimitOrder adaptLimitOrder(BitmexOrder bitmexOrder, String id) {

    BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();
    OrderType type = adaptOrderType(orderDescription.getType());

    BigDecimal originalAmount = bitmexOrder.getVolume();
    BigDecimal filledAmount = bitmexOrder.getVolumeExecuted();
    Instrument instrument = toInstrument(orderDescription.getAssetPair());
    Date timestamp = new Date((long) (bitmexOrder.getOpenTimestamp() * 1000L));

    OrderStatus status = adaptOrderStatus(bitmexOrder.getStatus());

    if (status == OrderStatus.NEW
        && filledAmount.compareTo(BigDecimal.ZERO) > 0
        && filledAmount.compareTo(originalAmount) < 0) {
      status = OrderStatus.PARTIALLY_FILLED;
    }

    return new LimitOrder(
        type,
        originalAmount,
        instrument,
        id,
        timestamp,
        orderDescription.getPrice(),
        orderDescription.getPrice(),
        filledAmount,
        bitmexOrder.getFee(),
        status);
  }

  public OrderType adaptOrderType(BitmexSide bitmexType) {
    return bitmexType == null
        ? null
        : bitmexType.equals(BitmexSide.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public String adaptOrderId(BitmexOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }

  public OrderStatus adaptOrderStatus(BitmexOrderStatus status) {

    switch (status) {
      case PENDING:
        return OrderStatus.PENDING_NEW;
      case OPEN:
        return OrderStatus.NEW;
      case CLOSED:
        return OrderStatus.FILLED;
      case CANCELED:
        return OrderStatus.CANCELED;
      case EXPIRED:
        return OrderStatus.EXPIRED;
      case REJECTED:
        return OrderStatus.REJECTED;
      default:
        return null;
    }
  }

  public OrderStatus adaptOrderStatus(BitmexPrivateOrder.OrderStatus status) {
    switch (status) {
      case New:
        return OrderStatus.NEW;
      case PartiallyFilled:
        return OrderStatus.PARTIALLY_FILLED;
      case Filled:
        return OrderStatus.FILLED;
      case Canceled:
        return OrderStatus.CANCELED;
      case Rejected:
        return OrderStatus.REJECTED;
      default:
        return null;
    }
  }

  public String toString(Currency currency) {
    // bitmex seems to use a lowercase 't' in XBT
    // can test this here - https://testnet.bitmex.com/api/explorer/#!/User/User_getDepositAddress
    // uppercase 'T' will return 'Unknown currency code'
    if (currency.getCurrencyCode().equals("BTC") || currency.getCurrencyCode().equals("XBT")) {
      return "XBt";
    }

    return currency.getCurrencyCode();
  }

  public Currency toCurrency(String currencyCode) {
    if (currencyCode.equalsIgnoreCase("XBt")) {
      return Currency.BTC;
    }

    return Currency.getInstance(currencyCode);
  }

  public String toString(Instrument instrument) {
    if (instrument == null) {
      return null;
    }
    String result = INSTRUMENT_TO_SYMBOL.get(instrument);

    // try to guess if no mapping found
    if (result == null) {
      log.warn("No symbol mapping found {}. Will guess...", instrument);
      if (instrument instanceof CurrencyPair) {
        result = String.format("%s_%s", instrument.getBase(), instrument.getCounter());
        log.warn("Guessed {}", result);
      }
    }

    return result;
  }

  public Instrument toInstrument(String bitmexSymbol) {
    if (bitmexSymbol == null) {
      return null;
    }
    Instrument result = SYMBOL_TO_INSTRUMENT.get(bitmexSymbol);

    // try to guess if no mapping found
    if (result == null) {
      log.warn("No symbol mapping found {}. Will guess...", bitmexSymbol);
      if (bitmexSymbol.contains("_")) {
        String[] parsed = bitmexSymbol.split("_");
        result = new CurrencyPair(parsed[0], parsed[1]);
        log.warn("Guessed {}", result);
      }
    }

    return result;
  }

  public UserTrade adoptUserTrade(BitmexPrivateExecution exec) {
    Instrument instrument = toInstrument(exec.symbol);
    // the "lastQty" parameter is in the USD currency for ???/USD pairs
    OrderType orderType = convertType(exec.side);
    return orderType == null
        ? null
        : UserTrade.builder()
            .id(exec.execID)
            .orderId(exec.orderID)
            .instrument(instrument)
            .originalAmount(exec.lastQty)
            .price(exec.lastPx)
            .feeAmount(exec.execComm.divide(SATOSHIS_BY_BTC, MathContext.DECIMAL32))
            .feeCurrency(Currency.XBT)
            .timestamp(exec.timestamp)
            .type(orderType)
            .orderUserReference(exec.clOrdID)
            .build();
  }

  private OrderType convertType(String side) {
    switch (side) {
      case "Buy":
        return OrderType.BID;
      case "Sell":
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public FundingRecord adaptFundingRecord(BitmexWalletTransaction walletTransaction) {
    return new FundingRecord(
        walletTransaction.getAddress(),
        walletTransaction.getTransactTime(),
        toCurrency(walletTransaction.getCurrency()),
        walletTransaction.getAmount().abs(),
        walletTransaction.getTransactID(),
        walletTransaction.getTx(),
        adaptFundingRecordtype(walletTransaction),
        adaptFundingRecordStatus(walletTransaction.getTransactStatus()),
        walletTransaction.getWalletBalance(),
        walletTransaction.getFee(),
        walletTransaction.getText());
  }

  private FundingRecord.Type adaptFundingRecordtype(
      final BitmexWalletTransaction walletTransaction) {

    String type = walletTransaction.getTransactType();
    if (type.equalsIgnoreCase("Deposit")) {
      return FundingRecord.Type.DEPOSIT;
    } else if (type.equalsIgnoreCase("Withdrawal")) {
      return FundingRecord.Type.WITHDRAWAL;
    } else if (type.equalsIgnoreCase("RealisedPNL") || type.equalsIgnoreCase("UnrealisedPNL")) {
      // 'RealisedPNL' will always have transactStatus = Completed whereas 'UnrealisedPNL' will
      // always be transactStatus = Pending
      if (walletTransaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
        return FundingRecord.Type.REALISED_PROFIT;
      } else if (walletTransaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
        return FundingRecord.Type.REALISED_LOSS;
      }
    }

    throw new ExchangeException("Unknown FundingRecord.Type");
  }

  private FundingRecord.Status adaptFundingRecordStatus(final String transactStatus) {
    return FundingRecord.Status.resolveStatus(transactStatus);
  }

  public Instrument toInstrument(BitmexTicker bitmexTicker) {
    if (bitmexTicker == null) {
      return null;
    }

    CurrencyPair currencyPair = new CurrencyPair(bitmexTicker.getUnderlying(), bitmexTicker.getQuoteCurrency());
    switch (bitmexTicker.getSymbolType()) {
      case SPOT:
        return currencyPair;
      case FUTURES:
        return new FuturesContract(currencyPair, bitmexTicker.getExpirationInfo().getFuturesCode());
      case PERPETUALS:
        return new FuturesContract(currencyPair, "PERP");
      case UNKNOWN:
      default:
        return null;
    }
  }

  public InstrumentMetaData toInstrumentMetaData(BitmexTicker bitmexTicker) {
    BigDecimal assetMultiplier = bitmexTicker.getUnderlyingToPositionMultiplier();

    BigDecimal minAssetAmount = null;
    if (bitmexTicker.getLotSize() != null && assetMultiplier != null && assetMultiplier.signum() != 0) {
      minAssetAmount = bitmexTicker.getLotSize().divide(assetMultiplier, MathContext.DECIMAL32);
    }

    BigDecimal maxAssetAmount = null;
    if (bitmexTicker.getMaxOrderQty() != null && assetMultiplier != null && assetMultiplier.signum() != 0) {
      maxAssetAmount = bitmexTicker.getMaxOrderQty().divide(assetMultiplier, MathContext.DECIMAL32);
    }

    return new InstrumentMetaData.Builder()
        .tradingFee(bitmexTicker.getTakerFee())
        .minimumAmount(minAssetAmount)
        .maximumAmount(maxAssetAmount)
        .priceScale(Optional.ofNullable(bitmexTicker.getTickSize()).map(BigDecimal::scale).orElse(null))
        .volumeScale(Optional.ofNullable(minAssetAmount).map(BigDecimal::scale).orElse(null))
        .priceStepSize(bitmexTicker.getTickSize())
        .marketOrderEnabled(true)
        .build();
  }

  public String toFuturesCode(ZonedDateTime zonedDateTime) {
    if (zonedDateTime == null) {
      return null;
    }
    // get short year
    String futuresCode = DateTimeFormatter.ofPattern("yy").format(zonedDateTime);
    switch (zonedDateTime.getMonth()) {
      case JANUARY:
        return "F" + futuresCode;
      case FEBRUARY:
        return "G" + futuresCode;
      case MARCH:
        return "H" + futuresCode;
      case APRIL:
        return "J" + futuresCode;
      case MAY:
        return "K" + futuresCode;
      case JUNE:
        return "M" + futuresCode;
      case JULY:
        return "N" + futuresCode;
      case AUGUST:
        return "Q" + futuresCode;
      case SEPTEMBER:
        return "U" + futuresCode;
      case OCTOBER:
        return "V" + futuresCode;
      case NOVEMBER:
        return "X" + futuresCode;
      case DECEMBER:
        return "Z" + futuresCode;
      default:
        return null;
    }
  }

  public BitmexSide toBitmexSide(OrderType type) {
    return type == OrderType.ASK ? BitmexSide.SELL : BitmexSide.BUY;
  }

  public Date toDate (ZonedDateTime zonedDateTime) {
    return Optional.ofNullable(zonedDateTime)
        .map(ChronoZonedDateTime::toInstant)
        .map(Date::from)
        .orElse(null);
  }

}
