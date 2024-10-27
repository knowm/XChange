package info.bitrich.xchangestream.bitget;

import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.ChannelType;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.MarketType;
import info.bitrich.xchangestream.bitget.dto.response.BitgetTickerNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetTickerNotification.TickerData;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsOrderBookSnapshotNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsOrderBookSnapshotNotification.OrderBookData;
import java.util.List;
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
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class BitgetStreamingAdapters {

  public Ticker toTicker(BitgetTickerNotification notification) {
    TickerData bitgetTickerDto = notification.getData().get(0);

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


  /**
   * Returns unique subscription id. Can be used as key for subscriptions caching
   */
  public String toSubscriptionId(BitgetChannel bitgetChannel) {
    return Stream.of(bitgetChannel.getMarketType(), bitgetChannel.getChannelType(), bitgetChannel.getInstrumentId())
        .map(String::valueOf)
        .collect(Collectors.joining("_"));
  }


  /**
   * Parses subscription id to channel (as "marketType_channelName_instrument")
   */
  public BitgetChannel toBitgetChannel(String subscriptionId) {
    Object[] parsedSubscriptionId = subscriptionId.split("_");
    String marketType = (String) ArrayUtils.get(parsedSubscriptionId, 0);
    String channelName = (String) ArrayUtils.get(parsedSubscriptionId, 1);
    String instrument = (String) ArrayUtils.get(parsedSubscriptionId, 2);

    return BitgetChannel.builder()
        .marketType(MarketType.valueOf(marketType))
        .channelType(ChannelType.valueOf(channelName))
        .instrumentId(instrument)
        .build();
  }

  public OrderBook toOrderBook(BitgetWsOrderBookSnapshotNotification notification, Instrument instrument) {
    OrderBookData orderBookData = notification.getData().get(0);
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

}
