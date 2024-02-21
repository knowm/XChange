package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioSingleSpotBalanceNotification;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class GateioStreamingAccountService implements StreamingAccountService {

  private final GateioStreamingService service;

  public GateioStreamingAccountService(GateioStreamingService service) {
    this.service = service;
  }


  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_BALANCES_CHANNEL)
        .map(GateioSingleSpotBalanceNotification.class::cast)
        .filter(notification -> (currency == null) || (notification.getResult().getCurrency().equals(currency)))
        .map(GateioStreamingAdapters::toBalance);
  }
}
