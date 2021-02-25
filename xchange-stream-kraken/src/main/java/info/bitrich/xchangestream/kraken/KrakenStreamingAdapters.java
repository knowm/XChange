package info.bitrich.xchangestream.kraken;

import com.google.common.collect.Lists;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
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

    public static List<OrderBookUpdate> adaptOrderbookUpdates(Instrument instrument, Order.OrderType orderType, List<List<String>> values) {
        if (values == null) {
            return Lists.newArrayList();
        }
        return values.stream()
                .map(wsOrder -> adaptOrderbookUpdate(instrument, orderType, wsOrder))
                .collect(Collectors.toList());
    }

    public static OrderBookUpdate adaptOrderbookUpdate(Instrument instrument, Order.OrderType orderType, List<String> list) {
        BigDecimal volume = new BigDecimal(list.get(1)).stripTrailingZeros();
        return new OrderBookUpdate(
                orderType,
                volume,
                instrument,
                new BigDecimal(list.get(0)).stripTrailingZeros(),
                null,
                volume
        );
    }
}
