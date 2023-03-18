package org.knowm.xchange.krakenfutures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.krakenfutures.dto.account.KrakenFuturesAccountInfo;
import org.knowm.xchange.krakenfutures.dto.account.KrakenFuturesAccounts;
import org.knowm.xchange.krakenfutures.dto.marketData.*;
import org.knowm.xchange.krakenfutures.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderStatus;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesAdapters {

  private static final String MULTI_COLLATERAL_PRODUCTS = "pf_";
  private static final String ACCOUNT_TYPE = "multiCollateralMarginAccount";

  public static Ticker adaptTicker(
          KrakenFuturesTicker krakenFuturesTicker, Instrument instrument) {

    if (krakenFuturesTicker != null) {
      Ticker.Builder builder = new Ticker.Builder();

      builder.ask(krakenFuturesTicker.getAsk());
      builder.bid(krakenFuturesTicker.getBid());
      builder.last(krakenFuturesTicker.getLast());
      builder.instrument(instrument);
      builder.low(krakenFuturesTicker.getLow24H());
      builder.high(krakenFuturesTicker.getHigh24H());
      builder.volume(krakenFuturesTicker.getVol24H());
      builder.quoteVolume(krakenFuturesTicker.getVol24H().multiply(krakenFuturesTicker.getLast()));
      builder.timestamp(krakenFuturesTicker.getLastTime());

      return builder.build();
    }
    return null;
  }

  /** AccountInfo only for the MultiCollateral account is implemented*/
  public static AccountInfo adaptAccounts(KrakenFuturesAccounts krakenFuturesAccounts, KrakenFuturesOpenPositions krakenFuturesOpenPositions) {
    List<Balance> balances = new ArrayList<>();
    BigDecimal collateralValue = BigDecimal.ZERO;

    for (Entry<String, KrakenFuturesAccountInfo> krakenFuturesAccountInfo : krakenFuturesAccounts.getAccounts().entrySet()) {
      if(krakenFuturesAccountInfo.getValue().getType().equals(ACCOUNT_TYPE)){
        for (Entry<String, KrakenFuturesAccountInfo.KrakenFuturesCurrency> krakenFuturesCurrencyEntry : krakenFuturesAccountInfo.getValue().getCurrencies().entrySet()) {
          balances.add(new Balance.Builder()
                  .currency(new Currency(krakenFuturesCurrencyEntry.getKey()))
                  .total(krakenFuturesCurrencyEntry.getValue().getAvailable())
                  .build());
        }
        collateralValue = krakenFuturesAccountInfo.getValue().getCollateralValue();
      }
    }

    BigDecimal totalOpenPositionsUsdValue = BigDecimal.ZERO;

    for (KrakenFuturesOpenPosition openPosition : krakenFuturesOpenPositions.getOpenPositions()) {
        totalOpenPositionsUsdValue = totalOpenPositionsUsdValue.add(openPosition.getPrice().multiply(openPosition.getSize()));
    }

    return new AccountInfo("multiCollateralMarginAccount", new Wallet.Builder()
            .features(Collections.singleton(Wallet.WalletFeature.FUTURES_TRADING))
            .balances(balances)
            .currentLeverage((totalOpenPositionsUsdValue.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : totalOpenPositionsUsdValue.divide(collateralValue, 3, RoundingMode.HALF_EVEN))
            .build());
  }

  public static OrderType adaptOrderType(KrakenFuturesOrderSide krakenFuturesOrderType) {
    return (krakenFuturesOrderType.equals(KrakenFuturesOrderSide.buy)) ? OrderType.BID : OrderType.ASK;
  }

  public static LimitOrder adaptLimitOrder(KrakenFuturesOpenOrder ord) {
    return new LimitOrder.Builder(adaptOrderType(ord.getSide()),adaptInstrument(ord.getSymbol()))
            .originalAmount(ord.getFilledSize().add(ord.getUnfilledSize()))
            .id(ord.getOrderId())
            .timestamp(ord.getReceivedTime())
            .limitPrice(ord.getLimitPrice())
            .orderStatus(adaptOrderStatus(ord.getStatus()))
            .remainingAmount(ord.getUnfilledSize())
            .flag((ord.isReduceOnly()) ? KrakenFuturesOrderFlags.REDUCE_ONLY : null)
            .build();
  }

  public static StopOrder adaptStopOrder(KrakenFuturesOpenOrder ord) {
    return new StopOrder.Builder(adaptOrderType(ord.getSide()),adaptInstrument(ord.getSymbol()))
            .originalAmount(ord.getFilledSize().add(ord.getUnfilledSize()))
            .id(ord.getOrderId())
            .timestamp(ord.getReceivedTime())
            .limitPrice(ord.getLimitPrice())
            .intention((ord.getOrderType().equals(KrakenFuturesOrderType.take_profit)) ? StopOrder.Intention.TAKE_PROFIT : StopOrder.Intention.STOP_LOSS)
            .orderStatus(adaptOrderStatus(ord.getStatus()))
            .remainingAmount(ord.getUnfilledSize())
            .stopPrice(ord.getStopPrice())
            .flag((ord.isReduceOnly()) ? KrakenFuturesOrderFlags.REDUCE_ONLY : null)
            .build();
  }

  public static OpenOrders adaptOpenOrders(KrakenFuturesOpenOrders orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    List<Order> triggerOrders = new ArrayList<>();

    if (orders != null && orders.isSuccess()) {
      for (KrakenFuturesOpenOrder ord : orders.getOrders()) {
        // how to handle stop-loss orders?
        // ignore anything but a plain limit order for now
        if (ord.getOrderType().equals(KrakenFuturesOrderType.lmt)) {
          limitOrders.add(adaptLimitOrder(ord));
        } else if(ord.getOrderType().equals(KrakenFuturesOrderType.stop)
                || ord.getOrderType().equals(KrakenFuturesOrderType.take_profit)){
          triggerOrders.add(adaptStopOrder(ord));
        }
      }
    }

    return new OpenOrders(limitOrders, triggerOrders);
  }

  public static UserTrade adaptFill(KrakenFuturesFill fill) {
    return new UserTrade.Builder()
        .type(adaptOrderType(fill.getSide()))
        .originalAmount(fill.getSize())
        .instrument(adaptInstrument(fill.getSymbol()))
        .price(fill.getPrice())
        .timestamp(fill.getFillTime())
        .id(fill.getFillId())
        .orderId(fill.getOrderId())
        .build();
  }

  public static UserTrades adaptFills(KrakenFuturesFills krakenFuturesFills) {
    List<UserTrade> trades = new ArrayList<>();

    if (krakenFuturesFills != null && krakenFuturesFills.isSuccess()) {
      for (KrakenFuturesFill fill : krakenFuturesFills.getFills()) {
        trades.add(adaptFill(fill));
      }
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static OrderBook adaptOrderBook(KrakenFuturesOrderBook krakenFuturesOrderBook) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();

    krakenFuturesOrderBook.getBidsAsks().getAsks().forEach(order-> asks.add(new LimitOrder.Builder(OrderType.ASK,krakenFuturesOrderBook.getInstrument())
                    .limitPrice(order.get(0))
                    .originalAmount(order.get(1))
            .build()));
    krakenFuturesOrderBook.getBidsAsks().getBids().forEach(order-> bids.add(new LimitOrder.Builder(OrderType.BID,krakenFuturesOrderBook.getInstrument())
            .limitPrice(order.get(0))
            .originalAmount(order.get(1))
            .build()));

    return new OrderBook(krakenFuturesOrderBook.getServerTime(), asks, bids);
  }

  public static ExchangeMetaData adaptInstrumentsMetaData(KrakenFuturesInstruments krakenFuturesInstruments) {
    Map<Instrument, InstrumentMetaData> instruments = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (KrakenFuturesInstrument instrument : krakenFuturesInstruments.getInstruments()) {
      if(instrument.getSymbol().contains("pf")){
        instruments.put(adaptInstrument(instrument.getSymbol()),new InstrumentMetaData.Builder()
                        .volumeScale(instrument.getVolumeScale())
                        .priceScale(instrument.getTickSize().scale())
                        .priceStepSize(instrument.getTickSize())
                        .minimumAmount(getMinimumAmountFromVolumeScale(instrument.getVolumeScale()))
                .build());
      }
    }

    return new ExchangeMetaData(instruments, currencies,null,null,true);
  }

  public static Instrument adaptInstrument(String symbol) {
      String main_symbol = symbol.replace(MULTI_COLLATERAL_PRODUCTS,"");
      return new FuturesContract(new CurrencyPair(main_symbol.substring(0, main_symbol.length() - 3).replace("xbt","btc")+"/"+main_symbol.substring(main_symbol.length()-3)),"PERP");
  }

  private static BigDecimal getMinimumAmountFromVolumeScale(Integer volumeScale){
    if(volumeScale == 0){
      return BigDecimal.ONE;
    } else {
      StringBuilder sb = new StringBuilder("0.");
      for(int i = 1; i < volumeScale; i++) {
        sb.append("0");
      }
      sb.append("1");
      return new BigDecimal(sb.toString());
    }
  }

  public static String adaptKrakenFuturesSymbol(Instrument instrument) {
    return MULTI_COLLATERAL_PRODUCTS+instrument.getBase().toString().replace("BTC","XBT").toLowerCase()+instrument.getCounter().toString().toLowerCase();
  }

  public static Trades adaptTrades(KrakenFuturesPublicFills krakenFuturesTrades, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    for (KrakenFuturesPublicFill fill : krakenFuturesTrades.getFills()) {
        trades.add(new Trade.Builder()
                        .id(fill.getTradeId())
                        .type(adaptOrderType(fill.getSide()))
                        .price(fill.getPrice())
                        .originalAmount(fill.getSize())
                        .timestamp(fill.getTime())
                        .instrument(instrument)
                .build());
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static FundingRates adaptFundingRates(KrakenFuturesTickers krakenFuturesTickers) {
    List<FundingRate> fundingRates = new ArrayList<>();

    for (KrakenFuturesTicker ticker : krakenFuturesTickers.getTickers()) {
      if(ticker.getSymbol().contains(MULTI_COLLATERAL_PRODUCTS)){
        fundingRates.add(adaptFundingRate(ticker));
      }
    }
    return new FundingRates(fundingRates);
  }

  public static FundingRate adaptFundingRate(KrakenFuturesTicker krakenFuturesTicker){
    LocalDateTime now = LocalDateTime.now();
    // KrakenFutures REST API getTicker returns absoluteValue for fundingRate. Needs to divided by markPrice in order to be the same value that kraken UI displays
    BigDecimal relative1hFundingRate = krakenFuturesTicker.getAbsoluteFundingRate().divide(krakenFuturesTicker.getMarkPrice(),8,RoundingMode.HALF_EVEN);
    return new FundingRate.Builder()
            .fundingRate1h(relative1hFundingRate)
            .fundingRate8h(relative1hFundingRate.multiply(BigDecimal.valueOf(8)))
            .fundingRateDate(Date.from(now.plus(60-now.get(ChronoField.MINUTE_OF_HOUR), ChronoUnit.MINUTES).toInstant(ZoneOffset.UTC)))
            .fundingRateEffectiveInMinutes(60-LocalTime.now().getMinute())
            .instrument(adaptInstrument(krakenFuturesTicker.getSymbol()))
            .build();
  }

  public static Order adaptKrakenFuturesOrder(KrakenFuturesOrdersStatusesResponse.KrakenFuturesOrder krakenFuturesOrder) {
    return new LimitOrder.Builder(adaptOrderType(krakenFuturesOrder.getOrder().getSide()), adaptInstrument(krakenFuturesOrder.getOrder().getSymbol()))
            .limitPrice(krakenFuturesOrder.getOrder().getLimitPrice())
            .originalAmount(krakenFuturesOrder.getOrder().getQuantity())
            .id(krakenFuturesOrder.getOrder().getOrderId())
            .userReference(krakenFuturesOrder.getOrder().getCliOrdId())
            .orderStatus(adaptOrderStatus(krakenFuturesOrder.getOrder().getStatus()))
            .build();
  }

  public static OrderStatus adaptOrderStatus(KrakenFuturesOrderStatus krakenFuturesOrderStatus){
    OrderStatus orderStatus = OrderStatus.UNKNOWN;

    if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.REJECTED)
      || krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.TRIGGER_ACTIVATION_FAILURE)){
      orderStatus = OrderStatus.REJECTED;
    } else if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.CANCELLED)){
      orderStatus = OrderStatus.CANCELED;
    } else if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.ENTERED_BOOK)){
      orderStatus = OrderStatus.NEW;
    } else if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.FULLY_EXECUTED)){
      orderStatus = OrderStatus.FILLED;
    } else if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.TRIGGER_PLACED)
      || krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.untouched)){
      orderStatus = OrderStatus.OPEN;
    } else if(krakenFuturesOrderStatus.equals(KrakenFuturesOrderStatus.partiallyFilled)){
      orderStatus = OrderStatus.PARTIALLY_FILLED;
    }

    return orderStatus;
  }

  public static OpenPositions adaptOpenPositions(KrakenFuturesOpenPositions krakenFuturesOpenPositions) {
    List<OpenPosition> openPositions = new ArrayList<>();

    krakenFuturesOpenPositions.getOpenPositions().forEach(krakenFuturesOpenPosition -> openPositions.add(new OpenPosition.Builder()
                    .instrument(adaptInstrument(krakenFuturesOpenPosition.getSymbol()))
                    .type((krakenFuturesOpenPosition.getSide().equals("long")) ? OpenPosition.Type.LONG : OpenPosition.Type.SHORT)
                    .size(krakenFuturesOpenPosition.getSize())
                    .price(krakenFuturesOpenPosition.getPrice())
            .build()));
    return new OpenPositions(openPositions);
  }
}
