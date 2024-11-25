package info.bitrich.xchangestream.bitget;

import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.ChannelType;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.MarketType;
import info.bitrich.xchangestream.bitget.dto.response.BitgetTickerNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetTickerNotification.TickerData;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsOrderBookSnapshotNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsOrderBookSnapshotNotification.OrderBookData;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsUserTradeNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsUserTradeNotification.BitgetFillData;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsUserTradeNotification.FeeDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class BitgetStreamingAdapters {

  public Ticker toTicker(BitgetTickerNotification notification) {
    TickerData bitgetTickerDto = notification.getPayloadItems().get(0);

    CurrencyPair currencyPair = BitgetAdapters.toCurrencyPair(bitgetTickerDto.getInstrument());
    if (currencyPair == null) {
      return null;
    }

    return new Ticker.Builder()
        .instrument(currencyPair)
        .open(bitgetTickerDto.getOpen24h())
        .last(bitgetTickerDto.getLastPrice())
        .bid(bitgetTickerDto.getBestBidPrice())
        .ask(bitgetTickerDto.getBestAskPrice())
        .high(bitgetTickerDto.getHigh24h())
        .low(bitgetTickerDto.getLow24h())
        .volume(bitgetTickerDto.getAssetVolume24h())
        .quoteVolume(bitgetTickerDto.getQuoteVolume24h())
        .timestamp(BitgetAdapters.toDate(bitgetTickerDto.getTimestamp()))
        .bidSize(bitgetTickerDto.getBestBidSize())
        .askSize(bitgetTickerDto.getBestAskSize())
        .percentageChange(bitgetTickerDto.getChange24h())
        .build();
  }

  /** Returns unique subscription id. Can be used as key for subscriptions caching */
  public String toSubscriptionId(BitgetChannel bitgetChannel) {
    return Stream.of(
            bitgetChannel.getMarketType(),
            bitgetChannel.getChannelType(),
            bitgetChannel.getInstrumentId())
        .map(String::valueOf)
        .collect(Collectors.joining("_"));
  }

  /**
   * Creates {@code BitgetChannel} from arguments
   *
   * @param args [{@code ChannelType}, {@code MarketType}, {@code Instrument}/{@code null}]
   */
  public BitgetChannel toBitgetChannel(Object... args) {
    ChannelType channelType = (ChannelType) ArrayUtils.get(args, 0);
    MarketType marketType = (MarketType) ArrayUtils.get(args, 1);
    Instrument instrument = (Instrument) ArrayUtils.get(args, 2);

    return BitgetChannel.builder()
        .channelType(channelType)
        .marketType(marketType)
        .instrumentId(
            Optional.ofNullable(instrument).map(BitgetAdapters::toString).orElse("default"))
        .build();
  }

  public OrderBook toOrderBook(
      BitgetWsOrderBookSnapshotNotification notification, Instrument instrument) {
    OrderBookData orderBookData = notification.getPayloadItems().get(0);
    List<LimitOrder> asks =
        orderBookData.getAsks().stream()
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
        orderBookData.getBids().stream()
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

    return new OrderBook(BitgetAdapters.toDate(orderBookData.getTimestamp()), asks, bids);
  }

  public UserTrade toUserTrade(BitgetWsUserTradeNotification notification) {
    BitgetFillData bitgetFillData = notification.getPayloadItems().get(0);
    return new UserTrade(
        bitgetFillData.getOrderSide(),
        bitgetFillData.getAssetAmount(),
        BitgetAdapters.toCurrencyPair(bitgetFillData.getSymbol()),
        bitgetFillData.getPrice(),
        BitgetAdapters.toDate(bitgetFillData.getUpdatedAt()),
        bitgetFillData.getTradeId(),
        bitgetFillData.getOrderId(),
        bitgetFillData.getFeeDetails().stream()
            .map(FeeDetail::getTotalFee)
            .map(BigDecimal::abs)
            .reduce(BigDecimal.ZERO, BigDecimal::add),
        bitgetFillData.getFeeDetails().stream()
            .map(FeeDetail::getCurrency)
            .findFirst()
            .orElse(null),
        null);
  }
}
