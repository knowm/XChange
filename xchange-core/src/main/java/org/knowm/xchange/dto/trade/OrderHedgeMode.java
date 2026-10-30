package org.knowm.xchange.dto.trade;

import org.knowm.xchange.dto.Order;

public enum OrderHedgeMode  implements  Order.IOrderFlags {
    ONEWAY,
    TWOWAY;
}
