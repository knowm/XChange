package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebsocketOpenOrder;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CoinmateStreamingTradeService implements StreamingTradeService {

  private final CoinmateStreamingServiceFactory serviceFactory;

  public CoinmateStreamingTradeService(CoinmateStreamingServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "channel/my-open-orders/" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper().readerFor(CoinmateWebsocketOpenOrder.class);

    return serviceFactory
        .createConnection(channelName, true)
        .map(
            (message) -> {
              CoinmateWebsocketOpenOrder websocketOpenOrder = reader.readValue(message);
              List<CoinmateWebsocketOpenOrder> websocketOpenOrders =
                  Collections.singletonList(websocketOpenOrder);
              return CoinmateStreamingAdapter.adaptWebsocketOpenOrders(
                  websocketOpenOrders, currencyPair);
            })
        .concatMapIterable(OpenOrders::getAllOpenOrders);
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "channel/my-trades/" + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper()
            .readerFor(new TypeReference<List<CoinmateWebSocketUserTrade>>() {});

    return serviceFactory
        .createConnection(channelName, true)
        .map(
            (message) -> {
              List<CoinmateWebSocketUserTrade> webSocketUserTrades = reader.readValue(message);
              return CoinmateStreamingAdapter.adaptWebSocketUserTrades(
                  webSocketUserTrades, currencyPair);
            })
        .concatMapIterable(UserTrades::getUserTrades);
  }
}
