package info.bitrich.xchangestream.dydx;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class dydxStreamingAdaptersTest {
  @Test
  public void dydxOrderBookChanges_nullChanges() {
    SortedMap<BigDecimal, BigDecimal> sideEntries = new TreeMap<>(Comparator.reverseOrder());

    List<LimitOrder> limitOrders =
        dydxStreamingAdapters.dydxOrderBookChanges(
            Order.OrderType.BID, CurrencyPair.ETH_USD, null, sideEntries, 10, false);

    Assert.assertEquals(0, limitOrders.size());
    Assert.assertEquals(0, sideEntries.size());
  }

  @Test
  public void dydxOrderBookChanges() {
    SortedMap<BigDecimal, BigDecimal> sideEntries = new TreeMap<>(Comparator.reverseOrder());

    List<LimitOrder> limitOrders =
        dydxStreamingAdapters.dydxOrderBookChanges(
            Order.OrderType.BID,
            CurrencyPair.ETH_USD,
            new String[][] {{"1840", "10"}, {"1850", "1"}},
            sideEntries,
            10,
            false);

    Assert.assertEquals(2, limitOrders.size());
    Assert.assertEquals(new BigDecimal(1850), limitOrders.get(0).getLimitPrice());
    Assert.assertEquals(new BigDecimal(1), limitOrders.get(0).getOriginalAmount());

    Assert.assertEquals(2, sideEntries.size());
    Assert.assertEquals(new BigDecimal(1850), sideEntries.firstKey());
    Assert.assertEquals(new BigDecimal(1), sideEntries.get(sideEntries.firstKey()));
  }
}
