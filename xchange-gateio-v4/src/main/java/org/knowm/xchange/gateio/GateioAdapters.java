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
import java.math.MathContext;
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

  public String toGateioInstrument(Instrument instrument) {
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

  public OrderBook toOrderBook(GateioOrderBook gateioOrderBook, Instrument instrument) {
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

  public InstrumentMetaData currencyPairToInstrumentMetaData(
      GateioCurrencyPairDetails gateioCurrencyPairDetails) {
    return InstrumentMetaData.builder()
        .tradingFee(gateioCurrencyPairDetails.getFee())
        .minimumAmount(gateioCurrencyPairDetails.getMinAssetAmount())
        .counterMinimumAmount(gateioCurrencyPairDetails.getMinQuoteAmount())
        .volumeScale(gateioCurrencyPairDetails.getAssetScale())
        .priceScale(gateioCurrencyPairDetails.getQuoteScale())
        .build();
  }

  public InstrumentMetaData instrumentToInstrumentMetaData(
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

  public String toGateioInstrument(OrderStatus orderStatus) {
    switch (orderStatus) {
      case OPEN:
        return "open";
      case CLOSED:
        return "finished";
      default:
        throw new IllegalArgumentException("Can't map " + orderStatus);
    }
  }

  public OrderStatus toOrderStatus(GateioSpotOrderResponse gateioSpotOrderResponse) {
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

  public OrderStatus toOrderStatusFutures(GateioFuturesOrderResponse gateioFuturesOrderResponse) {

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

  public GateioSpotOrderRequest toGateioSpotOrderRequest(MarketOrder marketOrder) {
    GateioSpotOrderRequest.GateioSpotOrderRequestBuilder builder = GateioSpotOrderRequest.builder()
        .currencyPair(marketOrder.getInstrument())
        .side(marketOrder.getType())
        .clientOrderId(marketOrder.getUserReference() != null ? marketOrder.getUserReference() : null)
        .type("market")
        .timeInForce("ioc")
        .amount(marketOrder.getOriginalAmount().toPlainString());
    builder.account("spot");
    return builder.build();
  }

  public GateioSpotOrderRequest toGateioSpotOrderRequest(LimitOrder limitOrder) {
    GateioSpotOrderRequest.GateioSpotOrderRequestBuilder builder = GateioSpotOrderRequest.builder()
        .currencyPair(limitOrder.getInstrument())
        .side(limitOrder.getType())
        .clientOrderId(limitOrder.getUserReference() != null ? limitOrder.getUserReference() : null)
        .type("limit")
        .timeInForce("gtc")
        .price(limitOrder.getLimitPrice().toPlainString())
        .amount(limitOrder.getOriginalAmount().toPlainString());
    builder.account("spot");
    return builder.build();
  }

  public GateioFuturesOrderRequest toGateioFuturesOrder(MarketOrder marketOrder, BigDecimal contractValue) {
    BigDecimal size = convertVolumeToContractSize(marketOrder.getOriginalAmount(), contractValue);
    String userReference;
    if (marketOrder.getUserReference() != null)
      if (marketOrder.getUserReference().startsWith("t-"))
        userReference = marketOrder.getUserReference();
      else userReference = "t-" + marketOrder.getUserReference();
    else userReference = "t-" + System.currentTimeMillis();
    return GateioFuturesOrderRequest.builder()
        .contract(toGateioInstrument(marketOrder.getInstrument()))
        .size(marketOrder.getType() == OrderType.BID ? size.toPlainString() : size.negate().toPlainString())
        .price(BigDecimal.ZERO.toPlainString())
        .text(userReference)
        .timeInForce("ioc") // a price of 0 with tif as ioc represents a market order.
        .build();
  }


  public GateioFuturesOrderRequest toGateioFuturesOrder(LimitOrder limitOrder, BigDecimal contractValue) {
    var builder = GateioFuturesOrderRequest.builder();
    if (limitOrder.getOrderFlags() != null) {
      Set<Order.IOrderFlags> flags = limitOrder.getOrderFlags();
      for (var flag : flags)
        if (flag instanceof GateioOrderFlags)
          builder.timeInForce(((GateioOrderFlags) flag).timeInForce.name().toLowerCase());
    }
    BigDecimal size = convertVolumeToContractSize(limitOrder.getOriginalAmount(), contractValue);
    return builder.contract(toGateioInstrument(limitOrder.getInstrument())).
        size(limitOrder.getType() == OrderType.BID ? size.toPlainString() : size.negate().toPlainString()).
        price(limitOrder.getLimitPrice().toPlainString()).
        text(limitOrder.getUserReference() != null ? limitOrder.getUserReference() : null).
        build();
  }


  public Order toOrder(GateioFuturesOrderResponse gateioFutureOrderResponse, BigDecimal contractValue) {
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
        .cumulativeAmount(amount.subtract(convertContractSizeToVolume(gateioFutureOrderResponse.getLeft(), contractValue)))
        .orderStatus(status)
        .timestamp(timestamp)
        .averagePrice(gateioFutureOrderResponse.getFillPrice())
        .fee(gateioFutureOrderResponse.getFee())
        .build();
  }


  public Order toOrder(GateioSpotOrderResponse gateioOrder) {
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
        builder.cumulativeAmount(gateioOrder.getFilledTotalQuote());
      } else if (orderType == OrderType.ASK) {
        BigDecimal filledAssetAmount =
            gateioOrder
                .getFilledTotalQuote()
                .divide(gateioOrder.getAvgDealPrice(), MathContext.DECIMAL32);
        builder.cumulativeAmount(filledAssetAmount);
      } else {
        throw new IllegalArgumentException("Can't map " + orderType);
      }
    } else builder.cumulativeAmount(BigDecimal.ZERO);

    return builder
        .id(gateioOrder.getId())
        .originalAmount(gateioOrder.getAmount())
        .userReference(gateioOrder.getClientOrderId())
        .timestamp(Date.from(gateioOrder.getCreatedAt()))
        .orderStatus(status)
        .averagePrice(gateioOrder.getAvgDealPrice())
        .fee(gateioOrder.getFee())
        .build();
  }

  public UserTrade toUserTrade(GateioUserTradeRaw gateioUserTradeRaw) {
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

  public GateioWithdrawalRequest toGateioWithdrawalRequest(GateioWithdrawFundsParams p) {
    return GateioWithdrawalRequest.builder()
        .clientRecordId(p.getClientRecordId())
        .address(p.getAddress())
        .tag(p.getAddressTag())
        .chain(p.getChain())
        .amount(p.getAmount())
        .currency(p.getCurrency())
        .build();
  }

  public Ticker toTickerSpot(GateioTicker gateioTicker) {
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

  public Ticker toTickerFutures(GateioFuturesTickerAndFunding gateioTicker, BigDecimal contractValue) {
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

  public CandleStickData toCandleStickDataSpot(
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

  public CandleStickData toCandleStickDataFutures(
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

  public FundingRecord toFundingRecords(GateioAccountBookRecord gateioAccountBookRecord) {
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

  private static BigDecimal convertVolumeToContractSize(
      BigDecimal size, BigDecimal contractValue) {
    return size.divide(contractValue, 20, RoundingMode.HALF_DOWN)
        .stripTrailingZeros();
  }

}
