package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.knowm.xchange.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Kraken streaming adapters
 */
public class KrakenStreamingAdapters {

    static final String ASK_SNAPSHOT = "as";
    static final String ASK_UPDATE = "a";

    static final String BID_SNAPSHOT = "bs";
    static final String BID_UPDATE = "b";

    public static OrderBook adaptOrderbookMessage(
            OrderBook orderBook, Instrument instrument, ArrayNode arrayNode) {
        Streams.stream(arrayNode.elements())
                .filter(JsonNode::isObject)
                .forEach(
                        currentNode -> {
                            if(currentNode.has(BID_SNAPSHOT) || currentNode.has(ASK_SNAPSHOT)) {
                                //Clear orderbook if receiving snapshot
                                clearOrderbook(orderBook);

                                adaptLimitOrders(instrument, Order.OrderType.BID, currentNode.get(BID_SNAPSHOT))
                                        .forEach(orderBook::update);
                                adaptLimitOrders(instrument, Order.OrderType.ASK, currentNode.get(ASK_SNAPSHOT))
                                        .forEach(orderBook::update);

                            } else if(currentNode.has(BID_UPDATE) || currentNode.has(ASK_UPDATE)) {
                                adaptLimitOrders(instrument, Order.OrderType.BID, currentNode.get(BID_UPDATE))
                                        .forEach(orderBook::update);
                                adaptLimitOrders(instrument, Order.OrderType.ASK, currentNode.get(ASK_UPDATE))
                                        .forEach(orderBook::update);
                            }
                        });
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
    public static Stream<LimitOrder> adaptLimitOrders(
            Instrument instrument, Order.OrderType orderType, JsonNode node) {
        if (node == null || !node.isArray()) {
            return Stream.empty();
        }
        return Streams.stream(node.elements())
                .filter(JsonNode::isArray)
                .map(jsonNode -> adaptLimitOrder(instrument, orderType, jsonNode));
    }

    /**
     * Adapt a JsonNode containing two decimals into a LimitOrder
     */
    public static LimitOrder adaptLimitOrder(
            Instrument instrument, Order.OrderType orderType, JsonNode node) {
        if (node == null) {
            return null;
        }
        Iterator<JsonNode> iterator = node.elements();
        BigDecimal price = nextNodeAsDecimal(iterator);
        BigDecimal volume = nextNodeAsDecimal(iterator);
        Date timestamp = nextNodeAsDate(iterator);
        return new LimitOrder(orderType, volume, instrument, null, timestamp, price);
    }

    /**
     * Adapt an ArrayNode containing a ticker message into a Ticker
     */
    public static Ticker adaptTickerMessage(Instrument instrument, ArrayNode arrayNode) {
        return Streams.stream(arrayNode.elements())
                .filter(JsonNode::isObject)
                .map(
                        tickerNode -> {
                            Iterator<JsonNode> askIterator = tickerNode.get("a").iterator();
                            Iterator<JsonNode> bidIterator = tickerNode.get("b").iterator();
                            Iterator<JsonNode> closeIterator = tickerNode.get("c").iterator();
                            Iterator<JsonNode> volumeIterator = tickerNode.get("v").iterator();
                            Iterator<JsonNode> vwapIterator = tickerNode.get("p").iterator();
                            Iterator<JsonNode> lowPriceIterator = tickerNode.get("l").iterator();
                            Iterator<JsonNode> highPriceIterator = tickerNode.get("h").iterator();
                            Iterator<JsonNode> openPriceIterator = tickerNode.get("o").iterator();

                            // Move iterators forward here required, this ignores the first field if the desired
                            // value is in the second element.
                            vwapIterator.next();
                            volumeIterator.next();

                            return new Ticker.Builder()
                                    .open(nextNodeAsDecimal(openPriceIterator))
                                    .ask(nextNodeAsDecimal(askIterator))
                                    .bid(nextNodeAsDecimal(bidIterator))
                                    .last(nextNodeAsDecimal(closeIterator))
                                    .high(nextNodeAsDecimal(highPriceIterator))
                                    .low(nextNodeAsDecimal(lowPriceIterator))
                                    .vwap(nextNodeAsDecimal(vwapIterator))
                                    .volume(nextNodeAsDecimal(volumeIterator))
                                    .instrument(instrument)
                                    .build();
                        })
                .findFirst()
                .orElse(null);
    }

    /**
     * Adapt an JsonNode into a list of Trade
     */
    public static List<Trade> adaptTrades(Instrument instrument, JsonNode arrayNode) {
        return Streams.stream(arrayNode.elements())
                .filter(JsonNode::isArray)
                .flatMap(
                        innerNode ->
                                Streams.stream(innerNode.elements())
                                        .map(inner -> KrakenStreamingAdapters.adaptTrade(instrument, inner)))
                .collect(Collectors.toList());
    }

    /**
     * Adapt an JsonNode into a single Trade
     */
    public static Trade adaptTrade(Instrument instrument, JsonNode arrayNode) {
        if (arrayNode == null || !arrayNode.isArray()) {
            return null;
        }
        Iterator<JsonNode> iterator = arrayNode.iterator();
        return new Trade.Builder()
                .price(nextNodeAsDecimal(iterator))
                .originalAmount(nextNodeAsDecimal(iterator))
                .timestamp(nextNodeAsDate(iterator))
                .type(nextNodeAsOrderType(iterator))
                .instrument(instrument)
                .build();
    }

    /**
     * Checks if a iterator has next node and returns the value as a BigDecimal. Returns null if the
     * iterator has no next value or the given iterator is null.
     */
    private static BigDecimal nextNodeAsDecimal(Iterator<JsonNode> iterator) {
        if (iterator == null || !iterator.hasNext()) {
            return null;
        }
        return new BigDecimal(iterator.next().textValue()).stripTrailingZeros();
    }

    /**
     * Checks if a iterator has next node and returns the value as a Date using the long value as
     * timestamp. Returns null if the iterator has no next value or the given iterator is null.
     */
    private static Date nextNodeAsDate(Iterator<JsonNode> iterator) {
        if (iterator == null || !iterator.hasNext()) {
            return null;
        }
        return DateUtils.fromUnixTime(iterator.next().asLong());
    }

    /**
     * Checks if a iterator has next node and returns the value as a Date using the long value as
     * timestamp. Returns null if the iterator has no next value or the given iterator is null.
     */
    private static Order.OrderType nextNodeAsOrderType(Iterator<JsonNode> iterator) {
        if (iterator == null || !iterator.hasNext()) {
            return null;
        }
        return KrakenAdapters.adaptOrderType(KrakenType.fromString(iterator.next().textValue()));
    }

    private static void clearOrderbook(OrderBook orderBook){
        orderBook.getBids().clear();
        orderBook.getAsks().clear();
    }
}
