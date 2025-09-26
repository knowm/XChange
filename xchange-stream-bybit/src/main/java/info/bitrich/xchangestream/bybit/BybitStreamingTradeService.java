package info.bitrich.xchangestream.bybit;

import static info.bitrich.xchangestream.bybit.BybitUserTradeStreamingService.BATCH_ORDER_CHANGE;
import static info.bitrich.xchangestream.bybit.BybitUserTradeStreamingService.ORDER_CANCEL;
import static info.bitrich.xchangestream.bybit.BybitUserTradeStreamingService.ORDER_CHANGE;
import static info.bitrich.xchangestream.bybit.BybitUserTradeStreamingService.ORDER_CREATE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.trade.BybitComplexOrderChanges;
import dto.trade.BybitComplexPositionChanges;
import dto.trade.BybitOrderChangesResponse;
import dto.trade.BybitPositionChangesResponse;
import dto.trade.BybitStreamOrderResponse;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.github.resilience4j.rxjava3.ratelimiter.operator.RateLimiterOperator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.service.BybitBaseService;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BybitStreamingTradeService extends BybitBaseService implements StreamingTradeService {

  private final Logger LOG = LoggerFactory.getLogger(BybitStreamingTradeService.class);
  private final BybitUserDataStreamingService streamingService;
  private final BybitUserTradeStreamingService userTradeService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BybitStreamingTradeService(BybitUserDataStreamingService streamingService, BybitUserTradeStreamingService userTradeService, ResilienceRegistries resilienceRegistries,
      BybitExchange exchange) {
    super(exchange, resilienceRegistries);
    this.streamingService = streamingService;
    this.userTradeService = userTradeService;
  }

  public Single<Integer> placeMarketOrder(MarketOrder order) {
    BybitCategory category = BybitAdapters.getCategory(order.getInstrument());
    Observable<Integer> observable = userTradeService.subscribeChannel(ORDER_CREATE, order, String.valueOf(System.nanoTime()), category)
        .flatMap(node -> {
          BybitStreamOrderResponse response = mapper.treeToValue(node, BybitStreamOrderResponse.class);
          if (response != null && response.getRetCode() == 0) {
            return Observable.just(0);
          } else {
            assert response != null;
            return Observable.just(response.getRetCode());
          }
        });
    return observable.firstElement()
        .compose(RateLimiterOperator.of(getCreateOrderRateLimiter(category))).toSingle();
  }

  public Single<Integer> placeLimitOrder(LimitOrder order) {
    BybitCategory category = BybitAdapters.getCategory(order.getInstrument());
    Observable<Integer> observable = userTradeService.subscribeChannel(ORDER_CREATE, order, String.valueOf(System.nanoTime()), category)
        .flatMap(node -> {
          BybitStreamOrderResponse response = mapper.treeToValue(node, BybitStreamOrderResponse.class);
          if (response != null && response.getRetCode() == 0) {
            return Observable.just(0);
          } else {
            assert response != null;
            return Observable.just(response.getRetCode());
          }
        });
    return observable.firstElement()
        .compose(RateLimiterOperator.of(getCreateOrderRateLimiter(category))).toSingle();
  }

  public Single<Integer> changeOrder(LimitOrder order) {
    BybitCategory category = BybitAdapters.getCategory(order.getInstrument());
    Observable<Integer> observable = userTradeService.subscribeChannel(ORDER_CHANGE, order, String.valueOf(System.nanoTime()), category)
        .flatMap(node -> {
          BybitStreamOrderResponse response = mapper.treeToValue(node, BybitStreamOrderResponse.class);
          if (response != null && response.getRetCode() == 0) {
            return Observable.just(0);
          } else {
            assert response != null;
            return Observable.just(response.getRetCode());
          }
        });
    return observable.firstElement()
        .compose(RateLimiterOperator.of(getAmendOrderRateLimiter(category))).toSingle();
  }

  public Single<List<Integer>> batchChangeOrder(List<LimitOrder> orders) {
    BybitCategory category = BybitAdapters.getCategory(orders.get(0).getInstrument());
    try {
      Observable<List<Integer>> observable = userTradeService.subscribeChannel(BATCH_ORDER_CHANGE, mapper.writeValueAsString(orders.toArray(new LimitOrder[0]))
              , String.valueOf(System.nanoTime()), category)
          .flatMap(node -> {
            BybitStreamOrderResponse response = mapper.treeToValue(node, BybitStreamOrderResponse.class);
            if(response.getRetCode() == 0) {
              List<Integer> list = new ArrayList<>();
              response.getRetExtInfo().getList().forEach(retExtInfo -> list.add(Integer.valueOf(retExtInfo.getCode())));
              return Observable.just(list);
            } else  {
              return Observable.just(List.of(response.getRetCode()));
            }
          });
      return observable.firstElement()
          .compose(RateLimiterOperator.of(getBatchAmendOrderRateLimiter(category))).toSingle();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Single<Integer> cancelOrder(CancelOrderParams params) {
    BybitCancelOrderParams bybitParams = (BybitCancelOrderParams) params;
    BybitCategory category = BybitAdapters.getCategory(bybitParams.getInstrument());
    Observable<Integer> observable = userTradeService.subscribeChannel(ORDER_CANCEL, bybitParams, String.valueOf(System.nanoTime()), category)
        .flatMap(node -> {
          BybitStreamOrderResponse response = mapper.treeToValue(node, BybitStreamOrderResponse.class);
          if (response != null && response.getRetCode() == 0) {
            return Observable.just(0);
          } else {
            assert response != null;
            return Observable.just(response.getRetCode());
          }
        });
    return observable.firstElement()
        .compose(RateLimiterOperator.of(getCancelOrderRateLimiter(category))).toSingle();
  }


  @Override
  /*
   * instrument param is not used
   * arg[0] BybitCategory, if null then subscribe to all category
   */
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    String channelUniqueId = "order";
    if (args[0] != null && args[0] instanceof BybitCategory) {
      channelUniqueId += "." + ((BybitCategory) args[0]).getValue();
    }
    return streamingService
        .subscribeChannel(channelUniqueId)
        .flatMap(
            node -> {
              BybitOrderChangesResponse bybitOrderChangesResponse =
                  mapper.treeToValue(node, BybitOrderChangesResponse.class);
              return Observable.fromIterable(
                  BybitStreamAdapters.adaptOrdersChanges(bybitOrderChangesResponse.getData()));
            });
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair pair, Object... args) {
    return getOrderChanges((Instrument) pair, args);
  }

  public Observable<BybitComplexOrderChanges> getComplexOrderChanges(BybitCategory category) {
    String channelUniqueId = "order";
    if (category != null) {
      channelUniqueId += "." + category.getValue();
    }
    return streamingService
        .subscribeChannel(channelUniqueId)
        .flatMap(
            node -> {
              BybitOrderChangesResponse bybitOrderChangesResponse =
                  mapper.treeToValue(node, BybitOrderChangesResponse.class);
              return Observable.fromIterable(
                  BybitStreamAdapters.adaptComplexOrdersChanges(
                      bybitOrderChangesResponse.getData()));
            });
  }

  public Observable<OpenPosition> getPositionChanges(BybitCategory category) {
    String channelUniqueId = "position";
    if (category != null) {
      channelUniqueId += "." + category.getValue();
    }
    return streamingService
        .subscribeChannel(channelUniqueId)
        .flatMap(
            node -> {
              BybitPositionChangesResponse bybitPositionChangesResponse =
                  mapper.treeToValue(node, BybitPositionChangesResponse.class);
              return Observable.fromIterable(
                  BybitStreamAdapters.adaptPositionChanges(bybitPositionChangesResponse.getData())
                      .getOpenPositions());
            });
  }

  public Observable<BybitComplexPositionChanges> getBybitPositionChanges(BybitCategory category) {
    String channelUniqueId = "position";
    if (category != null) {
      channelUniqueId += "." + category.getValue();
    }
    return streamingService
        .subscribeChannel(channelUniqueId)
        .flatMap(
            node -> {
              BybitPositionChangesResponse bybitPositionChangesResponse =
                  mapper.treeToValue(node, BybitPositionChangesResponse.class);
              return Observable.fromIterable(
                  BybitStreamAdapters.adaptComplexPositionChanges(
                      bybitPositionChangesResponse.getData()));
            });
  }

}
