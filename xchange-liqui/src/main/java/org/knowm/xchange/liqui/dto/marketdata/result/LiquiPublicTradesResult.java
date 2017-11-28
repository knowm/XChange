package org.knowm.xchange.liqui.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrades;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiPublicTradesResult extends LiquiResult<Map<String, LiquiPublicTrades>> {

    @JsonCreator
    public LiquiPublicTradesResult(final Map<String, LiquiPublicTrades> trades) {
        super(trades, null);
    }
}
