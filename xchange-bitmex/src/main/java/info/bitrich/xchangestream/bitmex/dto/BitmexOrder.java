package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;


public class BitmexOrder extends BitmexMarketDataEvent {

    public static final String ASK_SIDE = "Sell";
    public static final String BID_SIDE = "Buy";

    private String orderID;

    private int account;

    private String side;

    private double price;

    private String ordType;

    private String ordStatus;

    private String clOrdID;

    private long orderQty;

    private long cumQty;

    private boolean workingIndicator;

    public boolean isNotWorking() {
        return !workingIndicator;
    }

    @JsonCreator
    public BitmexOrder(@JsonProperty("symbol") String symbol,
                       @JsonProperty("timestamp") String timestamp,
                       @JsonProperty("orderID") String orderID,
                       @JsonProperty("account") int account,
                       @JsonProperty("side") String side,
                       @JsonProperty("price") double price,
                       @JsonProperty("ordType") String ordType,
                       @JsonProperty("ordStatus") String ordStatus,
                       @JsonProperty("clOrdID") String clOrdID,
                       @JsonProperty("orderQty") long orderQty,
                       @JsonProperty("cumQty") long cumQty,
                       @JsonProperty("workingIndicator") boolean workingIndicator) {
        super(symbol, timestamp);
        this.orderID = orderID;
        this.account = account;
        this.side = side;
        this.price = price;
        this.ordType = ordType;
        this.ordStatus = ordStatus;
        this.clOrdID = clOrdID;
        this.orderQty = orderQty;
        this.cumQty = cumQty;
        this.workingIndicator = workingIndicator;
    }

    public Order.OrderType getOrderSide() {
        return side.equals(ASK_SIDE) ? Order.OrderType.ASK : Order.OrderType.BID;
    }

    public Order toOrder() {
        if (ordType.equals("Limit")) {
            LimitOrder.Builder builder = new LimitOrder.Builder();
            builder.id(orderID)
                    .orderType(getOrderSide())
                    .limitPrice(new BigDecimal(price))
                    .originalAmount(new BigDecimal(orderQty))
                    .cumulativeAmount(new BigDecimal(cumQty))

            if (ordStatus.equals("Rejected") || ordStatus.equals("N/A")) {
                return builder.orderStatus(Order.OrderStatus.REJECTED).build();
            }
            return builder.build();
        }
//        builder.id(orderID)
//                .orderType(getOrderSide())
//                .limitPrice(price)
//                .
//
//        return new LimitOrder()
//
//        Order.Status status = Order.Status.fromName(ordStatus);
//        if (status == FAILED ||
//                status == UNKNOW) {
//            log.error("Got invalid order: {}", this.toString());
//            return Order.builder()
//                    .id(orderID)
//                    .clOrdID(clOrdID)
//                    .account(account)
//                    .status(status)
//                    .committedVol(committedVol)
//                    .build();
//        }
//        Order order = Order.builder()
//                .id(orderID)
//                .clOrdID(clOrdID)
//                .account(account)
//                .status(status)
//                .submittedVol(submittedVol)
//                .committedVol(committedVol)
//                .build();
//        if (status == Order.Status.NEW) {
//            order.price(price)
//                    .tradeSide(side.equals("Buy") ? Order.TradeSide.OPEN_LONG : Order.TradeSide.OPEN_SHORT)
//                    .type(Order.OrderType.fromName(ordType));
//        }
//        return order;
    }
}
