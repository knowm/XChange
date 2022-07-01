package org.knowm.xchange.deribit.v2;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.Direction;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import org.knowm.xchange.deribit.v2.dto.account.Position;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrade;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.deribit.v2.dto.trade.OrderState;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.DerivativeMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

public class DeribitAdapters {
  private static final String IMPLIED_COUNTER = "USD";
  private static final String PERPETUAL = "PERPETUAL";
  private static final int CURRENCY_SCALE = 8;
  private static final ThreadLocal<DateFormat> DATE_PARSER =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("ddMMMyy"));

  public static String adaptInstrumentName(Instrument instrument) {
    if (instrument instanceof FuturesContract) {
      return adaptInstrumentName((FuturesContract) instrument);
    } else if (instrument instanceof OptionsContract) {
      return adaptInstrumentName((OptionsContract) instrument);
    }
    throw new IllegalArgumentException("Unsupported instrument '" + instrument.toString() + "'");
  }

  public static String adaptInstrumentName(FuturesContract future) {
    return future.getCurrencyPair().base
        + "-"
        + (future.getPrompt() == null ? PERPETUAL : (future.getPrompt()));
  }

  public static String adaptInstrumentName(OptionsContract option) {
    String[] parts = option.toString().split("/");
    if (parts.length != 5) {
      throw new IllegalArgumentException("Could not adapt instrument name from '" + option + "'");
    }
    return option.getCurrencyPair().base
        + "-"
        + formatDate(option.getExpireDate())
        + "-"
        + parts[3]
        + "-"
        + parts[4];
  }

  public static Ticker adaptTicker(DeribitTicker deribitTicker) {
    return new Ticker.Builder()
        .instrument(adaptInstrument(deribitTicker.getInstrumentName()))
        .open(deribitTicker.getOpenInterest())
        .last(deribitTicker.getLastPrice())
        .bid(deribitTicker.getBestBidPrice())
        .ask(deribitTicker.getBestAskPrice())
        .high(deribitTicker.getMaxPrice())
        .low(deribitTicker.getMinPrice())
        .volume(deribitTicker.getStats().getVolume())
        .bidSize(deribitTicker.getBestBidAmount())
        .askSize(deribitTicker.getBestAskAmount())
        .timestamp(deribitTicker.getTimestamp())
        .build();
  }

  public static OrderBook adaptOrderBook(DeribitOrderBook deribitOrderBook) {
    Instrument instrument = adaptInstrument(deribitOrderBook.getInstrumentName());
    List<LimitOrder> bids =
        adaptOrdersList(deribitOrderBook.getBids(), Order.OrderType.BID, instrument);
    List<LimitOrder> asks =
        adaptOrdersList(deribitOrderBook.getAsks(), Order.OrderType.ASK, instrument);
    return new OrderBook(deribitOrderBook.getTimestamp(), asks, bids);
  }

  /** convert orders map (price -> amount) to a list of limit orders */
  private static List<LimitOrder> adaptOrdersList(
      TreeMap<BigDecimal, BigDecimal> map, Order.OrderType type, Instrument instrument) {
    return map.entrySet().stream()
        .map(e -> new LimitOrder(type, e.getValue(), instrument, null, null, e.getKey()))
        .collect(Collectors.toList());
  }

  public static Trade adaptTrade(DeribitTrade deribitTrade, Instrument instrument) {
    return new Trade.Builder()
        .type(adapt(deribitTrade.getDirection()))
        .originalAmount(deribitTrade.getAmount())
        .instrument(instrument)
        .price(deribitTrade.getPrice())
        .timestamp(deribitTrade.getTimestamp())
        .id(deribitTrade.getTradeId())
        .build();
  }

  public static Trades adaptTrades(DeribitTrades deribitTrades, Instrument instrument) {

    return new Trades(
        deribitTrades.getTrades().stream()
            .map(trade -> adaptTrade(trade, instrument))
            .collect(Collectors.toList()));
  }

  public static OpenOrders adaptOpenOrders(
      List<org.knowm.xchange.deribit.v2.dto.trade.Order> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();

    orders.forEach(
        o -> {
          Order order = DeribitAdapters.adaptOrder(o);
          if (order instanceof LimitOrder) {
            limitOrders.add((LimitOrder) order);
          } else {
            otherOrders.add(order);
          }
        });

    return new OpenOrders(limitOrders, otherOrders);
  }

  public static Order adaptOrder(org.knowm.xchange.deribit.v2.dto.trade.Order order) {
    Order.OrderType type = adapt(order.getDirection());
    Instrument instrument = adaptInstrument(order.getInstrumentName());
    Order.Builder builder;
    if (order.getOrderType().equals(OrderType.market)) {
      builder = new MarketOrder.Builder(type, instrument);
    } else if (order.getOrderType().equals(OrderType.limit)) {
      builder = new LimitOrder.Builder(type, instrument).limitPrice(order.getPrice());
    } else {
      throw new ExchangeException("Unsupported order type: \"" + order.getOrderType() + "\"");
    }
    builder
        .orderStatus(adaptOrderStatus(order.getOrderState()))
        .id(order.getOrderId())
        .userReference(order.getLabel())
        .timestamp(new Date(order.getCreationTimestamp()))
        .averagePrice(order.getAveragePrice())
        .originalAmount(order.getAmount())
        .cumulativeAmount(order.getFilledAmount())
        .fee(order.getCommission());

    return builder.build();
  }

  public static Order.OrderType adapt(Direction direction) {
    switch (direction) {
      case buy:
        return Order.OrderType.BID;
      case sell:
        return Order.OrderType.ASK;
      default:
        throw new RuntimeException("Not supported order direction: " + direction);
    }
  }

  public static Order.OrderStatus adaptOrderStatus(OrderState state) {
    switch (state) {
      case open:
      case untriggered:
        return Order.OrderStatus.OPEN;
      case filled:
        return Order.OrderStatus.FILLED;
      case rejected:
        return Order.OrderStatus.REJECTED;
      case cancelled:
        return Order.OrderStatus.CANCELED;
      case archive:
      default:
        return Order.OrderStatus.UNKNOWN;
    }
  }

  /** Parse errors from HTTP exceptions */
  public static ExchangeException adapt(DeribitException ex) {

    DeribitError error = ex.getError();

    if (error != null
        && error.getClass().equals(DeribitError.class)
        && isNotEmpty(error.getMessage())) {

      int code = error.getCode();
      String msg = error.getMessage();
      String data = error.getData().toString();
      if (isNotEmpty(data)) {
        msg += " - " + data;
      }

      switch (code) {
        case -32602:
          return new CurrencyPairNotValidException(data, ex);
        default:
          return new ExchangeException(msg, ex);
      }
    }
    return new ExchangeException("Operation failed without any error message", ex);
  }

  public static Balance adapt(AccountSummary as) {
    return new Balance(
        Currency.getInstance(as.getCurrency()), as.getBalance(), as.getAvailableFunds());
  }

  public static OpenPosition adapt(Position p) {
    return new OpenPosition.Builder()
        .instrument(adaptInstrument(p.getInstrumentName()))
        .size(p.getSize())
        .price(p.getMarkPrice())
        .build();
  }

  public static CurrencyMetaData adaptMeta(DeribitCurrency currency) {
    return new CurrencyMetaData(CURRENCY_SCALE, currency.getWithdrawalFee());
  }

  public static FuturesContract adaptFuturesContract(DeribitInstrument instrument) {
    CurrencyPair currencyPair =
        new CurrencyPair(instrument.getBaseCurrency(), instrument.getQuoteCurrency());
    String expireDate = PERPETUAL;

    if (!PERPETUAL.equalsIgnoreCase(instrument.getSettlementPeriod())) {
      expireDate = instrument.getExpirationTimestamp().toString();
    }
    return new FuturesContract(currencyPair, expireDate);
  }

  public static OptionsContract adaptOptionsContract(DeribitInstrument instrument) {
    CurrencyPair currencyPair = new CurrencyPair(instrument.getBaseCurrency(), IMPLIED_COUNTER);
    Date expireDate = instrument.getExpirationTimestamp();

    String[] parts = instrument.getInstrumentName().split("-");
    if (parts.length != 4) {
      throw new IllegalArgumentException(
          "Could not parse options contract from '" + instrument.getInstrumentName() + "'");
    }
    BigDecimal strike = new BigDecimal(parts[2]);
    OptionsContract.OptionType type = OptionsContract.OptionType.fromString(parts[3]);
    return new OptionsContract.Builder()
        .currencyPair(currencyPair)
        .expireDate(expireDate)
        .strike(strike)
        .type(type)
        .build();
  }

  public static Instrument adaptInstrument(String instrumentName) {
    String[] parts = instrumentName.split("-");
    if (parts.length == 2) {
      DeribitInstrument future = new DeribitInstrument();
      future.setBaseCurrency(parts[0]);
      future.setQuoteCurrency(IMPLIED_COUNTER);
      if (PERPETUAL.equalsIgnoreCase(parts[1])) {
        future.setSettlementPeriod(PERPETUAL);
      } else {
        try {
          future.setExpirationTimestamp(parseDate(parts[1]).getTime());
        } catch (ParseException e) {
          throw new IllegalArgumentException(
              "Could not adapt instrument from name '" + instrumentName + "'");
        }
      }
      return adaptFuturesContract(future);
    } else if (parts.length >= 3 && PERPETUAL.equalsIgnoreCase(parts[2])) {
      DeribitInstrument future = new DeribitInstrument();
      future.setBaseCurrency(parts[0]);
      future.setQuoteCurrency(parts[1]);
      future.setSettlementPeriod(PERPETUAL);
      return adaptFuturesContract(future);
    } else if (parts.length == 4) {
      DeribitInstrument option = new DeribitInstrument();
      option.setBaseCurrency(parts[0]);
      try {
        option.setExpirationTimestamp(parseDate(parts[1]).getTime());
      } catch (ParseException e) {
        throw new IllegalArgumentException(
            "Could not adapt instrument from name '" + instrumentName + "'");
      }
      option.setInstrumentName(instrumentName);
      return adaptOptionsContract(option);
    }
    throw new IllegalArgumentException(
        "Could not adapt instrument from name '" + instrumentName + "'");
  }

  public static DerivativeMetaData adaptMeta(DeribitInstrument instrument) {
    FeeTier[] feeTiers = {
      new FeeTier(
          BigDecimal.ZERO,
          new Fee(instrument.getMakerCommission(), instrument.getTakerCommission()))
    };
    return new DerivativeMetaData.Builder()
        .tradingFee(instrument.getTakerCommission())
        .feeTiers(feeTiers)
        .minimumAmount(instrument.getMinTradeAmount())
        .amountScale(instrument.getMinTradeAmount().scale())
        .priceScale(instrument.getTickSize().scale())
        .priceStepSize(instrument.getTickSize())
        .build();
  }

  public static UserTrades adaptUserTrades(
      org.knowm.xchange.deribit.v2.dto.trade.UserTrades userTrades) {
    return new UserTrades(
        userTrades.getTrades().stream()
            .map(DeribitAdapters::adaptUserTrade)
            .collect(Collectors.toList()),
        Trades.TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptUserTrade(org.knowm.xchange.deribit.v2.dto.trade.Trade trade) {
    return new UserTrade.Builder()
        .type(adapt(trade.getDirection()))
        .originalAmount(trade.getAmount())
        .instrument(adaptInstrument(trade.getInstrumentName()))
        .price(trade.getPrice())
        .timestamp(trade.getTimestamp())
        .id(trade.getTradeId())
        .orderId(trade.getOrderId())
        .feeAmount(trade.getFee())
        .feeCurrency(new Currency(trade.getFeeCurrency()))
        .orderUserReference(trade.getLabel())
        .build();
  }

  private static Date parseDate(String source) throws ParseException {
    if (!Character.isDigit(source.charAt(1))) {
      source = '0' + source;
    }
    return DATE_PARSER.get().parse(source);
  }

  private static String formatDate(Date date) {
    String str = DATE_PARSER.get().format(date).toUpperCase();
    if (str.charAt(0) == '0') {
      str = str.substring(1);
    }
    return str;
  }
}
