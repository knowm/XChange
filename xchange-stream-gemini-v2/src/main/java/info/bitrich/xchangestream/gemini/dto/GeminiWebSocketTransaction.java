package info.bitrich.xchangestream.gemini.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Adapted from V1 by Max Gao on 01-09-2021
 */
public class GeminiWebSocketTransaction {
    private String type;
    private String symbol;
    private String[][] changes;
    private JsonNode trades;
    private JsonNode auctionEvents;

    public GeminiWebSocketTransaction(
            @JsonProperty("type") String type,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("changes") String[][] changes,
            @JsonProperty("trades") JsonNode trades,
            @JsonProperty("auction_events") JsonNode auctionEvents) {
        this.type = type;
        this.symbol = symbol;
        this.changes = changes;
        this.trades = trades;
        this.auctionEvents = auctionEvents;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public String[][] getChanges() {
        return changes;
    }

    public JsonNode getTrades() {
        return trades;
    }

    public JsonNode getAuctionEvents() {
        return auctionEvents;
    }

    private List<LimitOrder> geminiOrderBookChanges(
            String side,
            Order.OrderType orderType,
            CurrencyPair currencyPair,
            String[][] changes,
            SortedMap<BigDecimal, BigDecimal> sideEntries,
            int maxDepth) {
        if (changes.length == 0) {
            return Collections.emptyList();
        }

        if (sideEntries == null) {
            return Collections.emptyList();
        }

        for (String[] level : changes) {
            if (level.length == 3 && !level[0].equals(side)) {
                continue;
            }

            BigDecimal price = new BigDecimal(level[level.length - 2]);
            BigDecimal volume = new BigDecimal(level[level.length - 1]);
            sideEntries.put(price, volume);
        }

        Stream<Map.Entry<BigDecimal, BigDecimal>> stream =
                sideEntries.entrySet().stream()
                        .filter(level -> level.getValue().compareTo(BigDecimal.ZERO) != 0);
        if (maxDepth != 0) {
            stream = stream.limit(maxDepth);
        }
        return stream
                .map(
                        level ->
                                new LimitOrder(
                                        orderType, level.getValue(), currencyPair, "0", null, level.getKey()))
                .collect(Collectors.toList());
    }


    public OrderBook toOrderBook(
            SortedMap<BigDecimal, BigDecimal> bids,
            SortedMap<BigDecimal, BigDecimal> asks,
            int maxDepth,
            CurrencyPair currencyPair) {
        // For efficiency, we go straight to XChange format
        List<LimitOrder> orderBookBids =
                geminiOrderBookChanges(
                        "buy",
                        Order.OrderType.BID,
                        currencyPair,
                        changes != null ? changes : null,
                        bids,
                        maxDepth);
        List<LimitOrder> orderBookAsks =
                geminiOrderBookChanges(
                        "sell",
                        Order.OrderType.ASK,
                        currencyPair,
                        changes != null ? changes : null,
                        asks,
                        maxDepth);
        return new OrderBook(
                null,
                orderBookAsks,
                orderBookBids,
                false);
    }
}
