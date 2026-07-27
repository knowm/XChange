package info.bitrich.xchangestream.cryptocom;

import info.bitrich.xchangestream.core.StreamingAccountService;
import io.reactivex.rxjava3.core.Observable;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class CryptoComStreamingAccountService implements StreamingAccountService {

  private static final String BALANCE_CHANNEL = "user.balance";

  private final CryptoComPrivateStreamingService service;

  public CryptoComStreamingAccountService(CryptoComPrivateStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    return service
        .subscribeChannel(BALANCE_CHANNEL)
        .flatMapIterable(message -> service.extractData(message, CryptoComBalance.class))
        .flatMapIterable(CryptoComStreamingAccountService::positionsOf)
        .filter(position -> currency.getCurrencyCode().equals(position.getInstrumentName()))
        .map(CryptoComStreamingAdapters::adaptBalance);
  }

  private static List<CryptoComBalance.PositionBalance> positionsOf(CryptoComBalance balance) {
    return balance.getPositionBalances() == null
        ? Collections.emptyList()
        : balance.getPositionBalances();
  }
}
