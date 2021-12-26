package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Streams;
import info.bitrich.xchangestream.ftx.dto.FtxOrderbookResponse;
import info.bitrich.xchangestream.ftx.dto.FtxTickerResponse;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.dto.marketdata.FtxTradeDto;
import org.knowm.xchange.ftx.dto.trade.FtxOrderFlags;
import org.knowm.xchange.instrument.Instrument;

public class FtxStreamingAdapters {

  private static final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  /** Incoming values always has 1 trailing 0 after the decimal, and start with 1 zero */
  private static final ThreadLocal<DecimalFormat> dfp =
      ThreadLocal.withInitial(() -> new DecimalFormat("0.0#######"));

  private static final ThreadLocal<DecimalFormat> dfs =
      ThreadLocal.withInitial(() -> new DecimalFormat("0.####E00"));
  private static final ThreadLocal<DecimalFormat> dfq =
      ThreadLocal.withInitial(() -> new DecimalFormat("0.0#######"));

  static Ticker NULL_TICKER =
      new Ticker.Builder().build(); // not need to create a new one each time

  public static OrderBook adaptOrderbookMessage(
      OrderBook orderBook, Instrument instrument, JsonNode jsonNode) {

    Streams.stream(jsonNode)
        .filter(JsonNode::isObject)
        .map(
            res -> {
              try {
                return mapper.readValue(res.toString(), FtxOrderbookResponse.class);
              } catch (IOException e) {
                throw new IllegalStateException(e);
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
                  throw new IllegalStateException("Checksum is not correct!");
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
    DecimalFormat fp = dfp.get();
    DecimalFormat fs = dfs.get();
    DecimalFormat fq = dfq.get();

    for (int i = 0; i < 100; i++) {
      if (bids.size() > i) {
        BigDecimal limitPrice = bids.get(i).getLimitPrice();
        boolean scientific = limitPrice.toPlainString().startsWith("0.0000");

        data.append(scientific ? fs.format(limitPrice) : fp.format(limitPrice))
            .append(":")
            .append(fq.format(bids.get(i).getOriginalAmount()))
            .append(":");
      }

      if (asks.size() > i) {
        BigDecimal limitPrice = asks.get(i).getLimitPrice();
        boolean scientific = limitPrice.toPlainString().startsWith("0.0000");

        data.append(scientific ? fs.format(limitPrice) : fp.format(limitPrice))
            .append(":")
            .append(fq.format(asks.get(i).getOriginalAmount()))
            .append(":");
      }
    }

    String s = data.toString().replace("E", "e"); // strip last :

    CRC32 crc32 = new CRC32();
    byte[] toBytes = s.getBytes(StandardCharsets.UTF_8);
    crc32.update(toBytes, 0, toBytes.length - 1);

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
        .map(ftxTickerResponse -> ftxTickerResponse.toTicker(instrument))
        .findFirst()
        .orElse(NULL_TICKER);
  }

  public static Iterable<Trade> adaptTradesMessage(Instrument instrument, JsonNode jsonNode) {
    return Streams.stream(jsonNode)
        .filter(JsonNode::isArray)
        .map(res -> (ArrayNode) res)
        .flatMap(Streams::stream)
        .map(
            tradeNode -> {
              try {
                return mapper.readValue(tradeNode.toString(), FtxTradeDto.class);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
        .map(
            ftxTradeDto ->
                new Trade.Builder()
                    .timestamp(ftxTradeDto.getTime())
                    .instrument(instrument)
                    .id(ftxTradeDto.getId())
                    .price(ftxTradeDto.getPrice())
                    .type(FtxAdapters.adaptFtxOrderSideToOrderType(ftxTradeDto.getSide()))
                    .originalAmount(ftxTradeDto.getSize())
                    .build())
        .collect(Collectors.toList());
  }

  public static UserTrade adaptUserTrade(JsonNode jsonNode) {
    JsonNode data = jsonNode.get("data");

    return new UserTrade.Builder()
        .currencyPair(new CurrencyPair(data.get("market").asText()))
        .type("buy".equals(data.get("side").asText()) ? Order.OrderType.BID : Order.OrderType.ASK)
        .instrument(new CurrencyPair(data.get("market").asText()))
        .originalAmount(data.get("size").decimalValue())
        .price(data.get("price").decimalValue())
        .timestamp(Date.from(Instant.ofEpochMilli(data.get("time").asLong())))
        .id(data.get("id").asText())
        .orderId(data.get("orderId").asText())
        .feeAmount(data.get("fee").decimalValue())
        .feeCurrency(new Currency(data.get("feeCurrency").asText()))
        .build();
  }

  public static Order adaptOrders(JsonNode jsonNode) {
    JsonNode data = jsonNode.get("data");

    LimitOrder.Builder order =
        new LimitOrder.Builder(
                "buy".equals(data.get("side").asText()) ? Order.OrderType.BID : Order.OrderType.ASK,
                new CurrencyPair(data.get("market").asText()))
            .id(data.get("id").asText())
            .timestamp(Date.from(Instant.now()))
            .limitPrice(data.get("price").decimalValue())
            .originalAmount(data.get("size").decimalValue())
            .userReference(data.get("clientId").asText())
            .remainingAmount(data.get("remainingSize").decimalValue())
            .averagePrice(data.get("avgFillPrice").decimalValue())
            .orderStatus(Order.OrderStatus.valueOf(data.get("status").asText().toUpperCase()));

    if (data.get("ioc").asBoolean()) order.flag(FtxOrderFlags.IOC);
    if (data.get("postOnly").asBoolean()) order.flag(FtxOrderFlags.POST_ONLY);
    if (data.get("reduceOnly").asBoolean()) order.flag(FtxOrderFlags.REDUCE_ONLY);

    return order.build();
  }
}
