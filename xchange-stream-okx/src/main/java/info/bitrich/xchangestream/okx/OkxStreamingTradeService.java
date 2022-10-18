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
        if (args.length < 1 || !(args[0] instanceof OkxInstType)) {
            throw new IllegalArgumentException("Failed to get args[0] type  OkxInstType");
        }

        OkxInstType instType = (OkxInstType)args[0];

        String underlying = null;
        if (args.length >= 2) {
            if (args[1] instanceof String) {
                underlying = (String)args[1];
            } else if (args[1] == null) {
                underlying = null;
            } else {
                throw new IllegalArgumentException("Failed to get args[1] underlying type String");
            }
        }

        String instId = null;
        if (args.length >= 3) {
            if (args[2] instanceof String) {
                instId = (String)args[2];
            } else if (args[1] == null) {
                instId = null;
            } else {
                throw new IllegalArgumentException("Failed to get args[2] instrumentId type String");
            }
        }
        String channelName = "orders";

        OkxSubscribeMessage message = new OkxSubscribeMessage();
        message.setOp("subscribe");
        message.getArgs().add(new OkxSubscribeMessage.SubscriptionTopic(channelName, instType, underlying, instId));

        return service.subscribeChannel(channelName, message).flatMap(
                jsonNode -> {
                    List<OkexOrderDetails> okexOrderDetails = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory().constructCollectionType(List.class, OkexOrderDetails.class));
                    return Observable.fromIterable(OkexAdapters.adaptUserTrades(okexOrderDetails).getUserTrades());
                }
        );
    }
}
