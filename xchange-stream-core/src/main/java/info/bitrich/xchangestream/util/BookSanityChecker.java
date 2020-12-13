package info.bitrich.xchangestream.util;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

/** @author Foat Akhmadeev 05/06/2018 */
public class BookSanityChecker {

  public static String hasErrors(OrderBook book) {
    List<LimitOrder> asks = book.getOrders(Order.OrderType.ASK);
    if (!Objects.equals(asks, book.getAsks())) return "Asks did not match for OrderBook";
    List<LimitOrder> bids = book.getOrders(Order.OrderType.BID);
    if (!Objects.equals(bids, book.getBids())) return "Bids did not match for OrderBook";

    LimitOrder bestAsk = null;
    if (!asks.isEmpty()) {
      bestAsk = asks.get(0);
      String askCheck = hasErrors(asks.iterator());
      if (askCheck != null) return askCheck;
    }

    LimitOrder bestBid = null;
    if (!bids.isEmpty()) {
      bestBid = bids.get(0);
      String bidCheck = hasErrors(CollectionUtils.descendingIterable(bids).iterator());
      if (bidCheck != null) return bidCheck;
    }

    if (bestAsk != null
        && bestBid != null
        && bestAsk.getLimitPrice().compareTo(bestBid.getLimitPrice()) <= 0)
      return format("Got incorrect best ask and bid %s, %s", bestAsk, bestBid);
    return null;
  }

  public static String hasErrors(Iterator<LimitOrder> side) {
    if (!side.hasNext()) return null;

    LimitOrder prev = side.next();
    String check = hasErrors(prev);
    if (check != null) return check;
    while (side.hasNext()) {
      LimitOrder curr = side.next();
      check = hasErrors(curr);
      if (check != null) return check;
      if (prev.getLimitPrice().compareTo(curr.getLimitPrice()) >= 0)
        return format("Wrong price order for LimitOrders %s, %s", prev, curr);
      prev = curr;
    }
    return null;
  }

  public static String hasErrors(LimitOrder limitOrder) {
    if (limitOrder.getOriginalAmount().compareTo(BigDecimal.ZERO) <= 0)
      return format("LimitOrder amount is <= 0 for %s", limitOrder);
    else return null;
  }
}
