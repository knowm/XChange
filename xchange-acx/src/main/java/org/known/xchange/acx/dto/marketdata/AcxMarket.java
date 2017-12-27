package org.known.xchange.acx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AcxMarket {
    /** A timestamp in seconds since Epoch */
    public final Date at;
    public final AcxTicker ticker;

    public AcxMarket(
            @JsonProperty("at") Date at,
            @JsonProperty("ticker") AcxTicker ticker
    ) {
        this.at = at;
        this.ticker = ticker;
    }
}
