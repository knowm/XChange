package info.bitrich.xchangestream.gemini;

import static org.knowm.xchange.gemini.v1.GeminiAdapters.adaptTrades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gemini.dto.GeminiLimitOrder;
import info.bitrich.xchangestream.gemini.dto.GeminiOrderbook;
import info.bitrich.xchangestream.gemini.dto.GeminiWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 15.11.17. */
public class GeminiStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG = LoggerFactory.getLogger(GeminiStreamingMarketDataService.class);

  private final GeminiStreamingService service;
  private final Map<CurrencyPair, GeminiOrderbook> orderbooks = new HashMap<>();

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public GeminiStreamingMarketDataService(GeminiStreamingService service) {
    this.service = service;
  }

  private boolean filterEventsByReason(JsonNode message, String type, String reason) {
    boolean hasEvents = false;
    if (message.has("events")) {
      for (JsonNode jsonEvent : message.get("events")) {
        boolean reasonResult =
            reason == null
                || (jsonEvent.has("reason") && jsonEvent.get("reason").asText().equals(reason));
        if (jsonEvent.get("type").asText().equals(type) && reasonResult) {
          hasEvents = true;
          break;
        }
      }
    }

    return hasEvents;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {

    Observable<GeminiOrderbook> subscribedOrderbookSnapshot =
        service
            .subscribeChannel(currencyPair, args)
            .filter(
                s ->
                    filterEventsByReason(s, "change", "initial")
                        || filterEventsByReason(s, "change", "place")
                        || filterEventsByReason(s, "change", "cancel")
                        || filterEventsByReason(s, "change", "trade"))
            .map(
                (JsonNode s) -> {
                  if (filterEventsByReason(s, "change", "initial")) {
                    GeminiWebSocketTransaction transaction =
                        mapper.treeToValue(s, GeminiWebSocketTransaction.class);
                    GeminiOrderbook orderbook = transaction.toGeminiOrderbook(currencyPair);
                    orderbooks.put(currencyPair, orderbook);
                    return orderbook;
                  }

                  if (filterEventsByReason(s, "change", "place")
                      || filterEventsByReason(s, "change", "cancel")
                      || filterEventsByReason(s, "change", "trade")) {

                    GeminiWebSocketTransaction transaction =
                        mapper.treeToValue(s, GeminiWebSocketTransaction.class);
                    GeminiLimitOrder[] levels = transaction.toGeminiLimitOrdersUpdate();
                    GeminiOrderbook orderbook = orderbooks.get(currencyPair);
                    orderbook.updateLevels(levels);
                    return orderbook;
                  }

                  throw new NotYetImplementedForExchangeException(
                      " Unknown message type, even after filtering: " + s.toString());
                });

    return subscribedOrderbookSnapshot.map(GeminiOrderbook::toOrderbook);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    Observable<GeminiTrade[]> subscribedTrades =
        service
            .subscribeChannel(currencyPair, args)
            .filter(s -> filterEventsByReason(s, "trade", null))
            .map(
                (JsonNode s) -> {
                  GeminiWebSocketTransaction transaction =
                      mapper.treeToValue(s, GeminiWebSocketTransaction.class);
                  return transaction.toGeminiTrades();
                });

    return subscribedTrades.flatMapIterable(s -> adaptTrades(s, currencyPair).getTrades());
  }
}
