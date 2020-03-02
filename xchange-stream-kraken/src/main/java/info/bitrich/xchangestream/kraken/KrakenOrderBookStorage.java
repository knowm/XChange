package info.bitrich.xchangestream.kraken;

import static java.util.Collections.reverseOrder;

import info.bitrich.xchangestream.kraken.dto.KrakenOrderBook;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

/** @author pchertalev */
public class KrakenOrderBookStorage {

  private TreeMap<BigDecimal, KrakenPublicOrder> asks;
  private TreeMap<BigDecimal, KrakenPublicOrder> bids;

  private final int maxDepth;

  /**
   * Constructor is used for snapshots only
   *
   * @param orderBookUpdate order book update items
   * @param maxDepth order book size can rise up, so depth value need for order book truncating
   */
  public KrakenOrderBookStorage(KrakenOrderBook orderBookUpdate, int maxDepth) {
    this.maxDepth = maxDepth;
    createFromLevels(orderBookUpdate);
  }

  /**
   * Create order book from snapshot
   *
   * @param orderBookUpdate order book snapshot
   */
  private void createFromLevels(KrakenOrderBook orderBookUpdate) {
    this.asks = new TreeMap<>(BigDecimal::compareTo);
    this.bids = new TreeMap<>(reverseOrder(BigDecimal::compareTo));

    for (KrakenPublicOrder orderBookItem : orderBookUpdate.getAsk()) {
      asks.put(orderBookItem.getPrice(), orderBookItem);
    }

    for (KrakenPublicOrder orderBookItem : orderBookUpdate.getBid()) {
      bids.put(orderBookItem.getPrice(), orderBookItem);
    }
  }

  /**
   * Converting to Kraken XChange format
   *
   * @return
   */
  public synchronized KrakenDepth toKrakenDepth() {
    List<KrakenPublicOrder> askLimits = new ArrayList<>(asks.values());
    List<KrakenPublicOrder> bidLimits = new ArrayList<>(bids.values());
    return new KrakenDepth(askLimits, bidLimits);
  }

  /**
   * Order book incremental update
   *
   * @param orderBookUpdate order book update
   */
  public synchronized void updateOrderBook(KrakenOrderBook orderBookUpdate) {
    updateOrderBookItems(orderBookUpdate.getAsk(), asks);
    updateOrderBookItems(orderBookUpdate.getBid(), bids);
  }

  private void updateOrderBookItems(
      KrakenPublicOrder[] itemsToUpdate, Map<BigDecimal, KrakenPublicOrder> localItems) {
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
