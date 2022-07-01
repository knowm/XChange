package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedBalanceUpdate;
import info.bitrich.xchangestream.lgo.dto.LgoBalanceUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class LgoStreamingAccountService implements StreamingAccountService {

  private static final String CHANNEL_NAME = "balance";
  private final LgoStreamingService service;
  private volatile Observable<LgoGroupedBalanceUpdate> subscription = null;

  public LgoStreamingAccountService(LgoStreamingService lgoStreamingService) {
    service = lgoStreamingService;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    ensureSubscription();
    return subscription.map(u -> u.getWallet().get(currency));
  }

  public Observable<Wallet> getWallet() {
    ensureSubscription();
    return subscription.map(u -> Wallet.Builder.from(u.getWallet().values()).build());
  }

  private void ensureSubscription() {
    if (subscription == null) {
      createSubscription();
    }
  }

  private synchronized void createSubscription() {
    if (subscription != null) {
      return;
    }
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    subscription =
        service
            .subscribeChannel(CHANNEL_NAME)
            .map(s -> mapper.readValue(s.toString(), LgoBalanceUpdate.class))
            .scan(
                new LgoGroupedBalanceUpdate(),
                (acc, s) -> {
                  List<Balance> updatedBalances = LgoAdapter.adaptBalances(s.getData());
                  if (s.getType().equals("snapshot")) {
                    return acc.applySnapshot(s.getSeq(), updatedBalances);
                  }
                  return acc.applyUpdate(s.getSeq(), updatedBalances);
                })
            .skip(1) // skips first element for it's just the empty initial accumulator
            .share();
  }
}
