package info.bitrich.xchangestream.okx;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.okx.dto.OkxSubscribeMessage;
import info.bitrich.xchangestream.okx.dto.enums.OkxInstType;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderDetails;

import java.util.List;

public class OkxStreamingTradeService implements StreamingTradeService {

    private final OkxStreamingService service;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public OkxStreamingTradeService(OkxStreamingService service) {
        this.service = service;
    }

    @Override
    public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {

        String channelName = "orders";

        OkxSubscribeMessage message = new OkxSubscribeMessage();
        message.setOp("subscribe");
        message.getArgs().add(new OkxSubscribeMessage.SubscriptionTopic(channelName, OkxInstType.ANY, null, OkexAdapters.adaptInstrumentId(instrument)));

        return service.subscribeChannel(channelName, message).flatMap(
                jsonNode -> {
                    List<OkexOrderDetails> okexOrderDetails = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderDetails.class));
                    return Observable.fromIterable(OkexAdapters.adaptUserTrades(okexOrderDetails).getUserTrades());
                }
        );
    }
}
