package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.dto.account.OkexPosition;

import java.util.List;

/**
 * <pre>
 * The implementation of position subscription, because the position action is not registered in the xchange-stream-core,
 * so when you need to pay attention, you need to convert StreamingExchange.class to OkexStreamExchange.class;
 * </pre>
 * <p> @Date : 2023/4/25 </p>
 * <p> @Project : XChange</p>
 *
 * <p> @author konbluesky </p>
 */
public class OkexStreamingPositionService {

    private final OkexStreamingService service;

    private final ExchangeMetaData exchangeMetaData;

    private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    public OkexStreamingPositionService(OkexStreamingService service, ExchangeMetaData exchangeMetaData) {
        this.service = service;
        this.exchangeMetaData = exchangeMetaData;
    }

    public Observable<OkexPosition> getPositions(Instrument instrument, Object... args) {
        String channelUniqueId = OkexStreamingService.POSITIONS + OkexAdapters.adaptInstrument(instrument);

        return service.subscribeChannel(channelUniqueId)
                      .filter(message -> message.has("data"))
                      .flatMap(jsonNode -> {
                          List<OkexPosition> okexPosition = mapper.treeToValue(jsonNode.get("data"), mapper.getTypeFactory()
                                                                                                           .constructCollectionType(List.class, OkexPosition.class));
                          return Observable.fromIterable(okexPosition);
                      });
    }
}
