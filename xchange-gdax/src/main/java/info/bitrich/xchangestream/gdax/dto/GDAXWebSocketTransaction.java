package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Domain object mapping a GDAX web socket message.
 */
public class GDAXWebSocketTransaction {
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
    private final String userId;
    private final String takerProfileId;
    private final String profileId;

    public GDAXWebSocketTransaction(
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
        this.userId = userId;
        this.takerProfileId = takerProfileId;
        this.profileId = profileId;
    }

    private String[][] GDAXOrderBookChanges(String side, String[][] changes, SortedMap<BigDecimal, String> sideEntries,
                                            int maxDepth) {
        if (changes.length == 0) {
            return null;
        }

        for (String[] level : changes) {
            if (level.length == 3 && !level[0].equals(side)) {
                continue;
            }

            BigDecimal price = new BigDecimal(level[level.length - 2]);
            String volume = level[level.length - 1];
            sideEntries.put(price, volume);
        }

        List<String[]> levels = new ArrayList<>();
        int currentDepth = 0;
        for (Map.Entry<BigDecimal, String> level : sideEntries.entrySet()) {
            if (maxDepth > 0 && currentDepth > maxDepth) continue;
            String volume = level.getValue();
            if (!volume.equals("0")) {
                levels.add(new String[]{level.getKey().toString(), volume, "1"});
                currentDepth++;
            }
        }

        return levels.toArray(new String[levels.size()][]);
    }

    public GDAXProductBook toGDAXProductBook(SortedMap<BigDecimal, String> bids, SortedMap<BigDecimal, String> asks,
                                             int maxDepth) {
        String[][] gdaxOrderBookBids = GDAXOrderBookChanges("buy", this.changes != null ? this.changes : this.bids,
                bids, maxDepth);
        String[][] gdaxOrderBookAsks = GDAXOrderBookChanges("sell", this.changes != null ? this.changes : this.asks,
                asks, maxDepth);
        return new GDAXProductBook((long) 0, gdaxOrderBookBids, gdaxOrderBookAsks);
    }

    public GDAXProductTicker toGDAXProductTicker() {
        String tickerTime = time;
        if (tickerTime == null) {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            tickerTime = dateFormatGmt.format(new Date()); //First ticker event doesn't have time!
        }
        return new GDAXProductTicker(String.valueOf(tradeId), price, lastSize, bestBid, bestAsk, volume24h, tickerTime);
    }

    public GDAXProductStats toGDAXProductStats() {
        return new GDAXProductStats(open24h, high24h, low24h, volume24h);
    }

    public GDAXTrade toGDAXTrade() {
        return new GDAXTrade(time, tradeId, price, size, side);
    }

    public GDAXFill toGDAXFill() {
        boolean taker = userId != null && takerUserId != null && userId.equals(takerUserId);
        return new GDAXFill(String.valueOf(tradeId), productId, price, size, taker ? takerOrderId : makerOrderId, time, null, null, true, side);
    }

    public String getType() {
        return type;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getBestBid() {
        return bestBid;
    }

    public BigDecimal getBestAsk() {
        return bestAsk;
    }

    public BigDecimal getLastSize() {
        return lastSize;
    }

    public BigDecimal getVolume24h() {
        return volume24h;
    }

    public BigDecimal getOpen24h() {
        return open24h;
    }

    public BigDecimal getLow24h() {
        return low24h;
    }

    public BigDecimal getHigh24h() {
        return high24h;
    }

    public String getSide() {
        return side;
    }

    public String getClientOid() {
        return clientOid;
    }

    public String getProductId() {
        return productId;
    }

    public Long getSequence() {
        return sequence;
    }

    public String getTime() {
        return time;
    }

    public BigDecimal getRemainingSize() {
        return remainingSize;
    }

    public String getReason() {
        return reason;
    }

    public long getTradeId() {
        return tradeId;
    }

    public String getMakerOrderId() {
        return makerOrderId;
    }

    /**
     * @deprecated Use {@link #getTakerOrderId()}
     */
    public String getTakenOrderId() {
        return takerOrderId;
    }
    
    public String getTakerOrderId() {
        return takerOrderId;
    }
    
    public String getTakerUserId() {
		return takerUserId;
	}

	public String getUserId() {
		return userId;
	}

	public String getTakerProfileId() {
        return takerProfileId;
	}

	public String getProfileId() {
		return profileId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GDAXWebSocketTransaction{");
        sb.append("type='").append(type).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", orderType='").append(orderType).append('\'');
        sb.append(", size=").append(size);
        sb.append(", price=").append(price);
        sb.append(", bestBid=").append(bestBid);
        sb.append(", bestAsk=").append(bestAsk);
        sb.append(", lastSize=").append(lastSize);
        sb.append(", volume24h=").append(volume24h);
        sb.append(", open24h=").append(open24h);
        sb.append(", low24h=").append(low24h);
        sb.append(", high24h=").append(high24h);
        sb.append(", side='").append(side).append('\'');
        sb.append(", clientOid='").append(clientOid).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", time='").append(time).append('\'');
        if ( userId != null )
        	sb.append(", userId='").append(userId).append('\'');
        if ( profileId != null )
        	sb.append(", profileId='").append(profileId).append('\'');
        if ( takerUserId != null )
        	sb.append(", takerUserId='").append(takerUserId).append('\'');
        if ( takerProfileId != null )
        	sb.append(", takerProfileId='").append(takerProfileId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
