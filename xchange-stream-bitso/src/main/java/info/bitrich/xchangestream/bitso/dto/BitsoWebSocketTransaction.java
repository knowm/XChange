package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Domain object mapping a CoinbasePro web socket message. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitsoWebSocketTransaction {

  private final String eventType;
  private final String book;
  private final BitsoOrderbookPayload payload;
  private final String action;
  private final String response;
  private final Long time;

  public BitsoWebSocketTransaction(
          @JsonProperty("type") String eventType,
          @JsonProperty("book") String book,
          @JsonProperty("payload") BitsoOrderbookPayload payload,
          @JsonProperty("action") String action,
          @JsonProperty("response") String response,
          @JsonProperty("time") Long time) {

//    currencyPair= BitsoAdapters.adaptSymbol(book);
    this.payload = payload;
    this.book=book;
    this.eventType=eventType;
    this.action=action;
    this.response=response;
    this.time=time;
  }

  public BitsoOrderbookPayload getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "BitsoWebSocketTransaction{" +
            "eventType='" + eventType + '\'' +
            ", book='" + book + '\'' +
            ", payload=" + payload +
            ", action='" + action + '\'' +
            ", response='" + response + '\'' +
            ", time=" + time +
            '}';
  }

  public String getAction() {
    return action;
  }

  public String getResponse() {
    return response;
  }

  public Long getTime() {
    return time;
  }

  public String getEventType() {
    return eventType;
  }

  public String getBook() {
    return book;
  }

  private List<LimitOrder> coinbaseProOrderBookChanges(
            String side,
          OrderType orderType,
          CurrencyPair currencyPair,
          List<BitsoOrderBook> bids) {
    SortedMap<BigDecimal, BigDecimal> sideEntries=new TreeMap<>();

    for (BitsoOrderBook bitsoOrderBook : bids) {
      BigDecimal price = new BigDecimal(bitsoOrderBook.getRate());
      BigDecimal volume = new BigDecimal(bitsoOrderBook.getAmount());
      sideEntries.put(price, volume);
    }

    Stream<Entry<BigDecimal, BigDecimal>> stream =
            sideEntries.entrySet().stream()
                    .filter(level -> level.getValue().compareTo(BigDecimal.ZERO) != 0);
    return stream
            .map(
                    level ->
                            new LimitOrder(
                                    orderType, level.getValue(), currencyPair, "0", null, level.getKey()))
            .collect(Collectors.toList());
  }

  public OrderBook toOrderBook(
          SortedMap<BigDecimal, BigDecimal> bids,
          SortedMap<BigDecimal, BigDecimal> asks,
          int maxDepth,
          CurrencyPair currencyPair) {
    List<LimitOrder> gdaxOrderBookBids = new ArrayList<>();
    List<LimitOrder> gdaxOrderBookAsks = new ArrayList<>();
    if(!ObjectUtils.isEmpty(payload)) {
      // For efficiency, we go straight to XChange format
      gdaxOrderBookBids = coinbaseProOrderBookChanges(
                      "buy",
                      OrderType.BID,
                      currencyPair,
                      payload.getBids());
    }
    if(!ObjectUtils.isEmpty(payload)) {
      gdaxOrderBookAsks = coinbaseProOrderBookChanges(
                      "sell",
                      OrderType.ASK,
                      currencyPair,
                      payload.getAsks());
    }
    return new OrderBook(
            new Date(),
            gdaxOrderBookAsks,
            gdaxOrderBookBids,
            false);
  }

  public BitsoTrades toBitsoTrade() {
    return new BitsoTrades("", payload.getOrderId(), new BigDecimal(payload.getRate()), new BigDecimal(payload.getAmount()), payload.getType(), payload.getMakerOrderId(), payload.getTakerOrderId());
  }

}
