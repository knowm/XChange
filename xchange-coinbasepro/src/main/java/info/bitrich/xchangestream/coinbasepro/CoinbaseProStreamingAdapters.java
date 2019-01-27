package info.bitrich.xchangestream.coinbasepro;

import static info.bitrich.xchangestream.core.OrderStatusChangeType.CLOSED;
import static info.bitrich.xchangestream.core.OrderStatusChangeType.OPENED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import info.bitrich.xchangestream.core.OrderStatusChange;
import info.bitrich.xchangestream.core.OrderStatusChange.Builder;

class CoinbaseProStreamingAdapters {

    private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProStreamingAdapters.class);

    private static Date parseDate(final String rawDate) {

        String modified;
        if (rawDate.length() > 23) {
            modified = rawDate.substring(0, 23);
        } else if (rawDate.endsWith("Z")) {
            switch (rawDate.length()) {
            case 20:
                modified = rawDate.substring(0, 19) + ".000";
                break;
            case 22:
                modified = rawDate.substring(0, 21) + "00";
                break;
            case 23:
                modified = rawDate.substring(0, 22) + "0";
                break;
            default:
                modified = rawDate;
                break;
            }
        } else {
            switch (rawDate.length()) {
            case 19:
                modified = rawDate + ".000";
                break;
            case 21:
                modified = rawDate + "00";
                break;
            case 22:
                modified = rawDate + "0";
                break;
            default:
                modified = rawDate;
                break;
            }
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(modified);
        } catch (ParseException e) {
            LOG.warn("unable to parse rawDate={} modified={}", rawDate, modified, e);
            return null;
        }
    }

    static OrderStatusChange adaptOrderStatusChange(CoinbaseProWebSocketTransaction s) {
        Builder result = OrderStatusChange.create()
            .orderId(s.getOrderId())
            .timestamp(parseDate(s.getTime()));
        switch (s.getType()) {
            case "open": return result.type(OPENED).build();
            case "done": return result.type(CLOSED).build();
            default: throw new IllegalStateException(s.getType() + " is not an order status change websocket transaction type");
        }
    }
}