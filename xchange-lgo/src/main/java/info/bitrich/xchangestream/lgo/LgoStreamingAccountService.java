package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedBalanceUpdate;
import info.bitrich.xchangestream.lgo.dto.LgoBalanceUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;
import org.slf4j.*;

import java.io.IOException;
import java.util.List;

public class LgoStreamingAccountService implements StreamingAccountService {

    private static final String CHANNEL_NAME = "balance";
    private final LgoStreamingService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(LgoStreamingAccountService.class);
    private volatile Observable<LgoGroupedBalanceUpdate> subscription = null;

    public LgoStreamingAccountService(LgoStreamingService lgoStreamingService) {
        service = lgoStreamingService;
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        if (subscription == null) {
            createSubscription();
        }
        return subscription.map(u -> u.getWallet().get(currency));
    }

    public Observable<Wallet> getWallet() {
        if (subscription == null) {
            createSubscription();
        }
        return subscription
                .map(u -> Wallet.Builder.from(u.getWallet().values()).build());
    }

    private void createSubscription() {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        subscription = service
                .subscribeChannel(CHANNEL_NAME)
                .map(s -> mapper.readValue(s.toString(), LgoBalanceUpdate.class))
                .scan(new LgoGroupedBalanceUpdate(), (acc, s) -> {
                    List<Balance> updatedBalances = LgoAdapter.adaptBalances(s.getData());
                    if (s.getType().equals("snapshot")) {
                        return acc.applySnapshot(s.getSeq(), updatedBalances);
                    }
                    if (acc.getSeq() + 1 != s.getSeq()) {
                        LOGGER.warn("Wrong seq expected {} get {}", acc.getSeq() + 1, s.getSeq());
                        resubscribe();
                    }
                    return acc.applyUpdate(s.getSeq(), updatedBalances);
                })
                .skip(1)
                .share();
    }

    private void resubscribe() {
        if (subscription == null) {
            return;
        }
        try {
            service.sendMessage(service.getUnsubscribeMessage(CHANNEL_NAME));
            service.sendMessage(service.getSubscribeMessage(CHANNEL_NAME));
        } catch (IOException e) {
            LOGGER.warn("Error resubscribing", e);
        }
    }
}
