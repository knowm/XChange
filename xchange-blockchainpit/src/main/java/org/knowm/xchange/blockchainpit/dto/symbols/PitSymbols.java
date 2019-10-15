package org.knowm.xchange.blockchainpit.dto.symbols;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.blockchainpit.dto.PitResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// {
//  "seqnum":1,
//  "event":"snapshot",
//  "channel":"symbols",
//  "symbols":{
//    "BTC-PAX":{
//      "base_currency":"BTC",
//      "base_currency_scale":8,
//      "counter_currency":"PAX",
//      "counter_currency_scale":8,
//      "min_price_increment":10000000,
//      "min_price_increment_scale":8,
//      "min_order_size":50000,
//      "min_order_size_scale":8,
//      "max_order_size":0,
//      "max_order_size_scale":8,
//      "lot_size":1,
//      "lot_size_scale":8,
//      "status":"open",
//      "id":16,
//      "auction_price":0.0,
//      "auction_size":0.0,
//      "auction_time":"",
//      "imbalance":0.0
//    }
//  }
// }
public class PitSymbols extends PitResponse {
    private final Map<String, PitSymbol> symbols;

    public PitSymbols(
            @JsonProperty("seqnum") Long seqnum,
            @JsonProperty("event") String event,
            @JsonProperty("channel") String channel,
            @JsonProperty("symbols") Map<String, PitSymbol> symbols
    ) {
        super(seqnum, event, channel);
        this.symbols = symbols;
    }

    public Map<String, PitSymbol> getSymbols() {
        return symbols;
    }
}
