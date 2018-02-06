package org.knowm.xchange.bitmex.dto.marketdata;

import org.knowm.xchange.bitmex.dto.BitmexOrderBookL2;

import java.util.List;

/**
 * Data object representing depth from bitmex
 */
public class BitmexDepth {

    private final List<BitmexOrderBookL2> asks;
    private final List<BitmexOrderBookL2> bids;

    /**
     * Constructor
     *
     * @param asks
     * @param bids
     */
    public BitmexDepth(List<BitmexOrderBookL2> asks, List<BitmexOrderBookL2> bids) {

        this.asks = asks;
        this.bids = bids;

    }

    public List<BitmexOrderBookL2> getAsks() {

        return asks;
    }

    public List<BitmexOrderBookL2> getBids() {

        return bids;
    }

    @Override
    public String toString() {

        return "bitmexDepth [asks=" + asks + ", bids=" + bids + "]";
    }
}
