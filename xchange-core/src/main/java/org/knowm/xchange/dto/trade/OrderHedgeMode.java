package org.knowm.xchange.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.dto.Order;

@Getter
@AllArgsConstructor
public enum OrderHedgeMode  implements  Order.IOrderFlags {
    ONEWAY,
    TWOWAY;
}
