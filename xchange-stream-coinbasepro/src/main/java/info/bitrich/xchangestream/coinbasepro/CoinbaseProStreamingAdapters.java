package info.bitrich.xchangestream.coinbasepro;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProStreamingAdapters {

  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProStreamingAdapters.class);

  /** TODO this clearly isn't good enough. We need an initial snapshot that these can build on. */
  static Order adaptOrder(CoinbaseProWebSocketTransaction s) {
    switch (s.getType()) {
      case "activate":
      case "received":
        return CoinbaseProAdapters.adaptOrder(
            new CoinbaseProOrder(
                s.getOrderId(),
                s.getPrice(),
                s.getSize() == null ? BigDecimal.ZERO : s.getSize(),
                s.getProductId(),
                s.getSide(),
                s.getTime(), // createdAt,
                null, // doneAt,
                BigDecimal.ZERO, // filled size
                null, // fees
                s.getType(), // status - TODO no clean mapping atm
                false, // settled
                s.getType().equals("received") ? "limit" : "stop", // type. TODO market orders
                null, // doneReason
                null,
                null, // stop TODO no source for this
                null // stopPrice
                ));
      default:
        OrderType type = s.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
        CurrencyPair currencyPair = new CurrencyPair(s.getProductId().replace('-', '/'));
        return new LimitOrder.Builder(type, currencyPair)
            .id(s.getOrderId())
            .orderStatus(adaptOrderStatus(s))
            .build();
    }
  }

  private static OrderStatus adaptOrderStatus(CoinbaseProWebSocketTransaction s) {
    if (s.getType().equals("done")) {
      if (s.getReason().equals("canceled")) {
        return OrderStatus.CANCELED;
      } else {
        return OrderStatus.FILLED;
      }
    } else if (s.getType().equals("match")) {
      return OrderStatus.PARTIALLY_FILLED;
    } else {
      return OrderStatus.NEW;
    }
  }

  public static Date parseDate(final String rawDate) {

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
}
