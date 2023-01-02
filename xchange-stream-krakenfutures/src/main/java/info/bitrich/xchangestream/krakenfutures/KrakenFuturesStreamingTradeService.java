package info.bitrich.xchangestream.krakenfutures;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.krakenfutures.dto.KrakenFuturesStreamingFillsDeltaResponse;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class KrakenFuturesStreamingTradeService implements StreamingTradeService {

    private final KrakenFuturesStreamingService streamingService;
    private final ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();

    public KrakenFuturesStreamingTradeService(KrakenFuturesStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @Override
    public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
        return streamingService.subscribeChannel(streamingService.FILLS)
                .filter(message-> message.has("feed") && message.has("fills"))
                .filter(message-> message.get("feed").asText().equals("fills"))
                .map(message-> KrakenFuturesStreamingAdapters.adaptUserTrades(objectMapper.treeToValue(message, KrakenFuturesStreamingFillsDeltaResponse.class)))
                .flatMapIterable(userTrades -> userTrades)
                .filter(userTrade -> userTrade.getInstrument().equals(instrument));
    }
}
