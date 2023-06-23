package info.bitrich.xchangestream.kucoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketOrderEvent;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KucoinStreamingTradeService implements StreamingTradeService {

  private static final Logger logger = LoggerFactory.getLogger(KucoinStreamingTradeService.class);

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  private final KucoinStreamingService service;

  public KucoinStreamingTradeService(KucoinStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getRawOrderChanges(currencyPair)
        .map(KucoinStreamingAdapters::adaptOrder);
  }

  public Observable<KucoinWebSocketOrderEvent> getRawOrderChanges(CurrencyPair currencyPair) {
    return service
        .subscribeChannel("/spotMarket/tradeOrders")
        .doOnError(ex -> logger.warn("encountered error while subscribing to order changes", ex))
        .map(node -> mapper.treeToValue(node, KucoinWebSocketOrderEvent.class))
        .filter(order -> currencyPair == null || currencyPair.equals(order.data.getCurrencyPair()));
  }
}
