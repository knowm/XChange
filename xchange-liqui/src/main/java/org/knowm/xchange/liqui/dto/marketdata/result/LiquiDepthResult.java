package org.knowm.xchange.liqui.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicOrders;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import java.util.Map;

public class LiquiDepthResult extends LiquiResult<Map<String, LiquiPublicOrders>> {

    @JsonCreator
    public LiquiDepthResult(final Map<String, LiquiPublicOrders> orders) {
        super(orders);
    }
}
