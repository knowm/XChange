package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedTradeUpdate;
import info.bitrich.xchangestream.lgo.dto.LgoTradesUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.*;

class LgoTradeBatchSubscription {

  private final LgoStreamingService service;
  private final CurrencyPair currencyPair;
  private final Flowable<Trade> subscription;
  private static final Logger LOGGER = LoggerFactory.getLogger(LgoTradeBatchSubscription.class);

  static LgoTradeBatchSubscription create(LgoStreamingService service, CurrencyPair currencyPair) {
    return new LgoTradeBatchSubscription(service, currencyPair);
  }

  private LgoTradeBatchSubscription(LgoStreamingService service, CurrencyPair currencyPair) {
    this.service = service;
    this.currencyPair = currencyPair;
    subscription = createTradeSubscription();
  }

  private Flowable<Trade> createTradeSubscription() {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    Flowable<Trade> flowable =
        service
            .subscribeChannel(LgoAdapter.channelName("trades", currencyPair))
            .map(s -> mapper.readValue(s.toString(), LgoTradesUpdate.class))
            .scan(
                new LgoGroupedTradeUpdate(currencyPair),
                (acc, s) -> {
                  if ("snapshot".equals(s.getType())) {
                    acc.apply(s.getBatchId(), s.getTrades());
                    return acc;
                  }
                  if (acc.getLastBatchId() + 1 != s.getBatchId()) {
                    LOGGER.warn(
                        "Wrong batchId. Expected {} got {}.",
                        acc.getLastBatchId() + 1,
                        s.getBatchId());
                    resubscribe();
                  }
                  acc.apply(s.getBatchId(), s.getTrades());
                  return acc;
                })
            .skip(1)
            .flatMap(acc -> Flowable.fromIterable(acc.getTrades()));
    return flowable;
  }

  private void resubscribe() {
    String channelName = LgoAdapter.channelName("trades", currencyPair);
    try {
      service.sendMessage(service.getUnsubscribeMessage(channelName));
      service.sendMessage(service.getSubscribeMessage(channelName));
    } catch (IOException e) {
      LOGGER.error("Error resubscribing", e);
    }
  }

  Flowable<Trade> getSubscription() {
    return subscription;
  }
}
