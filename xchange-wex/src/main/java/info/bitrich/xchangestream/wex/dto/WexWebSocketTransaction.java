package info.bitrich.xchangestream.wex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lukas Zaoralek on 16.11.17.
 */
public class WexWebSocketTransaction {
  private final JsonNode ask;
  private final JsonNode bid;

  public WexWebSocketTransaction(@JsonProperty("ask") JsonNode ask,
                                 @JsonProperty("bid") JsonNode bid) {
    this.ask = ask;
    this.bid = bid;
  }

  public static LimitOrder[] toOrderbookLevels(JsonNode jsonSide, Order.OrderType side, CurrencyPair currencyPair) {
    List<LimitOrder> levels = new ArrayList<>(jsonSide.size());
    for (JsonNode level : jsonSide) {
      BigDecimal price = new BigDecimal(level.get(0).asText());
      BigDecimal volume = new BigDecimal(level.get(1).asText());
      LimitOrder limitOrder = new LimitOrder(side, volume, currencyPair, null, null, price);
      levels.add(limitOrder);
    }

    return levels.toArray(new LimitOrder[levels.size()]);
  }

  public static Trade[] toTrades(JsonNode jsonSide, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>(jsonSide.size());
    for (JsonNode level : jsonSide) {
      String side = level.get(0).asText();
      BigDecimal price = new BigDecimal(level.get(1).asText());
      BigDecimal volume = new BigDecimal(level.get(2).asText());
      Order.OrderType takerSide = side.equals("sell") ? Order.OrderType.BID : Order.OrderType.ASK;
      Trade trade = new Trade(takerSide, volume, currencyPair, price, null, null);
      trades.add(trade);
    }

    return trades.toArray(new Trade[trades.size()]);
  }

  public LimitOrder[] toOrderbookUpdate(CurrencyPair currencyPair) {
    List<LimitOrder> levels;
    LimitOrder[] asks, bids;

    asks = toOrderbookLevels(ask, Order.OrderType.ASK, currencyPair);
    bids = toOrderbookLevels(bid, Order.OrderType.BID, currencyPair);

    levels = new ArrayList<>(asks.length + bids.length);
    levels.addAll(Arrays.asList(asks));
    levels.addAll(Arrays.asList(bids));

    return levels.toArray(new LimitOrder[levels.size()]);
  }
}
