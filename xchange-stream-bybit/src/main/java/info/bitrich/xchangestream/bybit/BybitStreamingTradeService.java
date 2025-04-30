package info.bitrich.xchangestream.bybit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.trade.BybitComplexOrderChanges;
import dto.trade.BybitComplexPositionChanges;
import dto.trade.BybitOrderChangesResponse;
import dto.trade.BybitPositionChangesResponse;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamingTradeService implements StreamingTradeService {

  private final BybitUserDataStreamingService streamingService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BybitStreamingTradeService(BybitUserDataStreamingService streamingService) {
    this.streamingService = streamingService;
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
