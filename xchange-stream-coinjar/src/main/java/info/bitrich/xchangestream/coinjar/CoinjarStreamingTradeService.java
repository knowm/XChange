package info.bitrich.xchangestream.coinjar;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketOrderEvent;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketUserTradeEvent;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CoinjarStreamingTradeService implements StreamingTradeService {

  private static final Logger logger = LoggerFactory.getLogger(CoinjarStreamingTradeService.class);

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  private final CoinjarStreamingService service;

  private final String userTradeChannel = "private";

  public CoinjarStreamingTradeService(CoinjarStreamingService service) {
    this.service = service;
  }

  @Override
  public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(userTradeChannel)
        .filter(node -> node.get("event").textValue().equals("private:fill"))
        .map(
            node -> {
              return mapper.treeToValue(node, CoinjarWebSocketUserTradeEvent.class);
            })
        .map(CoinjarStreamingAdapters::adaptUserTrade)
        .filter(userTrade -> currencyPair == null || currencyPair == userTrade.getInstrument());
  }

  @Override
  public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(userTradeChannel)
        .filter(node -> node.get("event").textValue().equals("private:order"))
        .map(
            node -> {
              return mapper.treeToValue(node, CoinjarWebSocketOrderEvent.class);
            })
        .map(CoinjarStreamingAdapters::adaptOrder)
        .filter(order -> currencyPair == null || currencyPair == order.getInstrument());
  }
}
