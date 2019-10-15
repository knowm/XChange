package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.blockchainpit.dto.PitResponse;

import java.util.ArrayList;

// {
//  "seqnum": 2,
//  "event": "snapshot",
//  "channel": "l3",
//  "symbol": "BTC-USD",
//  "bids": [
//    {
//      "id": "1234",
//      "px": 8723.45,
//      "qty": 1.10
//    },
//    {
//      "id": "1235",
//      "px": 8723.45,
//      "qty": 0.35
//    },
//    {
//      "id": "234",
//      "px": 8124.45,
//      "qty": 123.45
//    }
//  ],
//  "asks": [
//    {
//      "id": "2222",
//      "px": 8730.0,
//      "qty": 0.65
//    },
//    {
//      "id": "2225",
//      "px": 8730.0,
//      "qty": 0.9
//    },
//    {
//      "id": "2343",
//      "px": 8904.45,
//      "qty": 8.66
//    },
//    {
//      "id": "2353",
//      "px": 8904.45,
//      "qty": 5.0
//    }
//  ]
// }

public final class PitOrderbookL3 extends PitResponse {
    private final String symbol;
    private final ArrayList<PitDepthItemL3> bids;
    private final ArrayList<PitDepthItemL3> asks;

    public PitOrderbookL3(
        @JsonProperty("seqnum") Long seqnum,
        @JsonProperty("event") String event,
        @JsonProperty("channel") String channel,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("bids") ArrayList<PitDepthItemL3> bids,
        @JsonProperty("asks") ArrayList<PitDepthItemL3> asks
    ) {
        super(seqnum, event, channel);
        this.symbol = symbol;
        this.bids = bids;
        this.asks = asks;
    }

    public String getSymbol() {
        return symbol;
    }

    public ArrayList<PitDepthItemL3> getBids() {
        return bids;
    }

    public ArrayList<PitDepthItemL3> getAsks() {
        return asks;
    }
}
