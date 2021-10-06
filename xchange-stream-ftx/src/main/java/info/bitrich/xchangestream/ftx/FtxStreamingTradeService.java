package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class FtxStreamingTradeService implements StreamingTradeService {

    private final Flowable<JsonNode> fills;
    private final Flowable<JsonNode> orders;

    public FtxStreamingTradeService(FtxStreamingService service) {
        this.fills = service.subscribeChannel("fills");
        this.orders = service.subscribeChannel("orders");
    }

    @Override
    public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {

        return fills
                .filter(jsonNode -> jsonNode.hasNonNull("data"))
                .filter(jsonNode -> new CurrencyPair(jsonNode.get("data").get("market").asText()).equals(currencyPair))
                .map(FtxStreamingAdapters::adaptUserTrade);
    }

    @Override
    public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {

        return orders
                .filter(jsonNode -> jsonNode.hasNonNull("data"))
                .filter(jsonNode -> new CurrencyPair(jsonNode.get("data").get("market").asText()).equals(currencyPair))
                .map(FtxStreamingAdapters::adaptOrders);
    }
}
