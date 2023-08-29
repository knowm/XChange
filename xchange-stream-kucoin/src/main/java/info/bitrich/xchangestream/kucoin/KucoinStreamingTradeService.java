package info.bitrich.xchangestream.kucoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketOrderEvent;
import info.bitrich.xchangestream.kucoin.dto.KucoinWebSocketOrderEventV2;
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

  /**
   * https://docs.kucoin.com/#private-order-change
   * @param currencyPair
   * @return
   */
  public Observable<KucoinWebSocketOrderEvent> getRawOrderChanges(CurrencyPair currencyPair) {
    return service
        .subscribeChannel("/spotMarket/tradeOrders")
        .doOnError(ex -> logger.warn("encountered error while subscribing to order changes", ex))
        .map(node -> mapper.treeToValue(node, KucoinWebSocketOrderEvent.class))
        .filter(order -> currencyPair == null || currencyPair.equals(order.data.getCurrencyPair()));
  }

  /**
   * see https://docs.kucoin.com/#private-order-change-v2
   * This topic will push all change events of your orders.
   * Compared with v1, v2 adds an Order Status: "new", there is no difference in push speed
   * @param currencyPair
   * @return
   */
  public Observable<KucoinWebSocketOrderEventV2> getRawOrderChangesV2(CurrencyPair currencyPair){
    return service
            .subscribeChannel("/spotMarket/tradeOrdersV2")
            .doOnError(ex -> logger.warn("encountered error while subscribing to order changes", ex))
            .map(node -> mapper.treeToValue(node, KucoinWebSocketOrderEventV2.class))
            .filter(order -> currencyPair == null || currencyPair.equals(order.getData().getCurrencyPair()));
  }
}
