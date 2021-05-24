package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
import info.bitrich.xchangestream.ftx.dto.FtxOrderbookResponse;
import info.bitrich.xchangestream.ftx.dto.FtxTickerResponse;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;

public class FtxStreamingAdapters {

  private static final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public static OrderBook adaptOrderbookMessage(
      OrderBook orderBook, Instrument instrument, JsonNode jsonNode) {

    Streams.stream(jsonNode)
        .filter(JsonNode::isObject)
        .map(
            res -> {
              try {
                return mapper.readValue(res.toString(), FtxOrderbookResponse.class);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
        .forEach(
            message -> {
              if ("partial".equals(message.getAction())) {
                message
                    .getAsks()
                    .forEach(
                        ask ->
                            orderBook
                                .getAsks()
                                .add(
                                    new LimitOrder.Builder(Order.OrderType.ASK, instrument)
                                        .limitPrice(ask.get(0))
                                        .originalAmount(ask.get(1))
                                        .build()));

                message
                    .getBids()
                    .forEach(
                        bid ->
                            orderBook
                                .getBids()
                                .add(
                                    new LimitOrder.Builder(Order.OrderType.BID, instrument)
                                        .limitPrice(bid.get(0))
                                        .originalAmount(bid.get(1))
                                        .build()));
              } else {
                message
                    .getAsks()
                    .forEach(
                        ask ->
                            orderBook.update(
                                new LimitOrder.Builder(Order.OrderType.ASK, instrument)
                                    .limitPrice(ask.get(0))
                                    .originalAmount(ask.get(1))
                                    .build()));
                message
                    .getBids()
                    .forEach(
                        bid ->
                            orderBook.update(
                                new LimitOrder.Builder(Order.OrderType.BID, instrument)
                                    .limitPrice(bid.get(0))
                                    .originalAmount(bid.get(1))
                                    .build()));
              }

              if (orderBook.getAsks().size() > 0 && orderBook.getBids().size() > 0) {
                Long calculatedChecksum =
                    getOrderbookChecksum(orderBook.getAsks(), orderBook.getBids());

                if (!calculatedChecksum.equals(message.getChecksum())) {
                  throw new RuntimeException("Checksum is not correct!");
                }
              }
            });

    return new OrderBook(
        Date.from(Instant.now()),
        new ArrayList<>(orderBook.getAsks()),
        new ArrayList<>(orderBook.getBids()),
        true);
  }

  public static Long getOrderbookChecksum(List<LimitOrder> asks, List<LimitOrder> bids) {
    StringBuilder data = new StringBuilder(3072);

    for (int i = 0; i < 100; i++) {
      if (bids.size() >= i) {
        data.append(bids.get(i).getLimitPrice().doubleValue())
            .append(":")
            .append(bids.get(i).getOriginalAmount().doubleValue());
      }
      data.append(":");
      if (asks.size() >= i) {
        data.append(asks.get(i).getLimitPrice().doubleValue())
            .append(":")
            .append(asks.get(i).getOriginalAmount().doubleValue());
      }
      if (i != 99) {
        data.append(":");
      }
    }

    CRC32 crc32 = new CRC32();
    byte[] toBytes = data.toString().getBytes(StandardCharsets.UTF_8);
    crc32.update(toBytes, 0, toBytes.length);

    return crc32.getValue();
  }

  public static Ticker adaptTickerMessage(Instrument instrument, JsonNode jsonNode) {
    return Streams.stream(jsonNode)
        .filter(JsonNode::isObject)
        .map(
            res -> {
              try {
                return mapper.readValue(res.toString(), FtxTickerResponse.class);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
        .map(
            ticker ->
                new Ticker.Builder()
                    .instrument(instrument)
                    .ask(ticker.getAsk())
                    .bid(ticker.getBid())
                    .timestamp(ticker.getTime())
                    .build())
        .findFirst()
        .orElse(new Ticker.Builder().build());
  }
}
