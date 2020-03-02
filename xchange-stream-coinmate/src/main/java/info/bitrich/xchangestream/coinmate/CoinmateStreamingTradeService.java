package info.bitrich.xchangestream.coinmate;

import com.fasterxml.jackson.core.type.TypeReference;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebsocketOpenOrder;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CoinmateStreamingTradeService implements StreamingTradeService {

  private final PusherStreamingService service;
  private final String userId;

  public CoinmateStreamingTradeService(PusherStreamingService service, String userId) {
    this.service = service;
    this.userId = userId;
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "private-open_orders-"
            + userId
            + "-"
            + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    return service
        .subscribePrivateChannel(channelName, "open_orders")
        .map(
            (message) -> {
              List<CoinmateWebsocketOpenOrder> websocketOpenOrders =
                  StreamingObjectMapperHelper.getObjectMapper()
                      .readValue(message, new TypeReference<List<CoinmateWebsocketOpenOrder>>() {});
              return CoinmateStreamingAdapter.adaptWebsocketOpenOrders(
                  websocketOpenOrders, currencyPair);
            })
        .concatMapIterable(OpenOrders::getAllOpenOrders);
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "private-user-trades-"
            + userId
            + "-"
            + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    return service
        .subscribePrivateChannel(channelName, "user_trades")
        .map(
            (message) -> {
              List<CoinmateWebSocketUserTrade> webSocketUserTrades =
                  StreamingObjectMapperHelper.getObjectMapper()
                      .readValue(message, new TypeReference<List<CoinmateWebSocketUserTrade>>() {});
              return CoinmateStreamingAdapter.adaptWebSocketUserTrades(
                  webSocketUserTrades, currencyPair);
            })
        .concatMapIterable(UserTrades::getUserTrades);
  }
}
