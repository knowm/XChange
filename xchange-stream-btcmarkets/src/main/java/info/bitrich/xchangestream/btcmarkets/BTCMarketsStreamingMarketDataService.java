package info.bitrich.xchangestream.btcmarkets;

import static info.bitrich.xchangestream.btcmarkets.BTCMarketsStreamingService.CHANNEL_ORDERBOOK;
import static info.bitrich.xchangestream.btcmarkets.BTCMarketsStreamingService.CHANNEL_TICKER;
import static info.bitrich.xchangestream.btcmarkets.BTCMarketsStreamingService.CHANNEL_TRADE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import info.bitrich.xchangestream.btcmarkets.dto.BTCMarketsWebSocketOrderbookMessage;
import info.bitrich.xchangestream.btcmarkets.dto.BTCMarketsWebSocketTickerMessage;
import info.bitrich.xchangestream.btcmarkets.dto.BTCMarketsWebSocketTradeMessage;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

class BTCMarketsStreamingMarketDataService implements StreamingMarketDataService {

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  private final BTCMarketsStreamingService service;

  public BTCMarketsStreamingMarketDataService(BTCMarketsStreamingService service) {
    this.service = service;
  }

  private OrderBook handleOrderbookMessage(BTCMarketsWebSocketOrderbookMessage message)
      throws InvalidFormatException {
    return BTCMarketsStreamingAdapters.adaptOrderbookMessageToOrderbook(message);
  }

  private Ticker handleTickerMessage(BTCMarketsWebSocketTickerMessage message)
      throws InvalidFormatException {
    return BTCMarketsStreamingAdapters.adaptTickerMessageToTicker(message);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    final String marketId = BTCMarketsStreamingAdapters.adaptCurrencyPairToMarketId(currencyPair);
    return service
        .subscribeChannel(CHANNEL_ORDERBOOK, marketId)
        .map(node -> mapper.treeToValue(node, BTCMarketsWebSocketOrderbookMessage.class))
        .filter(orderEvent -> marketId.equals(orderEvent.marketId))
        .map(this::handleOrderbookMessage);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    final String marketId = BTCMarketsStreamingAdapters.adaptCurrencyPairToMarketId(currencyPair);
    return service
        .subscribeChannel(CHANNEL_TICKER, marketId)
        .map(node -> mapper.treeToValue(node, BTCMarketsWebSocketTickerMessage.class))
        .filter(tickerEvent -> marketId.equals(tickerEvent.getMarketId()))
        .map(this::handleTickerMessage);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    final String marketId = BTCMarketsStreamingAdapters.adaptCurrencyPairToMarketId(currencyPair);
    return service
        .subscribeChannel(CHANNEL_TRADE, marketId)
        .map(node -> mapper.treeToValue(node, BTCMarketsWebSocketTradeMessage.class))
        .filter(event -> marketId.equals(event.getMarketId()))
        .map(this::handleTradeMessage);
  }

  private Trade handleTradeMessage(BTCMarketsWebSocketTradeMessage message)
      throws InvalidFormatException {
    return BTCMarketsStreamingAdapters.adaptTradeMessageToTrade(message);
  }
}
