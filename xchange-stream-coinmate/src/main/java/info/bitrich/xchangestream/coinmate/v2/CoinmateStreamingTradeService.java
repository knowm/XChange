package info.bitrich.xchangestream.coinmate.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebsocketOpenOrder;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Flowable;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CoinmateStreamingTradeService implements StreamingTradeService {

  private final CoinmateStreamingService coinmateStreamingService;

  public CoinmateStreamingTradeService(CoinmateStreamingService coinmateStreamingService) {
    this.coinmateStreamingService = coinmateStreamingService;
  }

  @Override
  public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "private-open_orders-"
            + coinmateStreamingService.getUserId()
            + "-"
            + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper().readerFor(CoinmateWebsocketOpenOrder.class);

    return coinmateStreamingService
        .subscribeChannel(channelName, true)
        .map(
            (message) -> {
              List<CoinmateWebsocketOpenOrder> websocketOpenOrders =
                  Arrays.asList(
                      reader.readValue(message.get("payload"), CoinmateWebsocketOpenOrder[].class));
              return CoinmateStreamingAdapter.adaptWebsocketOpenOrders(
                  websocketOpenOrders, currencyPair);
            })
        .concatMapIterable(OpenOrders::getAllOpenOrders);
  }

  @Override
  public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "private-user-trades-"
            + coinmateStreamingService.getUserId()
            + "-"
            + CoinmateStreamingAdapter.getChannelPostfix(currencyPair);

    ObjectReader reader =
        StreamingObjectMapperHelper.getObjectMapper()
            .readerFor(new TypeReference<List<CoinmateWebSocketUserTrade>>() {});

    return coinmateStreamingService
        .subscribeChannel(channelName, true)
        .map(
            (message) -> {
              List<CoinmateWebSocketUserTrade> webSocketUserTrades =
                  reader.readValue(message.get("payload"));
              return CoinmateStreamingAdapter.adaptWebSocketUserTrades(
                  webSocketUserTrades, currencyPair);
            })
        .concatMapIterable(UserTrades::getUserTrades);
  }
}
