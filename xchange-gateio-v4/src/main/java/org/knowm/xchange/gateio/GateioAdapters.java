package org.knowm.xchange.gateio;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.gateio.dto.account.GateioAccountBookRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.dto.marketdata.*;
import org.knowm.xchange.gateio.dto.trade.*;
import org.knowm.xchange.gateio.service.params.GateioWithdrawFundsParams;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class GateioAdapters {

  public final BigDecimal PARTIALLY_FILLED_SCALE = new BigDecimal("0.1");

  public static String toGateioInstrument(Instrument instrument) {
    if (instrument == null) {
      return null;
    } else {
      return String.format(
              "%s_%s",
              instrument.getBase().getCurrencyCode(), instrument.getCounter().getCurrencyCode())
          .toUpperCase(Locale.ROOT);
    }
  }

  public static Instrument fromGateioInstrument(String gateIoInstrument, boolean isFuture) {
    String xchangeInstrument = gateIoInstrument.replace("_", "/");
    if (isFuture) {
      if (!xchangeInstrument.contains("/")) {
        // for futures, it might be just "BTC" or "BTC_USDT"
        return new FuturesContract(xchangeInstrument + "/USDT/PERP");
      }
      return new FuturesContract(new CurrencyPair(xchangeInstrument), "PERP");
    }
    if (!xchangeInstrument.contains("/")) {
      return null;
    }
    return new CurrencyPair(xchangeInstrument);
  }

  public static OrderBook toOrderBook(GateioOrderBook gateioOrderBook, Instrument instrument) {
    List<LimitOrder> asks =
        gateioOrderBook.getAsks().stream()
            .map(
                priceSizeEntry ->
                    new LimitOrder(
                        OrderType.ASK,
                        priceSizeEntry.getSize(),
                        instrument,
                        null,
                        null,
                        priceSizeEntry.getPrice()))
            .collect(Collectors.toList());

    List<LimitOrder> bids =
        gateioOrderBook.getBids().stream()
            .map(
                priceSizeEntry ->
                    new LimitOrder(
                        OrderType.BID,
                        priceSizeEntry.getSize(),
                        instrument,
                        null,
                        null,
                        priceSizeEntry.getPrice()))
            .collect(Collectors.toList());

    return new OrderBook(Date.from(gateioOrderBook.getGeneratedAt()), asks, bids);
  }

  public static InstrumentMetaData currencyPairToInstrumentMetaData(
      GateioCurrencyPairDetails gateioCurrencyPairDetails) {
    return InstrumentMetaData.builder()
        .tradingFee(gateioCurrencyPairDetails.getFee())
        .minimumAmount(gateioCurrencyPairDetails.getMinAssetAmount())
        .counterMinimumAmount(gateioCurrencyPairDetails.getMinQuoteAmount())
        .volumeScale(gateioCurrencyPairDetails.getAssetScale())
        .priceScale(gateioCurrencyPairDetails.getQuoteScale())
        .build();
  }

  public static InstrumentMetaData instrumentToInstrumentMetaData(
      GateioInstrumentDetails gateioInstrumentDetails) {
    return InstrumentMetaData.builder()
        .contractValue(gateioInstrumentDetails.getQuantoMultiplier())
        .tradingFee(gateioInstrumentDetails.getTakerFeeRate())
        .minimumAmount(gateioInstrumentDetails.getOrderSizeMin().multiply(gateioInstrumentDetails.getQuantoMultiplier()).stripTrailingZeros())
        .maximumAmount(gateioInstrumentDetails.getOrderSizeMax().multiply(gateioInstrumentDetails.getQuantoMultiplier()).stripTrailingZeros())
        .priceStepSize(gateioInstrumentDetails.getOrderPriceRound())
        // no data, so suggest that equals to order min size
        .amountStepSize(gateioInstrumentDetails.getOrderSizeMin().multiply(gateioInstrumentDetails.getQuantoMultiplier()).stripTrailingZeros())
        .volumeScale(numberOfDecimals(gateioInstrumentDetails.getOrderSizeMin().multiply(gateioInstrumentDetails.getQuantoMultiplier()).stripTrailingZeros()))
        .priceScale(numberOfDecimals(gateioInstrumentDetails.getOrderPriceRound()))
        .contractValue(gateioInstrumentDetails.getQuantoMultiplier())
        .build();
  }

  public static String toGateioInstrument(OrderStatus orderStatus) {
    switch (orderStatus) {
      case OPEN:
        return "open";
      case CLOSED:
        return "finished";
      default:
        throw new IllegalArgumentException("Can't map " + orderStatus);
    }
  }

  public static OrderStatus toOrderStatus(GateioSpotOrderResponse gateioSpotOrderResponse) {
    if (gateioSpotOrderResponse.getStatus() == null) {
      return null;
    }
    switch (gateioSpotOrderResponse.getStatus()) {
      case "open":
      case "put":
        return OrderStatus.OPEN;

      case "closed":
        // if more than `PARTIALLY_FILLED_SCALE` left to fill -> set to `PARTIALLY_FILLED`
        if (gateioSpotOrderResponse
            .getAmountLeftToFill()
            .compareTo(gateioSpotOrderResponse.getAmount().multiply(PARTIALLY_FILLED_SCALE))
            > 0) {
          return OrderStatus.PARTIALLY_FILLED;
        } else {
          return OrderStatus.FILLED;
        }
      case "filled":
      case "finish":
        return OrderStatus.FILLED;

      case "cancelled":
      case "stp":
        return OrderStatus.CANCELED;

      default:
        throw new IllegalArgumentException("Can't map " + gateioSpotOrderResponse.getStatus());
    }
  }

  public static OrderStatus toOrderStatusFutures(GateioFuturesOrderResponse gateioFuturesOrderResponse) {

    switch (gateioFuturesOrderResponse.getStatus()) {
      case "open":
        return OrderStatus.OPEN;

      case "finished":
        switch (gateioFuturesOrderResponse.getFinishAs()) {
          case "filled":
            return OrderStatus.FILLED;
          case "cancelled":
            return OrderStatus.CANCELED;
        }

      default:
        throw new IllegalArgumentException("Can't map " + gateioFuturesOrderResponse.getStatus());
    }
  }

  public static GateioSpotOrderRequest toGateioSpotOrderRequest(MarketOrder marketOrder) {
    GateioSpotOrderRequest.GateioSpotOrderRequestBuilder builder = GateioSpotOrderRequest.builder()
        .currencyPair(marketOrder.getInstrument())
        .side(marketOrder.getType())
        .clientOrderId(formatUserReference(marketOrder.getUserReference()))
        .type("market")
        .timeInForce("ioc")
        .amount(marketOrder.getOriginalAmount().toPlainString());
//    builder.account("unified");
    return builder.build();
  }

  public static GateioSpotOrderRequest toGateioSpotOrderRequest(LimitOrder limitOrder) {
    GateioSpotOrderRequest.GateioSpotOrderRequestBuilder builder = GateioSpotOrderRequest.builder()
        .currencyPair(limitOrder.getInstrument())
        .side(limitOrder.getType())
        .clientOrderId(formatUserReference(limitOrder.getUserReference()))
        .type("limit")
        .timeInForce("gtc")
        .price(limitOrder.getLimitPrice().toPlainString())
        .amount(limitOrder.getOriginalAmount().toPlainString());
//    builder.account("unified");
    return builder.build();
  }

  public static GateioFuturesOrderRequest toGateioFuturesOrder(MarketOrder marketOrder, BigDecimal contractValue) {
    BigDecimal size = convertVolumeToContractSize(marketOrder.getOriginalAmount(), contractValue);
    String userReference;
    userReference = formatUserReference(marketOrder.getUserReference());
    return GateioFuturesOrderRequest.builder()
        .contract(toGateioInstrument(marketOrder.getInstrument()))
        .size(marketOrder.getType() == OrderType.BID | marketOrder.getType() == OrderType.EXIT_ASK
            ? size.toPlainString() : size.negate().toPlainString())
        .price(BigDecimal.ZERO.toPlainString())// a price of 0 with tif as ioc represents a market order.
        .text(userReference)
        .timeInForce("ioc")
        .reduceOnly(isReduceOnly(marketOrder))
        .build();
  }

  private static String formatUserReference(String userReference) {
    String result;
    if (userReference != null)
      if (userReference.startsWith("t-"))
        result = userReference;
      else result = "t-" + userReference;
    else result = "t-" + System.currentTimeMillis();
    return result;
  }

  public static GateioFuturesOrderRequest toGateioFuturesOrder(LimitOrder limitOrder, BigDecimal contractValue) {
    var builder = GateioFuturesOrderRequest.builder();
    if (limitOrder.getOrderFlags() != null) {
      Set<Order.IOrderFlags> flags = limitOrder.getOrderFlags();
      for (var flag : flags)
        if (flag instanceof GateioOrderFlags)
          builder.timeInForce(((GateioOrderFlags) flag).timeInForce.name().toLowerCase());
    }
    BigDecimal size = convertVolumeToContractSize(limitOrder.getOriginalAmount(), contractValue);
    return builder.contract(toGateioInstrument(limitOrder.getInstrument()))
        .size(limitOrder.getType() == OrderType.BID ? size.toPlainString() : size.negate().toPlainString())
        .price(limitOrder.getLimitPrice().toPlainString())
        .text(limitOrder.getUserReference() != null ? limitOrder.getUserReference() : null)
        .reduceOnly(isReduceOnly(limitOrder))
        .build();
  }

  private static boolean isReduceOnly(Order order) {
    return order.getType() == OrderType.EXIT_ASK || order.getType() == OrderType.EXIT_BID;
  }

  public static Order toOrder(GateioFuturesOrderResponse gateioFutureOrderResponse, BigDecimal contractValue) {
    Order.Builder builder;
    Instrument instrument = gateioFutureOrderResponse.getContract();
    OrderType orderType = gateioFutureOrderResponse.getSize().signum() > 0 ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = convertContractSizeToVolume(gateioFutureOrderResponse.getSize().abs(), contractValue);
    //  a price of 0 with tif as ioc represents a market order.
    if (gateioFutureOrderResponse.getPrice().compareTo(BigDecimal.ZERO) == 0 && gateioFutureOrderResponse.getTimeInForce().equals("ioc"))
      builder = new MarketOrder.Builder(orderType, instrument);
    else
      builder = new LimitOrder.Builder(orderType, instrument).limitPrice(gateioFutureOrderResponse.getPrice());
    OrderStatus status = toOrderStatusFutures(gateioFutureOrderResponse);
    Date timestamp;
    if (gateioFutureOrderResponse.getFinishTimeMs() != null)
      timestamp = Date.from(gateioFutureOrderResponse.getFinishTimeMs());
    else if (gateioFutureOrderResponse.getUpdatedTimeMs() != null)
      timestamp = Date.from(gateioFutureOrderResponse.getUpdatedTimeMs());
    else {
      if (gateioFutureOrderResponse.getCreateTimeMs() != null)
        timestamp = Date.from(gateioFutureOrderResponse.getCreateTimeMs());
      else if (gateioFutureOrderResponse.getFinishTime() != null)
        timestamp = Date.from(gateioFutureOrderResponse.getFinishTime());
      else if (gateioFutureOrderResponse.getUpdatedTime() != null)
        timestamp = Date.from(gateioFutureOrderResponse.getUpdatedTime());
      else
        timestamp = Date.from(gateioFutureOrderResponse.getCreateTime());
    }
    return builder
        .id(String.valueOf(gateioFutureOrderResponse.getId()))
        .userReference(gateioFutureOrderResponse.getText())
        .originalAmount(amount)
        .cumulativeAmount(amount.subtract(convertContractSizeToVolume(gateioFutureOrderResponse.getLeft(), contractValue).abs()))
        .orderStatus(status)
        .timestamp(timestamp)
        .averagePrice(gateioFutureOrderResponse.getFillPrice())
        .fee(gateioFutureOrderResponse.getFee())
        .build();
  }


  public static Order toOrder(GateioSpotOrderResponse gateioOrder) {
    Order.Builder builder;
    Instrument instrument = gateioOrder.getCurrencyPair();
    OrderType orderType = gateioOrder.getSide();

    builder = switch (gateioOrder.getType()) {
      case "market" -> new MarketOrder.Builder(orderType, instrument);
      case "limit" -> new LimitOrder.Builder(orderType, instrument).limitPrice(gateioOrder.getPrice());
      default -> throw new IllegalArgumentException("Can't map " + gateioOrder.getType());
    };

    // if filled then calculate amounts
    OrderStatus status = toOrderStatus(gateioOrder);

    if (status == OrderStatus.FILLED || status == OrderStatus.PARTIALLY_FILLED) {
      if (orderType == OrderType.BID) {
        // It is better not to pass anything than a calculated value that is incorrect.
//        BigDecimal originalAmount =
//            gateioOrder
//                .getFilledTotalQuote()
//                .divide(gateioOrder.getAvgDealPrice(), MathContext.DECIMAL32);
        builder.cumulativeAmount(gateioOrder.getFilledAmount())
            .originalAmount(null);
      } else if (orderType == OrderType.ASK) {
        builder.cumulativeAmount(gateioOrder.getFilledAmount())
            .originalAmount(gateioOrder.getAmount());
      } else {
        throw new IllegalArgumentException("Can't map " + orderType);
      }
    } else {
      builder.cumulativeAmount(BigDecimal.ZERO)
          .originalAmount(gateioOrder.getAmount());
    }

    return builder
        .id(gateioOrder.getId())

        .userReference(gateioOrder.getClientOrderId())
        .timestamp(Date.from(gateioOrder.getCreatedAt()))
        .orderStatus(status)
        .averagePrice(gateioOrder.getAvgDealPrice())
        .fee(gateioOrder.getFee())
        .build();
  }

  public static UserTrade toUserTrade(GateioUserTradeRaw gateioUserTradeRaw) {
    return GateioUserTrade.builder()
        .type(gateioUserTradeRaw.getSide())
        .originalAmount(gateioUserTradeRaw.getAmount())
        .instrument(gateioUserTradeRaw.getCurrencyPair())
        .price(gateioUserTradeRaw.getPrice())
        .timestamp(Date.from(gateioUserTradeRaw.getTimeMs()))
        .id(String.valueOf(gateioUserTradeRaw.getId()))
        .orderId(String.valueOf(gateioUserTradeRaw.getOrderId()))
        .feeAmount(gateioUserTradeRaw.getFee())
        .feeCurrency(gateioUserTradeRaw.getFeeCurrency())
        .orderUserReference(gateioUserTradeRaw.getRemark())
        .role(gateioUserTradeRaw.getRole())
        .build();
  }

  public static GateioWithdrawalRequest toGateioWithdrawalRequest(GateioWithdrawFundsParams p) {
    return GateioWithdrawalRequest.builder()
        .clientRecordId(p.getClientRecordId())
        .address(p.getAddress())
        .tag(p.getAddressTag())
        .chain(p.getChain())
        .amount(p.getAmount())
        .currency(p.getCurrency())
        .build();
  }

  public static Ticker toTickerSpot(GateioTicker gateioTicker) {
    return new Ticker.Builder()
        .instrument(fromGateioInstrument(gateioTicker.getCurrencyPair(), false))
        .last(gateioTicker.getLastPrice())
        .bid(gateioTicker.getHighestBid())
        .bidSize(gateioTicker.getHighestBidSize())
        .ask(gateioTicker.getLowestAsk())
        .askSize(gateioTicker.getLowestAskSize())
        .high(gateioTicker.getMaxPrice24h())
        .low(gateioTicker.getMinPrice24h())
        .volume(gateioTicker.getAssetVolume())
        .quoteVolume(gateioTicker.getQuoteVolume())
        .percentageChange(gateioTicker.getChangePercentage24h())
        .build();
  }

  public static Ticker toTickerFutures(GateioFuturesTickerAndFunding gateioTicker, BigDecimal contractValue) {
    return new Ticker.Builder()
        .instrument(gateioTicker.getContract())
        .last(gateioTicker.getLastPrice())
        .bid(gateioTicker.getHighestBid())
        .bidSize(gateioTicker.getHighestBidSize())
        .ask(gateioTicker.getLowestAsk())
        .askSize(gateioTicker.getLowestAskSize())
        .high(gateioTicker.getHigh24h())
        .low(gateioTicker.getLow24h())
        .volume(convertContractSizeToVolume(gateioTicker.getVolume24h(), contractValue))
        .quoteVolume(gateioTicker.getVolume24hQuote())
        .percentageChange(gateioTicker.getChangePercentage24h())
        .build();
  }

  public static CandleStickData toCandleStickDataSpot(
      List<GateioSpotCandlestick> gateioSpotCandlesticks, Instrument instrument) {
    List<CandleStick> candleSticks =
        gateioSpotCandlesticks.stream()
            .map(
                gateioSpotCandlestick ->
                    new CandleStick.Builder()
                        .timestamp(Instant.ofEpochSecond(gateioSpotCandlestick.getTimestamp()))
                        .open(gateioSpotCandlestick.getOpen())
                        .high(gateioSpotCandlestick.getHigh())
                        .low(gateioSpotCandlestick.getLow())
                        .close(gateioSpotCandlestick.getClose())
                        .volume(gateioSpotCandlestick.getVolume())
                        .quotaVolume(gateioSpotCandlestick.getQuoteVolume())
                        .completed(gateioSpotCandlestick.isCompleted())
                        .build())
            .collect(Collectors.toList());

    return new CandleStickData(instrument, candleSticks);
  }

  public static CandleStickData toCandleStickDataFutures(
      List<GateioFuturesCandlestick> gateioFuturesCandlesticks, Instrument instrument, BigDecimal contractValue) {
    List<CandleStick> candleSticks =
        gateioFuturesCandlesticks.stream()
            .map(
                gateioFuturesCandlestick ->
                    new CandleStick.Builder()
                        .timestamp(Instant.ofEpochSecond(gateioFuturesCandlestick.getTimestamp()))
                        .open(gateioFuturesCandlestick.getOpen())
                        .high(gateioFuturesCandlestick.getHigh())
                        .low(gateioFuturesCandlestick.getLow())
                        .close(gateioFuturesCandlestick.getClose())
                        .volume(convertContractSizeToVolume(gateioFuturesCandlestick.getVolume(), contractValue))
                        .quotaVolume(gateioFuturesCandlestick.getQuoteVolume())
                        .build())
            .collect(Collectors.toList());

    return new CandleStickData(instrument, candleSticks);
  }

  public static FundingRecord toFundingRecords(GateioAccountBookRecord gateioAccountBookRecord) {
    return FundingRecord.builder()
        .internalId(gateioAccountBookRecord.getId())
        .date(Date.from(gateioAccountBookRecord.getTimestamp()))
        .currency(gateioAccountBookRecord.getCurrency())
        .balance(gateioAccountBookRecord.getBalance())
        .type(gateioAccountBookRecord.getType())
        .amount(gateioAccountBookRecord.getChange().abs())
        .description(gateioAccountBookRecord.getTypeDescription())
        .build();
  }

  private static int numberOfDecimals(BigDecimal value) {
    double d = value.doubleValue();
    return -(int) Math.round(Math.log10(d));
  }

  public static BigDecimal convertContractSizeToVolume(
      BigDecimal size, BigDecimal contractValue) {
    return size.multiply(contractValue).stripTrailingZeros();
  }

  public static BigDecimal convertVolumeToContractSize(
      BigDecimal size, BigDecimal contractValue) {
    return size.divide(contractValue, 20, RoundingMode.HALF_DOWN)
        .stripTrailingZeros();
  }

}
