package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;

import java.math.BigDecimal;

/**
 * Created by Lukas Zaoralek on 8.11.17.
 */
@JsonFormat(shape= JsonFormat.Shape.ARRAY)
public class BitfinexOrderbookLevel {
    public BigDecimal orderId;
    public BigDecimal price;
    public BigDecimal amount;

    public BitfinexOrderbookLevel() { }

    public BitfinexOrderbookLevel(BigDecimal price, BigDecimal amount, BigDecimal orderId) {
        this.price = price;
        this.amount = amount;
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getOrderId() {
        return orderId;
    }

    public BitfinexLevel toBitfinexLevel() {
        return new BitfinexLevel(price, amount, new BigDecimal(System.currentTimeMillis()));
    }
}
