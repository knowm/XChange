package info.bitrich.xchangestream.bitfinex.dto;

import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Lukas Zaoralek on 8.11.17.
 */
public class BitfinexOrderbook {
    private Map<BigDecimal, BitfinexOrderbookLevel> asks;
    private Map<BigDecimal, BitfinexOrderbookLevel> bids;
    private BigDecimal zero = new BigDecimal(0);

    public BitfinexOrderbook(BitfinexOrderbookLevel[] levels) {
        createFromLevels(levels);
    }

    public BitfinexOrderbook(BitfinexOrderbookLevel[] asks, BitfinexOrderbookLevel[] bids) {
        this.asks = new HashMap<>(asks.length);
        this.bids = new HashMap<>(bids.length);

        for (BitfinexOrderbookLevel level : asks) {
            this.asks.put(level.getOrderId(), level);
        }

        for (BitfinexOrderbookLevel level : bids) {
            this.bids.put(level.getOrderId(), level);
        }
    }

    private void createFromLevels(BitfinexOrderbookLevel[] levels) {
        this.asks = new HashMap<>(levels.length/2);
        this.bids = new HashMap<>(levels.length/2);

        BigDecimal zero = new BigDecimal(0);
        for (BitfinexOrderbookLevel level : levels) {
            if (level.getAmount().compareTo(zero) > 0) bids.put(level.getOrderId(), level);
            else asks.put(level.getOrderId(), level);
        }
    }

    public BitfinexDepth toBitfinexDepth() {
        SortedMap<BigDecimal, BitfinexOrderbookLevel> bitfinexLevelAsks = new TreeMap<>();
        SortedMap<BigDecimal, BitfinexOrderbookLevel> bitfinexLevelBids = new TreeMap<>(java.util.Collections.reverseOrder());

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

        return new BitfinexDepth(askLevels.toArray(new BitfinexLevel[askLevels.size()]),
                bidLevels.toArray(new BitfinexLevel[bidLevels.size()]));
    }

    public void updateLevel(BitfinexOrderbookLevel level) {
        Map<BigDecimal, BitfinexOrderbookLevel> side = level.getAmount().compareTo(zero) > 0 ? bids : asks;
        boolean shouldDelete = level.getPrice().compareTo(zero) == 0;

        side.remove(level.orderId);
        if (!shouldDelete) {
            side.put(level.orderId, level);
        }
    }
}
