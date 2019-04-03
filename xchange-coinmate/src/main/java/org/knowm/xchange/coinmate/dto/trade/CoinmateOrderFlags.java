package org.knowm.xchange.coinmate.dto.trade;

import org.knowm.xchange.dto.Order;

public enum CoinmateOrderFlags implements Order.IOrderFlags {
    /**
     * Stop loss price.
     */
    STOP,
    /**
     * Flag indicating that order should be created as hidden.
     */
    HIDDEN,
    /**
     * In case the flag is set: if limit order is not fully settled immediately the remaining part of the order is cancelled at the end of request.
     */
    IMMEDIATE_OR_CANCEL
}
