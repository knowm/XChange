package org.knowm.xchange.liqui.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import java.util.Map;

public class LiquiInfoResult extends LiquiResult<Map<String, LiquiPairInfo>> {

    private final long serverTime;

    public LiquiInfoResult(@JsonProperty("server_time") long serverTime, @JsonProperty("pairs") Map<String, LiquiPairInfo> pairInfo) {
        super(pairInfo);
        this.serverTime = serverTime;
    }

    public long getServerTime() {
        return this.serverTime;
    }
}
