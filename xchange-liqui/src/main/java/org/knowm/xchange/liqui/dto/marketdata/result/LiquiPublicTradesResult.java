package org.knowm.xchange.liqui.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrades;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import java.util.Map;

public class LiquiPublicTradesResult extends LiquiResult<Map<String, LiquiPublicTrades>> {

    @JsonCreator
    public LiquiPublicTradesResult(final Map<String, LiquiPublicTrades> trades) {
        super(trades);
    }
}
