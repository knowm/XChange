package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.ObjecMapperHelper;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.OrderBookDTO;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.TickerDTO;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.TradeDTO;
import java.util.Date;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

@UtilityClass
public class GateioStreamingAdapters {

  ObjectMapper objectMapper = ObjecMapperHelper.getObjectMapper();

  @SneakyThrows
  public Ticker toTicker(JsonNode jsonNode) {
    GateioTickerNotification notification = objectMapper.treeToValue(jsonNode, GateioTickerNotification.class);
    TickerDTO tickerDTO = notification.getResult();

    return new Ticker.Builder()
        .timestamp(Date.from(notification.getTime()))
        .instrument(tickerDTO.getCurrencyPair())
        .last(tickerDTO.getLastPrice())
        .ask(tickerDTO.getLowestAsk())
        .bid(tickerDTO.getHighestBid())
        .percentageChange(tickerDTO.getChangePercent24h())
        .volume(tickerDTO.getBaseVolume())
        .quoteVolume(tickerDTO.getQuoteVolume())
        .high(tickerDTO.getHighPrice24h())
        .low(tickerDTO.getLowPrice24h())
        .build();
  }


  @SneakyThrows
  public Trade toTrade(JsonNode jsonNode) {
    GateioTradeNotification notification = objectMapper.treeToValue(jsonNode, GateioTradeNotification.class);
    TradeDTO tradeDTO = notification.getResult();


    return new Trade.Builder()
        .type("sell".equals(tradeDTO.getSide()) ? OrderType.ASK : OrderType.BID)
        .originalAmount(tradeDTO.getAmount())
        .instrument(tradeDTO.getCurrencyPair())
        .price(tradeDTO.getPrice())
        .timestamp(Date.from(tradeDTO.getTime()))
        .id(String.valueOf(tradeDTO.getId()))
        .build();
  }


  @SneakyThrows
  public OrderBook toOrderBook(JsonNode jsonNode) {
    GateioOrderBookNotification notification = objectMapper.treeToValue(jsonNode, GateioOrderBookNotification.class);
    OrderBookDTO orderBookDTO = notification.getResult();

    Stream<LimitOrder> asks = orderBookDTO.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), orderBookDTO.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    Stream<LimitOrder> bids = orderBookDTO.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), orderBookDTO.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    return new OrderBook(Date.from(orderBookDTO.getTimestamp()), asks, bids);
  }


}
