package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.okex.dto.trade.OkexOrderDetails;

import java.util.List;

public class OkexStreamingTradeService implements StreamingTradeService {

    private final OkexStreamingService service;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public OkexStreamingTradeService(OkexStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
        String instId = OkexAdapters.adaptInstrument(instrument);
        String channelName = "orders";
        String channelUniqueId = channelName+"."+instId;

        return service.subscribeChannel(channelUniqueId, new OkexSubscribeMessage.SubscriptionTopic(channelName, OkexInstType.ANY, null, instId))
                .filter(message-> message.has("data"))
                .flatMap(jsonNode -> {
                    List<OkexOrderDetails> okexOrderDetails = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderDetails.class));
                    return Observable.fromIterable(OkexAdapters.adaptUserTrades(okexOrderDetails).getUserTrades());
                }
        );
    }
}
