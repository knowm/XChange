package info.bitrich.xchangestream.kraken;

import com.google.common.collect.Lists;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Kraken streaming adapters
 */
public class KrakenStreamingAdapters {

    static final String ASK_SNAPSHOT = "as";
    static final String ASK_UPDATE = "a";

    static final String BID_SNAPSHOT = "bs";
    static final String BID_UPDATE = "b";

    private static final List<String> BID_KEYS = Lists.newArrayList(BID_SNAPSHOT, BID_UPDATE);
    private static final List<String> ASK_KEYS = Lists.newArrayList(ASK_SNAPSHOT, ASK_UPDATE);

    public static OrderBook adaptOrderbookMessage(OrderBook orderBook, Instrument instrument, List<Object> messageAsList) {

        for (Object messageAsListItem : messageAsList) {
            // Any non map field in this message is irrelevant.
            if (Map.class.isAssignableFrom(messageAsListItem.getClass())) {
                Map<String, List<List<String>>> messageMap = (Map<String, List<List<String>>>) messageAsListItem;
                for (String key : BID_KEYS) {
                    for (LimitOrder o : adaptLimitOrders(instrument, Order.OrderType.BID, messageMap.get(key))) {
                        orderBook.update(o);
                    }
                }
                for (String key : ASK_KEYS) {
                    for (LimitOrder o : adaptLimitOrders(instrument, Order.OrderType.ASK, messageMap.get(key))) {
                        orderBook.update(o);
                    }
                }
            }
        }
        return new OrderBook(orderBook.getTimeStamp(), Lists.newArrayList(orderBook.getAsks()), Lists.newArrayList(orderBook.getBids()),true);

    }


    /**
     * Adapt a of orderbook orders into LimitOrders
     */
    public static List<LimitOrder> adaptLimitOrders(Instrument instrument, Order.OrderType orderType, List<List<String>> values) {
        if (values == null){
            return Collections.EMPTY_LIST;
        }
        return values.stream()
                .map(wsOrder -> adaptLimitOrder(instrument, orderType, wsOrder))
                .collect(Collectors.toList());
    }

    /**
     * Adapt a single order entry for the book message into a LimitOrder
     */
    public static LimitOrder adaptLimitOrder(Instrument instrument, Order.OrderType orderType, List<String> list) {
        BigDecimal volume = new BigDecimal(list.get(1)).stripTrailingZeros();
        return new LimitOrder(orderType, volume, instrument, null, null, new BigDecimal(list.get(0)).stripTrailingZeros());
    }
}
