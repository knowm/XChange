package info.bitrich.xchangestream.kucoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketBalanceChangeEvent;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KucoinStreamingAccountService implements StreamingAccountService {

    private static final Logger logger = LoggerFactory.getLogger(KucoinStreamingAccountService.class);

    private static ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

    private final KucoinStreamingService service;

    public KucoinStreamingAccountService(KucoinStreamingService service){
        this.service = service;
    }

    public Observable<KucoinWebSocketBalanceChangeEvent> getRawBalanceChanges(Currency currency, Object... args) {
        return service
                .subscribeChannel("/account/balance")
                .doOnError(ex -> logger.warn("encountered error while subscribing to account balance", ex))
                .map(node -> mapper.treeToValue(node, KucoinWebSocketBalanceChangeEvent.class))
                .filter(balance -> currency == null || currency.equals(new Currency(balance.getData().getCurrency())));
    }
}
