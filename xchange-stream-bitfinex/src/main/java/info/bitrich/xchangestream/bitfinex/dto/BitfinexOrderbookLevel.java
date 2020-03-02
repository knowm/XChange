package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;

import java.math.BigDecimal;

/**
 * Created by Lukas Zaoralek on 8.11.17.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"price","count","amount"})
public class BitfinexOrderbookLevel {

    public BigDecimal price;

    public BigDecimal count;

    public BigDecimal amount;

    public BitfinexOrderbookLevel() {
    }

    public BitfinexOrderbookLevel(BigDecimal price, BigDecimal count, BigDecimal amount) {
        this.price = price;
        this.amount = amount;
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCount() {
        return count;
    }

    public BitfinexLevel toBitfinexLevel() {
        // Xchange-bitfinex adapter expects the timestamp to be seconds since Epoch.
        return new BitfinexLevel(price, amount, new BigDecimal(System.currentTimeMillis() / 1000));
    }
}
