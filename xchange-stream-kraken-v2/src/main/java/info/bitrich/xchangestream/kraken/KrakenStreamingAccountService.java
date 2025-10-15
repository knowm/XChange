package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.kraken.dto.common.ChannelType;
import info.bitrich.xchangestream.kraken.dto.response.KrakenBalancesMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenDataMessage;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import java.util.Set;
import java.util.stream.Collectors;

public class KrakenStreamingAccountService implements StreamingAccountService {

  private final KrakenStreamingService service;

  public KrakenStreamingAccountService(KrakenStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return service
        .subscribeChannel(ChannelType.BALANCES.getValue())
        .map(KrakenBalancesMessage.class::cast)
        .map(KrakenDataMessage::getPayload)
        .filter(payload -> currency == null || payload.getCurrency().equals(currency))
        .map(KrakenStreamingAdapters::toBalance);
  }

  @Override
  public Observable<Set<Balance>> getBalancesChanges(Object... args) {
    return service
            .subscribeChannel(ChannelType.BALANCES.getValue())
            .map(KrakenBalancesMessage.class::cast)
            .map(message ->
                    message.getData().stream()
                      .map(KrakenStreamingAdapters::toBalance)
                      .collect(Collectors.toSet())
            );
  }
}
