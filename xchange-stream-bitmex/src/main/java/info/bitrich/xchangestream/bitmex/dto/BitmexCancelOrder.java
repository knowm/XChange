package info.bitrich.xchangestream.bitmex.dto;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.Date;

public class BitmexCancelOrder extends Order {
    public BitmexCancelOrder(BigDecimal originalAmount, Instrument instrument, String id, Date timestamp, BigDecimal averagePrice, BigDecimal cumulativeAmount, String userReference) {
        super(null, originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, null, OrderStatus.CANCELED, userReference);
    }

    public static class BitmexCancelOrderBuilder extends Order.Builder{
        protected BitmexCancelOrderBuilder(Instrument instrument) {
            super(null, instrument);
        }

        @Override
        public Order build() {
            return new BitmexCancelOrder(originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, userReference);
        }
    }
}
