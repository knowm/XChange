package info.bitrich.xchangestream.ftx;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.instrument.Instrument;

public class FtxStreamingTradeService implements StreamingTradeService {

    private final Flowable<JsonNode> fills;
    private final Flowable<JsonNode> orders;

    public FtxStreamingTradeService(FtxStreamingService service) {
        this.fills = service.subscribeChannel("fills");
        this.orders = service.subscribeChannel("orders");
    }

    @Override
    public Flowable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
        String market = FtxAdapters.adaptInstrumentToFtxMarket(instrument);
        return fills
                .filter(jsonNode -> jsonNode.hasNonNull("data"))
                .filter(jsonNode -> jsonNode.get("data").get("market").asText().equals(market))
                .map(FtxStreamingAdapters::adaptUserTrade);
    }

    @Override
    public Flowable<Order> getOrderChanges(Instrument instrument, Object... args) {
        String market = FtxAdapters.adaptInstrumentToFtxMarket(instrument);
        return orders
                .filter(jsonNode -> jsonNode.hasNonNull("data"))
                .filter(jsonNode -> jsonNode.get("data").get("market").asText().equals(market))
                .map(FtxStreamingAdapters::adaptOrders);
    }
}
