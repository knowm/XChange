package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedLevel2Update;
import info.bitrich.xchangestream.lgo.dto.LgoLevel2Update;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Flowable;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.slf4j.*;

class LgoLevel2BatchSubscription {

  private final LgoStreamingService service;
  private final Flowable<OrderBook> subscription;
  private static final Logger LOGGER = LoggerFactory.getLogger(LgoLevel2BatchSubscription.class);
  private CurrencyPair currencyPair;

  static LgoLevel2BatchSubscription create(LgoStreamingService service, CurrencyPair currencyPair) {
    return new LgoLevel2BatchSubscription(service, currencyPair);
  }

  private LgoLevel2BatchSubscription(LgoStreamingService service, CurrencyPair currencyPair) {
    this.service = service;
    this.currencyPair = currencyPair;
    subscription = createSubscription();
  }

  Flowable<OrderBook> getSubscription() {
    return subscription;
  }

  private Flowable<OrderBook> createSubscription() {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return service
        .subscribeChannel(LgoAdapter.channelName("level2", currencyPair))
        .map(s -> mapper.readValue(s.toString(), LgoLevel2Update.class))
        .scan(
            new LgoGroupedLevel2Update(),
            (acc, s) -> {
              if (s.getType().equals("snapshot")) {
                acc.applySnapshot(s.getBatchId(), currencyPair, s.getData());
                return acc;
              }
              if (acc.getLastBatchId() + 1 != s.getBatchId()) {
                LOGGER.warn(
                    "Wrong batch id. Expected {} got {}.",
                    acc.getLastBatchId() + 1,
                    s.getBatchId());
                acc.markDirty();
                resubscribe();
                return acc;
              }
              acc.applyUpdate(s.getBatchId(), currencyPair, s.getData());
              return acc;
            })
        .filter(LgoGroupedLevel2Update::isValid)
        .map(LgoGroupedLevel2Update::orderBook)
        .publish(1).refCount();
  }

  private void resubscribe() {
    try {
      String channelName = LgoAdapter.channelName("level2", currencyPair);
      service.sendMessage(service.getUnsubscribeMessage(channelName));
      service.sendMessage(service.getSubscribeMessage(channelName));
    } catch (IOException e) {
      LOGGER.warn("Error resubscribing", e);
    }
  }
}
