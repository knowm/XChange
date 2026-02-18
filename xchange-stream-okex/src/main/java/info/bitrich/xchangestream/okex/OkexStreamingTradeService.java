package info.bitrich.xchangestream.okex;

import static info.bitrich.xchangestream.okex.OkexPrivateStreamingService.CANCEL_ORDER;
import static info.bitrich.xchangestream.okex.OkexPrivateStreamingService.CHANGE_ORDER;
import static info.bitrich.xchangestream.okex.OkexPrivateStreamingService.PLACE_ORDER;
import static info.bitrich.xchangestream.okex.OkexPrivateStreamingService.USER_ORDER_CHANGES;
import static info.bitrich.xchangestream.okex.OkexPrivateStreamingService.USER_POSITION_CHANGES;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.github.resilience4j.rxjava3.ratelimiter.operator.RateLimiterOperator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexAuthenticated;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexPosition;
import org.knowm.xchange.okex.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.dto.trade.OkexOrderResponse;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class OkexStreamingTradeService implements StreamingTradeService {

  private final OkexPrivateStreamingService privateStreamingService;
  private final ExchangeMetaData exchangeMetaData;
  private final ResilienceRegistries resilienceRegistries;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public OkexStreamingTradeService(
      OkexPrivateStreamingService privateStreamingService,
      ExchangeMetaData exchangeMetaData,
      ResilienceRegistries resilienceRegistries) {
    this.privateStreamingService = privateStreamingService;
    this.exchangeMetaData = exchangeMetaData;
    this.resilienceRegistries = resilienceRegistries;
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    String channelUniqueId = USER_ORDER_CHANGES + OkexAdapters.adaptInstrument(instrument);

    return privateStreamingService
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<OkexOrderDetails> okexOrderDetails =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexOrderDetails.class));
              return Observable.fromIterable(
                  OkexAdapters.adaptOrdersChanges(okexOrderDetails, exchangeMetaData));
            });
  }

  // cannot use OrderChanges and UserTrades together
  // leave it for backward compatibility, but it is not trade at all
  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    String channelUniqueId = USER_ORDER_CHANGES + OkexAdapters.adaptInstrument(instrument);

    return privateStreamingService
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<OkexOrderDetails> okexOrderDetails =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexOrderDetails.class));
              return Observable.fromIterable(
                  OkexAdapters.adaptUserTrades(okexOrderDetails, exchangeMetaData).getUserTrades());
            });
  }

  @Override
  public Observable<OpenPosition> getPositionChanges(Instrument instrument) {
    String channelUniqueId = USER_POSITION_CHANGES + OkexAdapters.adaptInstrument(instrument);
    return privateStreamingService
        .subscribeChannel(channelUniqueId)
        .filter(message -> message.has("data"))
        .flatMap(
            jsonNode -> {
              List<OkexPosition> okexPositions =
                  mapper.treeToValue(
                      jsonNode.get("data"),
                      mapper
                          .getTypeFactory()
                          .constructCollectionType(List.class, OkexPosition.class));
              return Observable.fromIterable(
                  OkexAdapters.adaptOpenPositions(okexPositions, exchangeMetaData)
                      .getOpenPositions());
            });
  }

  public Single<Integer> placeLimitOrder(LimitOrder order) {
    if (privateStreamingService.isLoginDone()) {
      Observable<Integer> observable =
          privateStreamingService
              .subscribeChannel(String.valueOf(System.nanoTime()), PLACE_ORDER, order)
              .flatMap(
                  node -> {
                    TypeReference<OkexResponse<List<OkexOrderResponse>>> typeReference =
                        new TypeReference<>() {};
                    OkexResponse<List<OkexOrderResponse>> response =
                        mapper.treeToValue(node, typeReference);
                    if (response.getCode().equals("0")) {
                      return Observable.just(0);
                    } else {
                      return Observable.just(Integer.parseInt(response.getData().get(0).getCode()));
                    }
                  });
      return observable
          .firstOrError()
          .compose(
              RateLimiterOperator.of(
                  resilienceRegistries
                      .rateLimiters()
                      .rateLimiter(OkexAuthenticated.placeOrderPath)));
    } else {
      throw new UnsupportedOperationException("privateStreamingService not authorized");
    }
  }

  public Single<Integer> placeMarketOrder(MarketOrder order) {
    if (privateStreamingService.isLoginDone()) {
      Observable<Integer> observable =
          privateStreamingService
              .subscribeChannel(String.valueOf(System.nanoTime()), PLACE_ORDER, order)
              .flatMap(
                  node -> {
                    TypeReference<OkexResponse<List<OkexOrderResponse>>> typeReference =
                        new TypeReference<>() {};
                    OkexResponse<List<OkexOrderResponse>> response =
                        mapper.treeToValue(node, typeReference);
                    if (response.getCode().equals("0")) {
                      return Observable.just(0);
                    } else {
                      return Observable.just(Integer.parseInt(response.getData().get(0).getCode()));
                    }
                  });
      return observable
          .firstOrError()
          .compose(
              RateLimiterOperator.of(
                  resilienceRegistries
                      .rateLimiters()
                      .rateLimiter(OkexAuthenticated.placeOrderPath)));
    } else {
      throw new UnsupportedOperationException("privateStreamingService not authorized");
    }
  }

  public Single<Integer> changeOrder(LimitOrder order) {
    if (privateStreamingService.isLoginDone()) {
      Observable<Integer> observable =
          privateStreamingService
              .subscribeChannel(String.valueOf(System.nanoTime()), CHANGE_ORDER, order)
              .flatMap(
                  node -> {
                    TypeReference<OkexResponse<List<OkexOrderResponse>>> typeReference =
                        new TypeReference<>() {};
                    OkexResponse<List<OkexOrderResponse>> response =
                        mapper.treeToValue(node, typeReference);
                    if (response.getCode().equals("0")) {
                      return Observable.just(0);
                    } else {
                      return Observable.just(Integer.parseInt(response.getData().get(0).getCode()));
                    }
                  });
      return observable
          .firstOrError()
          .compose(
              RateLimiterOperator.of(
                  resilienceRegistries
                      .rateLimiters()
                      .rateLimiter(OkexAuthenticated.amendOrderPath)));
    } else {
      throw new UnsupportedOperationException("privateStreamingService not authorized");
    }
  }

  public Single<Integer> cancelOrder(CancelOrderParams params) {
    if (privateStreamingService.isLoginDone()) {
      Observable<Integer> observable =
          privateStreamingService
              .subscribeChannel(String.valueOf(System.nanoTime()), CANCEL_ORDER, params)
              .flatMap(
                  node -> {
                    TypeReference<OkexResponse<List<OkexOrderResponse>>> typeReference =
                        new TypeReference<>() {};
                    OkexResponse<List<OkexOrderResponse>> response =
                        mapper.treeToValue(node, typeReference);
                    if (response.getCode().equals("0")) {
                      return Observable.just(0);
                    } else {
                      return Observable.just(Integer.parseInt(response.getData().get(0).getCode()));
                    }
                  });
      return observable
          .firstOrError()
          .compose(
              RateLimiterOperator.of(
                  resilienceRegistries
                      .rateLimiters()
                      .rateLimiter(OkexAuthenticated.cancelOrderPath)));
    } else {
      throw new UnsupportedOperationException("privateStreamingService not authorized");
    }
  }
}
