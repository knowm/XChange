package org.knowm.xchange.service.trade.params;

import java.util.Date;

public class DefaultCandleStickParamWithLimit extends DefaultCandleStickParam {
    private final int limit;

    public DefaultCandleStickParamWithLimit(Date startDate, Date endDate, long periodInMillis,
                                            int limit) {
        super(startDate, endDate, periodInMillis);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
