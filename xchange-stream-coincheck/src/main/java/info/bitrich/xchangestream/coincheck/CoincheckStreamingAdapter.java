package info.bitrich.xchangestream.coincheck;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coincheck.dto.CoincheckStreamingOrderbookUpdate;
import info.bitrich.xchangestream.coincheck.dto.CoincheckStreamingTrade;
import info.bitrich.xchangestream.coincheck.dto.CoincheckSubscriptionNames;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import lombok.SneakyThrows;
import org.knowm.xchange.coincheck.CoincheckAdapter;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoincheckStreamingAdapter {
  private static final JavaType orderBookUpdateListType =
      StreamingObjectMapperHelper.getObjectMapper()
          .getTypeFactory()
          .constructCollectionType(ArrayList.class, CoincheckStreamingOrderbookUpdate.class);

  public static Stream<OrderBookUpdate> parseOrderBookUpdates(JsonNode json) {
    if (!json.isArray()) {
      throw new IllegalArgumentException("Array expected");
    }
    CurrencyPair pair = CoincheckPair.stringToPair(json.get(0).asText()).getPair();
    JsonNode container = json.get(1);
    if (container == null || container.isEmpty()) {
      return Stream.empty();
    }
    if (!container.isObject()) {
      throw new IllegalArgumentException("Object expected");
    }
    List<OrderBookUpdate> bids =
        parseOrderBookUpdates(Order.OrderType.BID, pair, container.get("bids"));
    List<OrderBookUpdate> asks =
        parseOrderBookUpdates(Order.OrderType.ASK, pair, container.get("asks"));
    return Stream.concat(bids.stream(), asks.stream());
  }

  @SneakyThrows
  private static List<OrderBookUpdate> parseOrderBookUpdates(
      Order.OrderType orderType, CurrencyPair currencyPair, JsonNode container) {

    ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();
    List<CoincheckStreamingOrderbookUpdate> streamingUpdates =
        objectMapper.treeToValue(container, orderBookUpdateListType);
    List<OrderBookUpdate> updates =
        streamingUpdates.stream()
            .map(u -> parseOrderBookUpdate(orderType, currencyPair, u))
            .collect(Collectors.toList());
    return updates;
  }

  private static OrderBookUpdate parseOrderBookUpdate(
      Order.OrderType orderType,
      CurrencyPair currencyPair,
      CoincheckStreamingOrderbookUpdate update) {
    return new OrderBookUpdate(
        orderType, update.getVolume(), currencyPair, update.getPrice(), null, update.getVolume());
  }

  @SneakyThrows
  public static Trade parseTrade(JsonNode json) {
    CoincheckStreamingTrade streamingTrade =
        StreamingObjectMapperHelper.getObjectMapper()
            .treeToValue(json, CoincheckStreamingTrade.class);
    Order.OrderType orderType = CoincheckAdapter.createOrderType(streamingTrade.getOrderType());
    return new Trade(
        orderType,
        streamingTrade.getAmount(),
        streamingTrade.getPair().getPair(),
        streamingTrade.getPrice(),
        null,
        streamingTrade.getId(),
        null,
        null);
  }

  public static String getChannelNameFromMessage(JsonNode node) {
    if (!node.isArray()) {
      return null;
    }
    if (node.size() == 2 && node.get(1).isObject()) {
      return node.get(0).asText() + "-" + CoincheckSubscriptionNames.ORDERBOOK.getName();
    }
    if (node.size() == 5) {
      return node.get(1).asText() + "-" + CoincheckSubscriptionNames.TRADES.getName();
    }
    return null;
  }
}
