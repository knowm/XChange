package info.bitrich.xchangestream.cexio;

import info.bitrich.xchangestream.cexio.dto.CexioWebSocketOrder;
import info.bitrich.xchangestream.cexio.dto.CexioWebSocketPair;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CexioAdapters {

    private static final int PRECISION = 3;
    private static final BigDecimal SATOSHI_SCALE = new BigDecimal("100000000");

    static Order adaptOrder(CexioWebSocketOrder order) {
        if (order.getType() != null) {
            return new CexioOrder(adaptOrderType(order.getType()),
                              adaptCurrencyPair(order.getPair()),
                              adaptAmount(order.getRemains()),
                              order.getId(),
                              order.getTime(),
                              order.getPrice(),
                              order.getFee(),
                              getOrderStatus(order));
        } else {
            return new CexioOrder(adaptCurrencyPair(order.getPair()),
                                  order.getId(),
                                  getOrderStatus(order),
                                  adaptAmount(order.getRemains()));
        }
    }

    private static CurrencyPair adaptCurrencyPair(CexioWebSocketPair pair) {
        return new CurrencyPair(pair.getSymbol1(), pair.getSymbol2());
    }

    private static Order.OrderType adaptOrderType(String type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case "buy":
                return Order.OrderType.BID;
            case "sell":
                return Order.OrderType.ASK;
            default:
                return null;
        }
    }

    private static BigDecimal adaptAmount(BigDecimal amount) {
        if (amount == null) {
            return null;
        }

        return amount.divide(SATOSHI_SCALE, PRECISION, RoundingMode.DOWN);
    }

    private static Order.OrderStatus getOrderStatus(CexioWebSocketOrder order) {
        Order.OrderStatus status;
        if (order.isCancel()) {
            status = Order.OrderStatus.CANCELED;
        } else if (order.getRemains().compareTo(BigDecimal.ZERO) == 0) {
            status = Order.OrderStatus.FILLED;
        } else if (order.getType() != null) {
            status = Order.OrderStatus.NEW;
        } else {
            status = Order.OrderStatus.PARTIALLY_FILLED;
        }
        return status;
    }
}
