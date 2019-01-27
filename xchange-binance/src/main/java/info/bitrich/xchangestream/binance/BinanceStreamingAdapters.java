package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.core.OrderStatusChange;
import info.bitrich.xchangestream.core.OrderStatusChangeType;

class BinanceStreamingAdapters {

    static OrderStatusChange adaptOrderStatusChange(ExecutionReportBinanceUserTransaction r) {
        return OrderStatusChange.create()
            .orderId(Long.toString(r.getOrderId()))
            .type(r.getExecutionType().equals(ExecutionType.NEW)
                ? OrderStatusChangeType.OPENED
                : OrderStatusChangeType.CLOSED
            )
            .timestamp(r.getEventTime())
            .build();
    }
}