package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitOrderStatus;
import static org.knowm.xchange.bybit.BybitAdapters.convertBybitSymbolToInstrument;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;
import static org.knowm.xchange.bybit.BybitAdapters.getOrderType;

import dto.marketdata.BybitOrderbook;
import dto.marketdata.BybitPublicOrder;
import dto.trade.BybitComplexOrderChanges;
import dto.trade.BybitComplexPositionChanges;
import dto.trade.BybitOrderChangesResponse.BybitOrderChanges;
import dto.trade.BybitOrderFlag;
import dto.trade.BybitPositionChangesResponse.BybitPositionChanges;
import dto.trade.BybitStreamBatchAmendOrdersPayload;
import dto.trade.BybitStreamBatchAmendOrdersPayload.BybitStreamBatchAmendOrderPayload;
import dto.trade.BybitTrade;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRate.FundingRateInterval;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamAdapters {

  public static OrderBook adaptOrderBook(BybitOrderbook bybitOrderBooks, Instrument instrument) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();
    Date timestamp = new Date(bybitOrderBooks.getCts());
    bybitOrderBooks
        .getData()
        .getAsk()
        .forEach(
            bybitAsk ->
                asks.add(adaptOrderBookOrder(bybitAsk, instrument, OrderType.ASK, timestamp)));

    bybitOrderBooks
        .getData()
        .getBid()
        .forEach(
            bybitBid ->
                bids.add(adaptOrderBookOrder(bybitBid, instrument, OrderType.BID, timestamp)));

    return new OrderBook(timestamp, asks, bids);
  }

  public static Trades adaptTrades(List<BybitTrade> bybitTrades, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    bybitTrades.forEach(
        bybitTrade ->
            trades.add(
                Trade.builder()
                    .id(bybitTrade.getTradeId())
                    .instrument(instrument)
                    .originalAmount(bybitTrade.getTradeSize())
                    .price(bybitTrade.getTradePrice())
                    .timestamp(bybitTrade.getTimestamp())
                    .type(getOrderType(bybitTrade.getSide()))
                    .build()));

    return new Trades(trades);
  }

  public static LimitOrder adaptOrderBookOrder(
      BybitPublicOrder bybitPublicOrder,
      Instrument instrument,
      OrderType orderType,
      Date timestamp) {

    return new LimitOrder(
        orderType,
        new BigDecimal(bybitPublicOrder.getSize()),
        instrument,
        "",
        timestamp,
        new BigDecimal(bybitPublicOrder.getPrice()));
  }

  public static List<Order> adaptOrdersChanges(List<BybitOrderChanges> bybitOrderChanges) {
    Date date = new Date();
    List<Order> orders = new ArrayList<>();
    for (BybitOrderChanges bybitOrderChange : bybitOrderChanges) {
      date.setTime(Long.parseLong(bybitOrderChange.getUpdatedTime()));
      Order.OrderType orderType = getOrderType(bybitOrderChange.getSide());
      Order.Builder builder = null;
      switch (bybitOrderChange.getOrderType()) {
        case LIMIT:
          builder =
              new LimitOrder.Builder(
                      orderType,
                      convertBybitSymbolToInstrument(
                          bybitOrderChange.getSymbol(), bybitOrderChange.getCategory()))
                  .limitPrice(new BigDecimal(bybitOrderChange.getPrice()));
          break;
        case MARKET:
          builder =
              new MarketOrder.Builder(
                  orderType,
                  convertBybitSymbolToInstrument(
                      bybitOrderChange.getSymbol(), bybitOrderChange.getCategory()));
          break;
      }
      if (!bybitOrderChange.getAvgPrice().isEmpty()) {
        builder.averagePrice(new BigDecimal(bybitOrderChange.getAvgPrice()));
      }
      builder
          .fee(new BigDecimal(bybitOrderChange.getCumExecFee()))
          .id(bybitOrderChange.getOrderId())
          .orderStatus(adaptBybitOrderStatus(bybitOrderChange.getOrderStatus()))
          .timestamp(date)
          .cumulativeAmount(new BigDecimal(bybitOrderChange.getCumExecQty()))
          .originalAmount(new BigDecimal(bybitOrderChange.getQty()))
          .id(bybitOrderChange.getOrderId())
          .userReference(bybitOrderChange.getOrderLinkId());
      if (!bybitOrderChange.getRejectReason().isEmpty())
        builder.flag(new BybitOrderFlag(bybitOrderChange.getRejectReason()));
      orders.add(builder.build());
    }
    return orders;
  }

  public static OpenPositions adaptPositionChanges(
      List<BybitPositionChanges> bybitPositionChanges) {
    OpenPositions openPositions = new OpenPositions(new ArrayList<>());
    for (BybitPositionChanges position : bybitPositionChanges) {
      OpenPosition.Type type = getPositionType(position);
      BigDecimal liqPrice = getLiqPrice(position);
      if (!position.getLiqPrice().isEmpty()) {
        liqPrice = new BigDecimal(position.getLiqPrice());
      }
      OpenPosition openPosition =
          OpenPosition.builder()
              .instrument(
                  convertBybitSymbolToInstrument(position.getSymbol(), position.getCategory()))
              .type(type)
              .size(new BigDecimal(position.getSize()))
              .price(new BigDecimal(position.getEntryPrice()))
              .liquidationPrice(liqPrice)
              .unRealisedPnl(new BigDecimal(position.getUnrealisedPnl()))
              .build();
      openPositions.getOpenPositions().add(openPosition);
    }
    return openPositions;
  }

  private static OpenPosition.Type getPositionType(BybitPositionChanges position) {
    if (!position.getSide().isEmpty()) {
      return position.getSide().equals("Buy") ? Type.LONG : Type.SHORT;
    }
    return null;
  }

  private static BigDecimal getLiqPrice(BybitPositionChanges position) {
    if (!position.getLiqPrice().isEmpty()) {
      return new BigDecimal(position.getLiqPrice());
    }
    return null;
  }

  public static List<BybitComplexPositionChanges> adaptComplexPositionChanges(
      List<BybitPositionChanges> data) {
    List<BybitComplexPositionChanges> result = new ArrayList<>();
    for (BybitPositionChanges position : data) {
      OpenPosition.Type type = getPositionType(position);
      BigDecimal liqPrice = getLiqPrice(position);
      BigDecimal bustPrice = null;
      if (!position.getBustPrice().isEmpty()) {
        bustPrice = new BigDecimal(position.getBustPrice());
      }
      BigDecimal sessionAvgPrice = null;
      if (!position.getSessionAvgPrice().isEmpty()) {
        sessionAvgPrice = new BigDecimal(position.getSessionAvgPrice());
      }
      BybitComplexPositionChanges positionChanges =
          BybitComplexPositionChanges.builder()
              .instrument(
                  convertBybitSymbolToInstrument(position.getSymbol(), position.getCategory()))
              .type(type)
              .size(new BigDecimal(position.getSize()))
              .price(new BigDecimal(position.getEntryPrice()))
              .liquidationPrice(liqPrice)
              .unRealisedPnl(new BigDecimal(position.getUnrealisedPnl()))
              .positionIdx(position.getPositionIdx())
              .tradeMode(position.getTradeMode())
              .riskId(position.getRiskId())
              .riskLimitValue(position.getRiskLimitValue())
              .markPrice(new BigDecimal(position.getMarkPrice()))
              .positionBalance(new BigDecimal(position.getPositionBalance()))
              .autoAddMargin(position.getAutoAddMargin())
              .positionMM(new BigDecimal(position.getPositionMM()))
              .positionIM(new BigDecimal(position.getPositionIM()))
              .bustPrice(bustPrice)
              .positionValue(new BigDecimal(position.getPositionValue()))
              .leverage(new BigDecimal(position.getLeverage()))
              .takeProfit(new BigDecimal(position.getTakeProfit()))
              .stopLoss(new BigDecimal(position.getStopLoss()))
              .trailingStop(new BigDecimal(position.getTrailingStop()))
              .curRealisedPnl(new BigDecimal(position.getCurRealisedPnl()))
              .cumRealisedPnl(new BigDecimal(position.getCumRealisedPnl()))
              .sessionAvgPrice(sessionAvgPrice)
              .positionStatus(position.getPositionStatus())
              .adlRankIndicator(position.getAdlRankIndicator())
              .isReduceOnly(position.isReduceOnly())
              .mmrSysUpdatedTime(position.getMmrSysUpdatedTime())
              .leverageSysUpdatedTime(position.getLeverageSysUpdatedTime())
              .createdTime(new Date(Long.parseLong(position.getCreatedTime())))
              .updatedTime(new Date(Long.parseLong(position.getUpdatedTime())))
              .seq(position.getSeq())
              .build();
      result.add(positionChanges);
    }
    return result;
  }

  public static List<BybitComplexOrderChanges> adaptComplexOrdersChanges(
      List<BybitOrderChanges> data) {
    List<BybitComplexOrderChanges> result = new ArrayList<>();
    for (BybitOrderChanges change : data) {
      Order.OrderType type = getOrderType(change.getSide());
      BigDecimal avgPrice =
          change.getAvgPrice().isEmpty() ? null : new BigDecimal(change.getAvgPrice());
      BigDecimal triggerPrice =
          change.getTriggerPrice().isEmpty() ? null : new BigDecimal(change.getTriggerPrice());
      BigDecimal takeProfit =
          change.getTakeProfit().isEmpty() ? null : new BigDecimal(change.getTakeProfit());
      BigDecimal stopLoss =
          change.getStopLoss().isEmpty() ? null : new BigDecimal(change.getStopLoss());
      BybitComplexOrderChanges orderChanges =
          new BybitComplexOrderChanges(
              type,
              new BigDecimal(change.getQty()),
              convertBybitSymbolToInstrument(change.getSymbol(), change.getCategory()),
              change.getOrderLinkId(),
              new Date(Long.parseLong(change.getCreatedTime())),
              avgPrice,
              new BigDecimal(change.getCumExecQty()),
              new BigDecimal(change.getCumExecFee()),
              adaptBybitOrderStatus(change.getOrderStatus()),
              change.getCategory(),
              change.getOrderId(),
              change.getIsLeverage(),
              change.getBlockTradeId(),
              new BigDecimal(change.getPrice()),
              new BigDecimal(change.getQty()),
              change.getSide(),
              change.getPositionIdx(),
              change.getCreateType(),
              change.getCancelType(),
              change.getRejectReason(),
              change.getLeavesQty().isEmpty() ? null : new BigDecimal(change.getLeavesQty()),
              change.getLeavesValue().isEmpty() ? null : new BigDecimal(change.getLeavesValue()),
              new BigDecimal(change.getCumExecValue()),
              change.getFeeCurrency(),
              BybitTimeInForce.valueOf(change.getTimeInForce().toUpperCase()),
              change.getOrderType(),
              change.getStopOrderType(),
              change.getOcoTriggerBy(),
              change.getOrderIv(),
              change.getMarketUnit(),
              triggerPrice,
              takeProfit,
              stopLoss,
              change.getTpslMode(),
              change.getTpLimitPrice().isEmpty() ? null : new BigDecimal(change.getTpLimitPrice()),
              change.getSlLimitPrice().isEmpty() ? null : new BigDecimal(change.getSlLimitPrice()),
              change.getTpTriggerBy(),
              change.getSlTriggerBy(),
              change.getTriggerDirection(),
              change.getTriggerBy(),
              change.getLastPriceOnCreated(),
              change.isReduceOnly(),
              change.isCloseOnTrigger(),
              change.getPlaceType(),
              change.getSmpType(),
              change.getSmpGroup(),
              change.getSmpOrderId(),
              new Date(Long.parseLong(change.getUpdatedTime())));

      result.add(orderChanges);
    }
    return result;
  }

  public static BybitStreamBatchAmendOrdersPayload adaptBatchAmendOrder(
      LimitOrder[] orders, BybitCategory category) {
    List<BybitStreamBatchAmendOrderPayload> ordersPayload = new ArrayList<>();
    for (LimitOrder order : orders) {
      ordersPayload.add(
          new BybitStreamBatchAmendOrderPayload(
              convertToBybitSymbol(order.getInstrument()),
              order.getId(),
              order.getUserReference(),
              null,
              // conditional
              order.getOriginalAmount() == null ? null : order.getOriginalAmount().toPlainString(),
              // conditional
              order.getLimitPrice() == null ? null : order.getLimitPrice().toPlainString(),
              null,
              null,
              null,
              null,
              null,
              null,
              null,
              null));
    }
    return new BybitStreamBatchAmendOrdersPayload(category, ordersPayload);
  }

  public static FundingRate adaptFundingRate(BybitLinearInverseTicker bybitTicker) {
    int interval = bybitTicker.getFundingIntervalHour();
    BigDecimal fundingRate = bybitTicker.getFundingRate();
    FundingRateInterval rateInterval = adaptFundingRateInterval(interval);
    BigDecimal fundingRate1h =
        fundingRate.divide(
            BigDecimal.valueOf(interval), fundingRate.scale(), RoundingMode.HALF_EVEN);
    return new FundingRate.Builder()
        .fundingRate1h(fundingRate1h)
        .fundingRate(fundingRate)
        .instrument(convertBybitSymbolToInstrument(bybitTicker.getSymbol(), BybitCategory.LINEAR))
        .fundingRateInterval(rateInterval)
        .fundingRateDate(bybitTicker.getNextFundingTime())
        .fundingRateEffectiveInMinutes(
            TimeUnit.MILLISECONDS.toMinutes(
                bybitTicker.getNextFundingTime().getTime() - System.currentTimeMillis()))
        .build();
  }

  public static FundingRateInterval adaptFundingRateInterval(int interval) {
    switch (interval) {
      case 1:
        {
          return FundingRateInterval.H1;
        }
      case 2:
        {
          return FundingRateInterval.H2;
        }
      case 4:
        {
          return FundingRateInterval.H4;
        }
      case 6:
        {
          return FundingRateInterval.H6;
        }
      default:
        {
          return FundingRateInterval.H8;
        }
    }
  }
}
