package info.bitrich.xchangestream.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.coinbasepro.CoinbaseProStreamingAdapters;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductStats;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill.Side;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Domain object mapping a CoinbasePro web socket message. */
@Getter
@ToString
@Builder
public class CoinbaseProWebSocketTransaction {
  private final String type;
  private final String orderId;
  private final String orderType;
  private final BigDecimal size;
  private final BigDecimal remainingSize;
  private final BigDecimal price;
  private final BigDecimal bestBid;
  private final BigDecimal bestAsk;
  private final BigDecimal lastSize;
  private final BigDecimal volume24h;
  private final BigDecimal open24h;
  private final BigDecimal low24h;
  private final BigDecimal high24h;
  private final String side;
  private final String[][] bids;
  private final String[][] asks;
  private final String[][] changes;
  private final String clientOid;
  private final String productId;
  private final long sequence;
  private final String time;
  private final String reason;
  private final long tradeId;
  private final String makerOrderId;
  private final String takerOrderId;

  private final String takerUserId;
  private final BigDecimal takerFeeRate;
  private final BigDecimal makerFeeRate;
  private final String userId;
  private final String takerProfileId;
  private final String profileId;

  public CoinbaseProWebSocketTransaction(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("order_type") String orderType,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("remaining_size") BigDecimal remainingSize,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("best_bid") BigDecimal bestBid,
      @JsonProperty("best_ask") BigDecimal bestAsk,
      @JsonProperty("last_size") BigDecimal lastSize,
      @JsonProperty("volume_24h") BigDecimal volume24h,
      @JsonProperty("open_24h") BigDecimal open24h,
      @JsonProperty("low_24h") BigDecimal low24h,
      @JsonProperty("high_24h") BigDecimal high24h,
      @JsonProperty("side") String side,
      @JsonProperty("bids") String[][] bids,
      @JsonProperty("asks") String[][] asks,
      @JsonProperty("changes") String[][] changes,
      @JsonProperty("client_oid") String clientOid,
      @JsonProperty("product_id") String productId,
      @JsonProperty("sequence") long sequence,
      @JsonProperty("time") String time,
      @JsonProperty("reason") String reason,
      @JsonProperty("trade_id") long tradeId,
      @JsonProperty("maker_order_id") String makerOrderId,
      @JsonProperty("taker_order_id") String takerOrderId,
      @JsonProperty("taker_user_id") String takerUserId,
      @JsonProperty("taker_fee_rate") BigDecimal takerFeeRate,
      @JsonProperty("maker_fee_rate") BigDecimal makerFeeRate,
      @JsonProperty("user_id") String userId,
      @JsonProperty("taker_profile_id") String takerProfileId,
      @JsonProperty("profile_id") String profileId) {

    this.remainingSize = remainingSize;
    this.reason = reason;
    this.tradeId = tradeId;
    this.makerOrderId = makerOrderId;
    this.takerOrderId = takerOrderId;
    this.type = type;
    this.orderId = orderId;
    this.orderType = orderType;
    this.size = size;
    this.price = price;
    this.bestBid = bestBid;
    this.bestAsk = bestAsk;
    this.lastSize = lastSize;
    this.volume24h = volume24h;
    this.high24h = high24h;
    this.low24h = low24h;
    this.open24h = open24h;
    this.side = side;
    this.bids = bids;
    this.asks = asks;
    this.changes = changes;
    this.clientOid = clientOid;
    this.productId = productId;
    this.sequence = sequence;
    this.time = time;
    this.takerUserId = takerUserId;
    this.takerFeeRate = takerFeeRate;
    this.makerFeeRate = makerFeeRate;
    this.userId = userId;
    this.takerProfileId = takerProfileId;
    this.profileId = profileId;
  }

  private Stream<LimitOrder> coinbaseProOrderBookChanges(
      String side,
      OrderType orderType,
      CurrencyPair currencyPair,
      String[][] changes,
      SortedMap<BigDecimal, LimitOrder> sideEntries,
      int maxDepth) {
    if (changes.length == 0) {
      return Stream.empty();
    }

    if (sideEntries == null) {
      return Stream.empty();
    }

    for (String[] level : changes) {
      if (level.length == 3 && !level[0].equals(side)) {
        continue;
      }

      BigDecimal price = new BigDecimal(level[level.length - 2]);
      BigDecimal volume = new BigDecimal(level[level.length - 1]);
      if (volume.compareTo(BigDecimal.ZERO) == 0) {
        sideEntries.remove(price);
      } else {
        LimitOrder order = new LimitOrder(orderType, volume, currencyPair, "0", null, price);
        sideEntries.put(price, order);
      }
    }

    Stream<LimitOrder> stream = sideEntries.values().stream();

    if (maxDepth != 0) {
      stream = stream.limit(maxDepth);
    }

    return stream;
  }

  public OrderBook toOrderBook(
      SortedMap<BigDecimal, LimitOrder> bids,
      SortedMap<BigDecimal, LimitOrder> asks,
      int maxDepth,
      CurrencyPair currencyPair) {
    // For efficiency, we go straight to XChange format
    Stream<LimitOrder> gdaxOrderBookBids =
        coinbaseProOrderBookChanges(
            "buy",
            OrderType.BID,
            currencyPair,
            changes != null ? changes : this.bids,
            bids,
            maxDepth);
    Stream<LimitOrder> gdaxOrderBookAsks =
        coinbaseProOrderBookChanges(
            "sell",
            OrderType.ASK,
            currencyPair,
            changes != null ? changes : this.asks,
            asks,
            maxDepth);
    return new OrderBook(
        time == null ? null : CoinbaseProStreamingAdapters.parseDate(time),
        gdaxOrderBookAsks,
        gdaxOrderBookBids,
        false);
  }

  public CoinbaseProProductTicker toCoinbaseProProductTicker() {
    String tickerTime = time;
    if (tickerTime == null) {
      SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
      tickerTime = dateFormatGmt.format(new Date()); // First ticker event doesn't have time!
    }
    return new CoinbaseProProductTicker(
        String.valueOf(tradeId), price, lastSize, bestBid, bestAsk, volume24h, tickerTime);
  }

  public CoinbaseProProductStats toCoinbaseProProductStats() {
    return new CoinbaseProProductStats(open24h, high24h, low24h, volume24h);
  }

  public CoinbaseProTrade toCoinbaseProTrade() {
    return new CoinbaseProTrade(time, tradeId, price, size, side, makerOrderId, takerOrderId);
  }

  public CoinbaseProFill toCoinbaseProFill() {
    boolean taker = userId != null && takerUserId != null && userId.equals(takerUserId);
    // buy/sell are flipped on the taker side.
    String useSide = side;
    if (taker && side != null) {
      if ("buy".equals(side)) {
        useSide = "sell";
      } else {
        useSide = "buy";
      }
    }
    return CoinbaseProFill.builder()
        .tradeId(String.valueOf(tradeId))
        .productId(productId)
        .price(price)
        .size(size)
        .orderId(taker ? takerOrderId : makerOrderId)
        .createdAt(time)
        .fee(taker ? price.multiply(size).multiply(takerFeeRate) : price.multiply(size).multiply(makerFeeRate))
        .side(Side.valueOf(useSide))
        .settled(true)
        .build();
  }
}
