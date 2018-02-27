package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Lukas Zaoralek on 8.11.17.
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketUpdateOrderbook extends BitfinexWebSocketOrderbookTransaction {
    public BitfinexOrderbookLevel level;

    public BitfinexWebSocketUpdateOrderbook() {
    }

    public BitfinexWebSocketUpdateOrderbook(BitfinexOrderbookLevel level) {
        this.level = level;
    }

    @Override
    public BitfinexOrderbook toBitfinexOrderBook(BitfinexOrderbook orderbook) {
        orderbook.updateLevel(level);
        return orderbook;
    }
}
