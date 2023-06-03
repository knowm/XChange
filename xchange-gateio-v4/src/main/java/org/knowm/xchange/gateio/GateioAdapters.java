package org.knowm.xchange.gateio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioPublicOrder;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.DateUtils;


@UtilityClass
public final class GateioAdapters {

  public CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }


  public String toString(Instrument instrument) {
    return String.format("%s_%s",
            instrument.getBase().getCurrencyCode(),
            instrument.getCounter().getCurrencyCode())
        .toUpperCase(Locale.ROOT);
  }


  public Ticker adaptTicker(CurrencyPair currencyPair, GateioTicker gateioTicker) {

    BigDecimal ask = gateioTicker.getLowestAsk();
    BigDecimal bid = gateioTicker.getHighestBid();
    BigDecimal last = gateioTicker.getLast();
    BigDecimal low = gateioTicker.getLow24hr();
    BigDecimal high = gateioTicker.getHigh24hr();
    // Looks like gate.io vocabulary is inverted...
    BigDecimal baseVolume = gateioTicker.getQuoteVolume();
    BigDecimal quoteVolume = gateioTicker.getBaseVolume();
    BigDecimal percentageChange = gateioTicker.getPercentChange();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(ask)
        .bid(bid)
        .last(last)
        .low(low)
        .high(high)
        .volume(baseVolume)
        .quoteVolume(quoteVolume)
        .percentageChange(percentageChange)
        .build();
  }


  public OrderBook toOrderBook(GateioOrderBook gateioOrderBook, Instrument instrument) {
    List<LimitOrder> asks = gateioOrderBook.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());


    List<LimitOrder> bids = gateioOrderBook.getBids().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());

    return new OrderBook(Date.from(gateioOrderBook.getGeneratedAt()), asks, bids);
  }


  public LimitOrder adaptOrder(
      GateioPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public List<LimitOrder> adaptOrders(
      List<GateioPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (GateioPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public OrderBook adaptOrderBook(GateioDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        GateioAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids =
        GateioAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public LimitOrder adaptOrder(
      GateioOpenOrder order, Collection<Instrument> currencyPairs) {

    String[] currencyPairSplit = order.getCurrencyPair().split("_");
    CurrencyPair currencyPair = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
    return new LimitOrder(
        order.getType().equals("sell") ? OrderType.ASK : OrderType.BID,
        order.getAmount(),
        currencyPair,
        order.getOrderNumber(),
        null,
        order.getRate());
  }

  public OpenOrders adaptOpenOrders(
      GateioOpenOrders openOrders, Collection<Instrument> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<>();
    for (GateioOpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public OrderType adaptOrderType(GateioOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(GateioOrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public Trade adaptTrade(
      GateioTradeHistory.GateioPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade.Builder()
        .type(orderType)
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(timestamp)
        .id(trade.getTradeId())
        .build();
  }

  public Trades adaptTrades(GateioTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();
    long lastTradeId = 0;
    for (GateioTradeHistory.GateioPublicTrade trade : tradeHistory.getTrades()) {
      String tradeIdString = trade.getTradeId();
      if (!tradeIdString.isEmpty()) {
        long tradeId = Long.valueOf(tradeIdString);
        if (tradeId > lastTradeId) {
          lastTradeId = tradeId;
        }
      }
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, lastTradeId, TradeSortType.SortByTimestamp);
  }


  public InstrumentMetaData toInstrumentMetaData(GateioCurrencyPairDetails gateioCurrencyPairDetails) {
    return new InstrumentMetaData.Builder()
        .tradingFee(gateioCurrencyPairDetails.getFee())
        .minimumAmount(gateioCurrencyPairDetails.getMinAssetAmount())
        .counterMinimumAmount(gateioCurrencyPairDetails.getMinQuoteAmount())
        .volumeScale(gateioCurrencyPairDetails.getAssetScale())
        .priceScale(gateioCurrencyPairDetails.getQuoteScale())
        .build();
  }


  public UserTrades adaptUserTrades(List<GateioTrade> userTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (GateioTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public UserTrade adaptUserTrade(GateioTrade gateioTrade) {

    OrderType orderType = adaptOrderType(gateioTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(gateioTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(gateioTrade.getPair());

    return new UserTrade.Builder()
        .type(orderType)
        .originalAmount(gateioTrade.getAmount())
        .currencyPair(currencyPair)
        .price(gateioTrade.getRate())
        .timestamp(timestamp)
        .id(gateioTrade.getTradeID())
        .orderId(gateioTrade.getOrderNumber())
        .build();
  }

  public List<FundingRecord> adaptDepositsWithdrawals(
      GateioDepositsWithdrawals depositsWithdrawals) {
    List<FundingRecord> result = new ArrayList<>();

    depositsWithdrawals
        .getDeposits()
        .forEach(
            d -> {
              FundingRecord r =
                  new FundingRecord(
                      d.address,
                      d.getTimestamp(),
                      Currency.getInstance(d.currency),
                      d.amount,
                      d.id,
                      d.txid,
                      FundingRecord.Type.DEPOSIT,
                      status(d.status),
                      null,
                      null,
                      null);
              result.add(r);
            });
    depositsWithdrawals
        .getWithdraws()
        .forEach(
            w -> {
              FundingRecord r =
                  new FundingRecord(
                      w.address,
                      w.getTimestamp(),
                      Currency.getInstance(w.currency),
                      w.amount,
                      w.id,
                      w.txid,
                      FundingRecord.Type.WITHDRAWAL,
                      status(w.status),
                      null,
                      null,
                      null);
              result.add(r);
            });

    return result;
  }

  private FundingRecord.Status status(String gateioStatus) {
    switch (gateioStatus) {
      case "DONE":
        return Status.COMPLETE;
      default:
        return Status.PROCESSING; // @TODO which statusses are possible at gate.io?
    }
  }
  
  
  public String toString(OrderStatus orderStatus) {
    switch (orderStatus) {
      case OPEN:
        return "open";
      case CLOSED:
        return "finished";
      default:
        throw new IllegalArgumentException("Can't map " + orderStatus);
    }
  }


  public GateioOrder toGateioOrder(MarketOrder marketOrder) {
    GateioOrder gateioOrder = GateioOrder.builder()
        .currencyPair(toString(marketOrder.getInstrument()))
        .side(toString(marketOrder.getType()))
        .clientOrderId(marketOrder.getUserReference())
        .account("spot")
        .type("market")
        .timeInForce("ioc")
        .amount(marketOrder.getOriginalAmount())
        .build();
    return gateioOrder;
  }


  public String toString(OrderType orderType) {
    switch (orderType) {
      case BID:
        return "buy";
      case ASK:
        return "sell";
      default:
        throw new IllegalArgumentException("Can't map " + orderType);
    }

  }
}
