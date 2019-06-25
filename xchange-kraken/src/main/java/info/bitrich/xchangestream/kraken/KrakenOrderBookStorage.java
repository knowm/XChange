package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.kraken.dto.KrakenOrderBook;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.reverseOrder;

/**
 * @author pchertalev
 */
public class KrakenOrderBookStorage {

    private TreeMap<BigDecimal, KrakenPublicOrder> asks;
    private TreeMap<BigDecimal, KrakenPublicOrder> bids;

    private final int maxDepth;

    public KrakenOrderBookStorage(KrakenOrderBook orderbookTransaction, int maxDepth) {
        this.maxDepth = maxDepth;
        createFromLevels(orderbookTransaction);
    }

    private void createFromLevels(KrakenOrderBook orderbookTransaction) {
        this.asks = new TreeMap<>(BigDecimal::compareTo);
        this.bids = new TreeMap<>(reverseOrder(BigDecimal::compareTo));

        for (KrakenPublicOrder orderBookItem : orderbookTransaction.getAsk()) {
            asks.put(orderBookItem.getPrice(), orderBookItem);
        }

        for (KrakenPublicOrder orderBookItem : orderbookTransaction.getBid()) {
            bids.put(orderBookItem.getPrice(), orderBookItem);
        }
    }
    public KrakenDepth toKrakenDepth() {
        List<KrakenPublicOrder> askLimits = new ArrayList<>(asks.values());
        List<KrakenPublicOrder> bidLimits = new ArrayList<>(bids.values());
        return new KrakenDepth(askLimits, bidLimits);
    }

    public void updateOrderBook(KrakenOrderBook orderBookTransaction) {
        updateOrderBookItems(orderBookTransaction.getAsk(), asks);
        updateOrderBookItems(orderBookTransaction.getBid(), bids);
    }

    private void updateOrderBookItems(KrakenPublicOrder[] itemsToUpdate, Map<BigDecimal, KrakenPublicOrder> localItems) {
        for (KrakenPublicOrder askToUpdate : itemsToUpdate) {
            localItems.remove(askToUpdate.getPrice());
            if (askToUpdate.getVolume().compareTo(BigDecimal.ZERO) != 0) {
                localItems.put(askToUpdate.getPrice(), askToUpdate);
            }
        }
        truncate(asks, maxDepth);
        truncate(bids, maxDepth);
    }

    private void truncate(TreeMap items, int maxSize) {
        while (items.size() > maxSize) {
            items.remove(items.lastKey());
        }
    }

}
