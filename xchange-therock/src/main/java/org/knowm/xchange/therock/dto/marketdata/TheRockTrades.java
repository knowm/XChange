package org.knowm.xchange.therock.dto.marketdata;

import java.util.Arrays;

public class TheRockTrades {

    private final int count;
    private final TheRockTrade[] trades;

    public TheRockTrades(TheRockTrade[] trades) {

        this.count = trades.length;
        this.trades = trades;
    }

    public int getCount() {
        return count;
    }

    public TheRockTrade[] getTrades() {
        return trades;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("TheRockTrades [count=");
        builder.append(count);
        builder.append(", trades=");
        builder.append(Arrays.toString(trades));
        builder.append("]");
        return builder.toString();
    }
}
