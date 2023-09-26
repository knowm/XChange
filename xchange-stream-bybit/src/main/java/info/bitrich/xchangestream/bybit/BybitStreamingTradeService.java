package info.bitrich.xchangestream.bybit;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bybit.dto.BybitUserTradeResponseDto;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamingTradeService implements StreamingTradeService {

  private static final String EXECUTION_CHANNEL = "execution";
  private final BybitStreamingService streamingService;

  private final ObjectMapper objectMapper = StreamingObjectMapperHelper.getObjectMapper();

  public BybitStreamingTradeService(BybitStreamingService streamingService) {
    this.streamingService = streamingService;
  }
  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    return getUserTrades();
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getUserTrades();
  }

  @Override
  public Observable<UserTrade> getUserTrades() {
    return streamingService
        .subscribeChannel(EXECUTION_CHANNEL)
        .filter(jsonNode -> jsonNode.has("topic"))
        .filter(jsonNode -> jsonNode.get("topic").asText().equals(EXECUTION_CHANNEL))
        .map(s -> objectMapper.treeToValue(s, BybitUserTradeResponseDto.class))
        .map(bybitUserTradeResponseDto -> BybitStreamingAdapters.adaptStreamingUserTradeList(bybitUserTradeResponseDto.getData()))
        .flatMap(Observable::fromIterable);
  }
}
