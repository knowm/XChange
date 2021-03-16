package info.bitrich.xchangestream.cexio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.cexio.dto.CexioWebSocketOrderBookSubscribeResponse;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class CexioStreamingMarketDataService implements StreamingMarketDataService {

  private final CexioStreamingRawService streamingOrderDataService;

  public CexioStreamingMarketDataService(CexioStreamingRawService streamingOrderDataService) {
    this.streamingOrderDataService = streamingOrderDataService;
  }

  static class OrderBookUpdateConsumer
      implements io.reactivex.functions.Function<
          CexioWebSocketOrderBookSubscribeResponse, OrderBook> {
    BigInteger prevID = null;
    OrderBook orderBookSoFar =
        new OrderBook(new Date(), new ArrayList<LimitOrder>(), new ArrayList<LimitOrder>());
    final CexioStreamingRawService streamingOrderDataService;

    public OrderBookUpdateConsumer(CexioStreamingRawService streamingOrderDataService) {
      this.streamingOrderDataService = streamingOrderDataService;
    }

    @Override
    public OrderBook apply(CexioWebSocketOrderBookSubscribeResponse t) throws Exception {
      OrderBook retVal;
      if (prevID != null && prevID.add(BigInteger.ONE).compareTo(t.id) != 0) {
        throw new IllegalStateException(
            "Received an update message with id ["
                + t.id
                + "] not sequential to last id ["
                + prevID
                + "]. "
                + "Orderbook out of order!");
      }

      prevID = t.id;
      orderBookSoFar = CexioAdapters.adaptOrderBookIncremental(orderBookSoFar, t);
      retVal = orderBookSoFar;

      return retVal;
    }
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelNameForPair =
        CexioStreamingRawService.GetOrderBookChannelForCurrencyPair(currencyPair);

    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    // check depth parameter
    int depth = 0;
    if (args != null && args[0] instanceof Integer) {
      depth = (Integer) args[0];
    }
    Flowable<JsonNode> jsonNodeFlowable =
        streamingOrderDataService.subscribeChannel(channelNameForPair, currencyPair, depth);
    OrderBookUpdateConsumer orderBookConsumer =
        new OrderBookUpdateConsumer(streamingOrderDataService);
    return jsonNodeFlowable
        .map(
            s -> {
              JsonNode dataNode = s.get("data");
              return mapper.readValue(
                  dataNode.toString(), CexioWebSocketOrderBookSubscribeResponse.class);
            })
        .map(orderBookConsumer);
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }
}
