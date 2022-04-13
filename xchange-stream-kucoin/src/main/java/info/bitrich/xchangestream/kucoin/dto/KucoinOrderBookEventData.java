package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.List;

@ToString
public class KucoinOrderBookEventData {

    @JsonProperty("sequenceStart")
    public long sequenceStart;

    @JsonProperty("sequenceEnd")
    public long sequenceEnd;

    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("changes")
    public KucoinOrderBookChanges changes;

    public void update(CurrencyPair currencyPair, OrderBook orderBook) {
        update(currencyPair, orderBook, Order.OrderType.BID, changes.bids);
        update(currencyPair, orderBook, Order.OrderType.ASK, changes.asks);
    }

    private void update(CurrencyPair currencyPair, OrderBook orderBook, Order.OrderType orderType, List<List<String>> changes) {
        for (List<String> change : changes) {
            String price = change.get(0);
            if (!"0".equals(price)) {
                String size = change.get(1);
                LimitOrder limitOrder = new LimitOrder(orderType, new BigDecimal(size), currencyPair, null, null, new BigDecimal(price));
                orderBook.update(limitOrder);
            }
        }
    }
}
