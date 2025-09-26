package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.kraken.dto.common.ChannelType;
import info.bitrich.xchangestream.kraken.dto.response.KrakenDataMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenExecutionsMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenExecutionsMessage.KrakenExecutionType;
import info.bitrich.xchangestream.kraken.dto.response.KrakenMessage.KrakenMessageType;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.dto.trade.UserTrade;

public class KrakenStreamingTradeService implements StreamingTradeService {

  private final KrakenStreamingService service;

  public KrakenStreamingTradeService(KrakenStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<UserTrade> getUserTrades() {
    return service
        .subscribeChannel(ChannelType.USER_TRADES.getValue())
        .filter(krakenMessage -> krakenMessage.getType() == KrakenMessageType.UPDATE)
        .map(KrakenExecutionsMessage.class::cast)
        .filter(krakenExecutionsMessage -> !krakenExecutionsMessage.getData().isEmpty())
        .map(KrakenDataMessage::getPayload)
        .filter(payload -> payload.getKrakenExecutionType() == KrakenExecutionType.TRADE)
        .map(KrakenStreamingAdapters::toUserTrade);
  }
}
