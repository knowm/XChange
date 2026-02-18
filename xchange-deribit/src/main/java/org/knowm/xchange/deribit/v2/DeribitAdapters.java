package org.knowm.xchange.deribit.v2;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.account.DeribitAccountSummary;
import org.knowm.xchange.deribit.v2.dto.account.DeribitPosition;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransactionLog;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrade;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.deribit.v2.dto.trade.DeribitOrder;
import org.knowm.xchange.deribit.v2.dto.trade.DeribitUserTrade;
import org.knowm.xchange.deribit.v2.dto.trade.DeribitUserTrades;
import org.knowm.xchange.deribit.v2.dto.trade.OrderState;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class DeribitAdapters {

  public final Map<String, Instrument> SYMBOL_TO_INSTRUMENT = new HashMap<>();
  private final Map<Instrument, String> INSTRUMENT_TO_SYMBOL = new HashMap<>();

  public void putSymbolMapping(String symbol, Instrument instrument) {
    SYMBOL_TO_INSTRUMENT.put(symbol, instrument);
    INSTRUMENT_TO_SYMBOL.put(instrument, symbol);
  }

  public Ticker adaptTicker(DeribitTicker deribitTicker) {
    return new Ticker.Builder()
        .instrument(toInstrument(deribitTicker.getInstrumentName()))
        .open(deribitTicker.getOpenInterest())
        .last(deribitTicker.getLastPrice())
        .bid(deribitTicker.getBestBidPrice())
        .ask(deribitTicker.getBestAskPrice())
        .high(deribitTicker.getMaxPrice())
        .low(deribitTicker.getMinPrice())
        .volume(deribitTicker.getStats().getVolume())
        .bidSize(deribitTicker.getBestBidAmount())
        .askSize(deribitTicker.getBestAskAmount())
        .timestamp(toDate(deribitTicker.getTimestamp()))
        .build();
  }

  public OrderBook adaptOrderBook(DeribitOrderBook deribitOrderBook) {
    Instrument instrument = toInstrument(deribitOrderBook.getInstrumentName());
    List<LimitOrder> bids =
        adaptOrdersList(deribitOrderBook.getBids(), Order.OrderType.BID, instrument);
    List<LimitOrder> asks =
        adaptOrdersList(deribitOrderBook.getAsks(), Order.OrderType.ASK, instrument);
    return new OrderBook(toDate(deribitOrderBook.getTimestamp()), asks, bids);
  }

  /** convert orders map (price -> amount) to a list of limit orders */
  private List<LimitOrder> adaptOrdersList(
      TreeMap<BigDecimal, BigDecimal> map, Order.OrderType type, Instrument instrument) {
    return map.entrySet().stream()
        .map(e -> new LimitOrder(type, e.getValue(), instrument, null, null, e.getKey()))
        .collect(Collectors.toList());
  }

  public Trade adaptTrade(DeribitTrade deribitTrade, Instrument instrument) {
    return Trade.builder()
        .type(deribitTrade.getOrderSide())
        .originalAmount(deribitTrade.getAmount())
        .instrument(instrument)
        .price(deribitTrade.getPrice())
        .timestamp(toDate(deribitTrade.getTimestamp()))
        .id(deribitTrade.getTradeId())
        .build();
  }

  public Trades adaptTrades(DeribitTrades deribitTrades, Instrument instrument) {

    return new Trades(
        deribitTrades.getTrades().stream()
            .map(trade -> adaptTrade(trade, instrument))
            .collect(Collectors.toList()));
  }

  public OpenOrders adaptOpenOrders(List<DeribitOrder> deribitOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> otherOrders = new ArrayList<>();

    deribitOrders.forEach(
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

  public Order adaptOrder(DeribitOrder deribitOrder) {
    Order.OrderType type = deribitOrder.getOrderSide();
    Instrument instrument = toInstrument(deribitOrder.getInstrumentName());
    Order.Builder builder;
    if (deribitOrder.getOrderType().equals(OrderType.market)) {
      builder = new MarketOrder.Builder(type, instrument);
    } else if (deribitOrder.getOrderType().equals(OrderType.limit)) {
      builder = new LimitOrder.Builder(type, instrument).limitPrice(deribitOrder.getPrice());
    } else {
      throw new ExchangeException(
          "Unsupported deribitOrder type: \"" + deribitOrder.getOrderType() + "\"");
    }
    builder
        .orderStatus(adaptOrderStatus(deribitOrder.getOrderState()))
        .id(deribitOrder.getOrderId())
        .userReference(deribitOrder.getLabel())
        .timestamp(toDate(deribitOrder.getCreatedAt()))
        .averagePrice(deribitOrder.getAveragePrice())
        .originalAmount(deribitOrder.getAmount())
        .cumulativeAmount(deribitOrder.getFilledAmount())
        .fee(deribitOrder.getCommission());

    return builder.build();
  }

  public Order.OrderStatus adaptOrderStatus(OrderState state) {
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
  public ExchangeException adapt(DeribitException ex) {

    DeribitError error = ex.getError();

    if (error != null
        && error.getClass().equals(DeribitError.class)
        && StringUtils.isNotEmpty(error.getMessage())) {

      int code = error.getCode();
      String msg = error.getMessage();
      String data = error.getData().toString();
      if (StringUtils.isNotEmpty(data)) {
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

  public Balance adapt(DeribitAccountSummary deribitAccountSummary) {
    return new Balance(
        deribitAccountSummary.getCurrency(),
        deribitAccountSummary.getBalance(),
        deribitAccountSummary.getAvailableFunds());
  }

  public OpenPosition adapt(DeribitPosition deribitPosition) {
    var size =
        deribitPosition.getSizeCurrency() != null
            ? deribitPosition.getSizeCurrency()
            : deribitPosition.getSize();
    return OpenPosition.builder()
        .instrument(toInstrument(deribitPosition.getInstrumentName()))
        .type(deribitPosition.getPositionType())
        .size(size)
        .marginMode(MarginMode.CROSS)
        .price(deribitPosition.getMarkPrice())
        .liquidationPrice(deribitPosition.getEstimatedLiquidationPrice())
        .unRealisedPnl(deribitPosition.getFloatingProfitLoss())
        .build();
  }

  public CurrencyMetaData adaptMeta(DeribitCurrency currency) {
    return new CurrencyMetaData(currency.getDecimals(), currency.getWithdrawalFee());
  }

  public InstrumentMetaData adaptMeta(DeribitInstrument instrument) {
    FeeTier[] feeTiers = {
      new FeeTier(
          BigDecimal.ZERO,
          new Fee(instrument.getMakerCommission(), instrument.getTakerCommission()))
    };
    return InstrumentMetaData.builder()
        .tradingFee(instrument.getTakerCommission())
        .feeTiers(feeTiers)
        .minimumAmount(instrument.getMinTradeAmount())
        .volumeScale(instrument.getMinTradeAmount().scale())
        .priceScale(instrument.getTickSize().scale())
        .priceStepSize(instrument.getTickSize())
        .build();
  }

  public UserTrades adaptUserTrades(DeribitUserTrades deribitUserTrades) {
    return new UserTrades(
        deribitUserTrades.getTrades().stream()
            .map(DeribitAdapters::adaptUserTrade)
            .collect(Collectors.toList()),
        Trades.TradeSortType.SortByTimestamp);
  }

  public String toString(Instrument instrument) {
    return INSTRUMENT_TO_SYMBOL.get(instrument);
  }

  public Instrument toInstrument(String symbol) {
    return SYMBOL_TO_INSTRUMENT.get(symbol);
  }

  public Currency toCurrency(DeribitCurrency deribitCurrency) {
    return Currency.getInstance(deribitCurrency.getCurrency());
  }

  public Instrument toInstrument(DeribitInstrument deribitInstrument) {
    if (deribitInstrument == null) {
      return null;
    }

    var currencyPair =
        new CurrencyPair(
            deribitInstrument.getBaseCurrency(), deribitInstrument.getCounterCurrency());
    if (deribitInstrument.getKind() == Kind.SPOT) {
      return currencyPair;
    }

    if (deribitInstrument.getKind() == Kind.FUTURES) {
      var prompt = deribitInstrument.getInstrumentName().split("-")[1];
      return new FuturesContract(currencyPair, prompt);
    }

    if (deribitInstrument.getKind() == Kind.OPTIONS) {
      return new OptionsContract(
          currencyPair,
          deribitInstrument.getExpirationTimestamp(),
          deribitInstrument.getStrike(),
          deribitInstrument.getOptionType());
    }

    return null;
  }

  public FundingRecord toFundingRecord(DeribitTransactionLog deribitTransactionLog) {
    return FundingRecord.builder()
        .address(deribitTransactionLog.getAddress())
        .addressTag(deribitTransactionLog.getAddressTag())
        .date(toDate(deribitTransactionLog.getTimestamp()))
        .currency(deribitTransactionLog.getCurrency())
        .amount(
            Optional.ofNullable(deribitTransactionLog.getAmount())
                .orElse(deribitTransactionLog.getChange()))
        .internalId(deribitTransactionLog.getId())
        .blockchainTransactionHash(deribitTransactionLog.getBlockchainTransactionHash())
        .type(toFundingRecordType(deribitTransactionLog))
        .status(Status.COMPLETE)
        .balance(deribitTransactionLog.getBalance())
        .build();
  }

  private Type toFundingRecordType(DeribitTransactionLog deribitTransactionLog) {
    if (deribitTransactionLog == null || deribitTransactionLog.getTransactionType() == null) {
      return null;
    }

    switch (deribitTransactionLog.getTransactionType()) {
      case TRADE:
        return Type.TRADE;
      case DEPOSIT:
        return Type.DEPOSIT;
      case WITHDRAWAL:
        return Type.WITHDRAWAL;
      case TRANSFER:
        return Type.INTERNAL_SUB_ACCOUNT_TRANSFER;
      default:
        if (deribitTransactionLog.getChange() != null) {
          return deribitTransactionLog.getChange().signum() > 0
              ? Type.OTHER_INFLOW
              : Type.OTHER_OUTFLOW;
        }
    }
    return null;
  }

  private UserTrade adaptUserTrade(DeribitUserTrade deribitDeribitUserTrade) {
    return UserTrade.builder()
        .type(deribitDeribitUserTrade.getOrderSide())
        .originalAmount(deribitDeribitUserTrade.getAmount())
        .instrument(toInstrument(deribitDeribitUserTrade.getInstrumentName()))
        .price(deribitDeribitUserTrade.getPrice())
        .timestamp(toDate(deribitDeribitUserTrade.getTimestamp()))
        .id(deribitDeribitUserTrade.getTradeId())
        .orderId(deribitDeribitUserTrade.getOrderId())
        .feeAmount(deribitDeribitUserTrade.getFee())
        .feeCurrency(deribitDeribitUserTrade.getFeeCurrency())
        .orderUserReference(deribitDeribitUserTrade.getLabel())
        .build();
  }

  public Date toDate(Instant instant) {
    return Optional.ofNullable(instant).map(Date::from).orElse(null);
  }
}
