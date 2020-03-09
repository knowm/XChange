package info.bitrich.xchangestream.bitfinex.dto;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;

/** Created by Lukas Zaoralek on 8.11.17. */
public class BitfinexOrderbook {
  private Map<BigDecimal, BitfinexOrderbookLevel> asks;
  private Map<BigDecimal, BitfinexOrderbookLevel> bids;

  public BitfinexOrderbook(BitfinexOrderbookLevel[] levels) {
    createFromLevels(levels);
  }

  private void createFromLevels(BitfinexOrderbookLevel[] levels) {
    this.asks = new HashMap<>(levels.length / 2);
    this.bids = new HashMap<>(levels.length / 2);

    for (BitfinexOrderbookLevel level : levels) {

      if (level.getCount().compareTo(ZERO) == 0) continue;

      if (level.getAmount().compareTo(ZERO) > 0) bids.put(level.getPrice(), level);
      else
        asks.put(
            level.getPrice(),
            new BitfinexOrderbookLevel(
                level.getPrice(), level.getCount(), level.getAmount().abs()));
    }
  }

  public synchronized BitfinexDepth toBitfinexDepth() {
    SortedMap<BigDecimal, BitfinexOrderbookLevel> bitfinexLevelAsks = new TreeMap<>();
    SortedMap<BigDecimal, BitfinexOrderbookLevel> bitfinexLevelBids =
        new TreeMap<>(java.util.Collections.reverseOrder());

    for (Map.Entry<BigDecimal, BitfinexOrderbookLevel> level : asks.entrySet()) {
      bitfinexLevelAsks.put(level.getValue().getPrice(), level.getValue());
    }

    for (Map.Entry<BigDecimal, BitfinexOrderbookLevel> level : bids.entrySet()) {
      bitfinexLevelBids.put(level.getValue().getPrice(), level.getValue());
    }

    List<BitfinexLevel> askLevels = new ArrayList<>(asks.size());
    List<BitfinexLevel> bidLevels = new ArrayList<>(bids.size());
    for (Map.Entry<BigDecimal, BitfinexOrderbookLevel> level : bitfinexLevelAsks.entrySet()) {
      askLevels.add(level.getValue().toBitfinexLevel());
    }
    for (Map.Entry<BigDecimal, BitfinexOrderbookLevel> level : bitfinexLevelBids.entrySet()) {
      bidLevels.add(level.getValue().toBitfinexLevel());
    }

    return new BitfinexDepth(
        askLevels.toArray(new BitfinexLevel[askLevels.size()]),
        bidLevels.toArray(new BitfinexLevel[bidLevels.size()]));
  }

  public synchronized void updateLevel(BitfinexOrderbookLevel level) {

    Map<BigDecimal, BitfinexOrderbookLevel> side;

    // Determine side and normalize negative ask amount values
    BitfinexOrderbookLevel bidAskLevel = level;
    if (level.getAmount().compareTo(ZERO) < 0) {
      side = asks;
      bidAskLevel =
          new BitfinexOrderbookLevel(level.getPrice(), level.getCount(), level.getAmount().abs());
    } else {
      side = bids;
    }

    boolean shouldDelete = bidAskLevel.getCount().compareTo(ZERO) == 0;

    side.remove(bidAskLevel.getPrice());
    if (!shouldDelete) {
      side.put(bidAskLevel.getPrice(), bidAskLevel);
    }
  }
}
