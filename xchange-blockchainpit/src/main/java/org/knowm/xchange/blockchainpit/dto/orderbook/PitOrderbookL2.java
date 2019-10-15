package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.blockchainpit.dto.PitResponse;

import java.util.ArrayList;

/**
 * {
 *  "seqnum": 2,
 *  "event": "snapshot",
 *  "channel": "l2",
 *  "symbol": "BTC-USD",
 *  "bids": [
 *    {
 *      "px": 8723.45,
 *      "qty": 1.45,
 *      "num": 2
 *    },
 *    {
 *      "px": 8124.45,
 *      "qty": 123.45,
 *      "num": 1
 *    }
 *  ],
 *  "asks": [
 *    {
 *      "px": 8730.0,
 *      "qty": 1.55,
 *      "num": 2
 *    },
 *    {
 *      "px": 8904.45,
 *      "qty": 13.66,
 *      "num": 2
 *    }
 *  ]
 * }
 */
public final class PitOrderbookL2 extends PitResponse {
    private final String symbol;
    private final ArrayList<PitDepthItemL2> bids;
    private final ArrayList<PitDepthItemL2> asks;

    public PitOrderbookL2(
        @JsonProperty("seqnum") Long seqnum,
        @JsonProperty("event") String event,
        @JsonProperty("channel") String channel,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("bids") ArrayList<PitDepthItemL2> bids,
        @JsonProperty("asks") ArrayList<PitDepthItemL2> asks
    ) {
        super(seqnum, event, channel);
        this.symbol = symbol;
        this.bids = bids;
        this.asks = asks;
    }

    public String getSymbol() {
        return symbol;
    }

    public ArrayList<PitDepthItemL2> getBids() {
        return bids;
    }

    public ArrayList<PitDepthItemL2> getAsks() {
        return asks;
    }
}
