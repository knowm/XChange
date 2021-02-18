package info.bitrich.xchangestream.bitso;

import info.bitrich.xchangestream.bitso.dto.BitsoTrades;
import info.bitrich.xchangestream.bitso.dto.BitsoWebSocketTransaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsoStreamingAdapters {

  private static final Logger LOG = LoggerFactory.getLogger(BitsoStreamingAdapters.class);

  /** TODO this clearly isn't good enough. We need an initial snapshot that these can build on. */
  static Order adaptOrder(BitsoWebSocketTransaction s) {
    System.out.println(s);
    switch (s.getEventType()) {
      case "activate":
      case "received":
        return null;
//        return BitsoAdapters.adaptOrder(
////            new BitsoOrderBook(
////                s.getOrderId(),
////                s.getPrice(),
////                s.getSize() == null ? BigDecimal.ZERO : s.getSize(),
////                s.getProductId(),
////                s.getSide(),
////                s.getTime(), // createdAt,
////                null, // doneAt,
////                BigDecimal.ZERO, // filled size
////                null, // fees
////                s.getType(), // status - TODO no clean mapping atm
////                false, // settled
////                s.getType().equals("received") ? "limit" : "stop", // type. TODO market orders
////                null, // doneReason
////                null,
////                null, // stop TODO no source for this
////                null // stopPrice
////                ));
      default:
        return null;
//        OrderType type = s.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
//        CurrencyPair currencyPair = new CurrencyPair(s.getProductId().replace('-', '/'));
//        return new LimitOrder.Builder(type, currencyPair)
//            .id(s.getOrderId())
//            .orderStatus(adaptOrderStatus(s))
//            .build();
    }
  }

  private static OrderStatus adaptOrderStatus(BitsoWebSocketTransaction s) {
    if (s.getEventType().equals("done")) {
//      if (s.getReason().equals("canceled")) {
//        return OrderStatus.CANCELED;
//      } else {
        return OrderStatus.FILLED;
//      }
    } else if (s.getEventType().equals("match")) {
      return OrderStatus.PARTIALLY_FILLED;
    } else {
      return OrderStatus.NEW;
    }
  }

  public static Trades adaptTrades(BitsoTrades[] coinbaseExTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList(coinbaseExTrades.length);
    BitsoTrades[] var3 = coinbaseExTrades;
    int var4 = coinbaseExTrades.length;

    for(int var5 = 0; var5 < var4; ++var5) {
      BitsoTrades trade = var3[var5];
      Order.OrderType type = trade.getSide().equals("sell") ? Order.OrderType.BID : Order.OrderType.ASK;
      Trade t = (new org.knowm.xchange.dto.marketdata.Trade.Builder()).type(type).originalAmount(trade.getSize()).price(trade.getPrice()).currencyPair(currencyPair).timestamp(parseDate(trade.getTimestamp())).id(String.valueOf(trade.getTradeId())).makerOrderId(trade.getMakerOrderId()).takerOrderId(trade.getTakerOrderId()).build();
      trades.add(t);
    }

    return new Trades(trades, coinbaseExTrades[0].getTradeId(), Trades.TradeSortType.SortByID);
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
      if(!ObjectUtils.isEmpty(modified)){
        return dateFormat.parse(modified);
      }else {
        return dateFormat.parse(dateFormat.format(new Date()));
      }
    } catch (ParseException e) {
      LOG.warn("unable to parse rawDate={} modified={}", rawDate, modified, e);
      return null;
    }
  }
}
