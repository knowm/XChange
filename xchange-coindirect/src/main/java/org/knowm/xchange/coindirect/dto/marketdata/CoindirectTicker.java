package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CoindirectTicker {
    public final List<CoindirectTick> data;
    public final CoindirectTickMetadata metaData;

    public CoindirectTicker(@JsonProperty("data") List<CoindirectTick> data, @JsonProperty("metaData") CoindirectTickMetadata metaData) {
        this.data = data;
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "CoindirectTicker{" +
                "data=" + data +
                ", metaData=" + metaData +
                '}';
    }
}
