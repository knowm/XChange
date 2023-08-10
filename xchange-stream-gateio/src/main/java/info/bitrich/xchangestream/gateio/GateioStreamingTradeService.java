package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;

public class GateioStreamingTradeService implements StreamingTradeService {

  private final GateioStreamingService service;

  public GateioStreamingTradeService(GateioStreamingService service) {
    this.service = service;
  }


  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_TRADES_CHANNEL, currencyPair)
        .map(GateioSingleUserTradeNotification.class::cast)
        .map(GateioStreamingAdapters::toUserTrade);
  }

  @Override
  public Observable<UserTrade> getUserTrades() {
    return getUserTrades(null);
  }
}
