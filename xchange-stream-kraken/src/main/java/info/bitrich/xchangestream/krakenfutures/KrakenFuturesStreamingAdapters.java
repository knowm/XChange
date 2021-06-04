package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Kraken streaming adapters
 */
public class KrakenFuturesStreamingAdapters {

    static final String ASK_SNAPSHOT = "asks";
    static final String ASK_UPDATE = "sell";

    static final String BID_SNAPSHOT = "bids";
    static final String BID_UPDATE = "buy";

    public static OrderBook adaptFuturesOrderbookMessage(
            OrderBook orderBook, Instrument instrument, ObjectNode objectNode) {
        if (objectNode.get("feed").asText().equals("book_snapshot")) {
            //Clear orderbook if receiving snapshot
            clearOrderbook(orderBook);

            Date timestamp = new Date(objectNode.get("timestamp").asLong());
            adaptFuturesLimitOrders(instrument, Order.OrderType.BID, objectNode.withArray(BID_SNAPSHOT), timestamp)
                    .forEach(orderBook::update);
            adaptFuturesLimitOrders(instrument, Order.OrderType.ASK, objectNode.withArray(ASK_SNAPSHOT), timestamp)
                    .forEach(orderBook::update);
        } else {
            if (objectNode.get("side").asText().equals(BID_UPDATE)) {
                orderBook.update(adaptFuturesLimitOrder(instrument, Order.OrderType.BID, objectNode));
            }
            if (objectNode.get("side").asText().equals(ASK_UPDATE)) {
                orderBook.update(adaptFuturesLimitOrder(instrument, Order.OrderType.ASK, objectNode));
            }
        }
        return new OrderBook(
                orderBook.getTimeStamp(),
                Lists.newArrayList(orderBook.getAsks()),
                Lists.newArrayList(orderBook.getBids()),
                true);
    }

    /**
     * Adapt a JsonNode to a Stream of limit orders, the node past in here should be the body of a
     * a/b/as/bs key.
     */
    public static Stream<LimitOrder> adaptFuturesLimitOrders(
            Instrument instrument, Order.OrderType orderType, ArrayNode node, Date timestamp) {
        if (node == null || !node.isArray()) {
            return Stream.empty();
        }
        return Streams.stream(node.elements())
                .map(jsonNode -> adaptFututuresSnapshotLimitOrder(instrument, orderType, jsonNode, timestamp));
    }

    /**
     * Adapt a JsonNode containing two decimals into a LimitOrder
     */
    public static LimitOrder adaptFututuresSnapshotLimitOrder(
            Instrument instrument, Order.OrderType orderType, JsonNode node, Date timestamp) {
        if (node == null) {
            return null;
        }

        BigDecimal price = new BigDecimal(node.get("price").asText()).stripTrailingZeros();
        BigDecimal volume = new BigDecimal(node.get("qty").asText()).stripTrailingZeros();
        return new LimitOrder(orderType, volume, instrument, null, timestamp, price);
    }

    public static LimitOrder adaptFuturesLimitOrder(
            Instrument instrument, Order.OrderType orderType, ObjectNode node) {
        if (node == null) {
            return null;
        }
        BigDecimal price = new BigDecimal(node.get("price").asText()).stripTrailingZeros();
        BigDecimal volume = new BigDecimal(node.get("qty").asText()).stripTrailingZeros();
        Date timestamp = new Date(node.get("timestamp").asLong());
        return new LimitOrder(orderType, volume, instrument, null, timestamp, price);
    }

    private static void clearOrderbook(OrderBook orderBook) {
        orderBook.getBids().clear();
        orderBook.getAsks().clear();
    }
}
