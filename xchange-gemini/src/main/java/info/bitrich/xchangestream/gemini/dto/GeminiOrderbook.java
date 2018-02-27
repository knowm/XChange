package info.bitrich.xchangestream.gemini.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.gemini.v1.GeminiAdapters;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.knowm.xchange.gemini.v1.GeminiAdapters.adaptOrders;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class GeminiOrderbook {
    private final SortedMap<BigDecimal, GeminiLimitOrder> asks;
    private final SortedMap<BigDecimal, GeminiLimitOrder> bids;
    private final BigDecimal zero = new BigDecimal(0);

    private final CurrencyPair currencyPair;

    public GeminiOrderbook(CurrencyPair currencyPair) {
        asks = new TreeMap<>();
        bids = new TreeMap<>(java.util.Collections.reverseOrder());
        this.currencyPair = currencyPair;
    }

    public void createFromLevels(GeminiLimitOrder[] levels) {
        for (GeminiLimitOrder level : levels) {
            SortedMap<BigDecimal, GeminiLimitOrder> orderBookSide = level.getSide() == Order.OrderType.ASK ? asks : bids;
            orderBookSide.put(level.getPrice(), level);
        }
    }

    public void updateLevel(GeminiLimitOrder level) {
        SortedMap<BigDecimal, GeminiLimitOrder> orderBookSide = level.getSide() == Order.OrderType.ASK ? asks : bids;
        boolean shouldDelete = level.getAmount().compareTo(zero) == 0;
        BigDecimal price = new BigDecimal(level.getPrice().toString()); // copy of the price is required for thread safety (BigDecimal is not thread safe, and the hashcode can be affected by outside accesses of the value)
        orderBookSide.remove(price);
        if (!shouldDelete) {
            orderBookSide.put(price, level);
        }
    }

    public void updateLevels(GeminiLimitOrder[] levels) {
        for (GeminiLimitOrder level : levels) {
            updateLevel(level);
        }
    }

    public OrderBook toOrderbook() {
        GeminiLimitOrder[] askLevels = asks.values().toArray(new GeminiLimitOrder[asks.size()]);
        GeminiLimitOrder[] bidLevels = bids.values().toArray(new GeminiLimitOrder[bids.size()]);
        GeminiAdapters.OrdersContainer askOrdersContainer = adaptOrders(askLevels, currencyPair, Order.OrderType.ASK);
        GeminiAdapters.OrdersContainer bidOrdersContainer = adaptOrders(bidLevels, currencyPair, Order.OrderType.BID);

        return new OrderBook(null, askOrdersContainer.getLimitOrders(), bidOrdersContainer.getLimitOrders());
    }
}
