package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;

import java.math.BigDecimal;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexTrade extends BitmexLimitOrder {

    private String trdMatchID;

    @JsonCreator
    public BitmexTrade(@JsonProperty("symbol") String symbol,
                       @JsonProperty("side") String side,
                       @JsonProperty("price") BigDecimal price,
                       @JsonProperty("size") BigDecimal size,
                       @JsonProperty("trdMatchID") String trdMatchID,
                       @JsonProperty("timestamp") String timestamp) {
        super(symbol, "", side, price, size, timestamp);
        this.trdMatchID = trdMatchID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTrdMatchID() {
        return trdMatchID;
    }

    public Trade toTrade() {
        CurrencyPair pair = getCurrencyPair();
        Order.OrderType orderType = getOrderSide();
        return new Trade(orderType, size, pair, price, getDate(), trdMatchID);
    }
}
