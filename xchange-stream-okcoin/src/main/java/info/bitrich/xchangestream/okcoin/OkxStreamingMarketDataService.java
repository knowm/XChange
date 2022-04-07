package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxSubscribeMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexTrade;

import java.util.List;

public class OkxStreamingMarketDataService implements StreamingMarketDataService {
    private final OkxStreamingService service;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public OkxStreamingMarketDataService(OkxStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
        // TODO
        return StreamingMarketDataService.super.getTicker(instrument, args);
    }

    @Override
    public Observable<Trade> getTrades(Instrument instrument, Object... args) {
        String channelName = "trades";

        String instId = OkexAdapters.adaptCurrencyPairId(instrument);
        OkxSubscribeMessage.SubscriptionTopic topic = new OkxSubscribeMessage.SubscriptionTopic("trades", null, null, instId);
        OkxSubscribeMessage osm = new OkxSubscribeMessage();
        osm.setOp("subscribe");
        osm.getArgs().add(topic);

        return service
            .subscribeChannel(channelName, osm)
            .flatMap(jsonNode -> {
                List<OkexTrade> okexTradeList = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexTrade.class));
                return Observable.fromIterable(OkexAdapters.adaptTrades(okexTradeList, instrument).getTrades());
            });
    }

    @Override
    public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
        // TODO
        return StreamingMarketDataService.super.getOrderBook(instrument, args);
    }
}
