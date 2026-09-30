package info.bitrich.xchangestream.dydx;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

class dydxStreamingAdaptersTest {
  @Test
  void dydxOrderBookChanges_nullChanges() {
    SortedMap<BigDecimal, BigDecimal> sideEntries = new TreeMap<>(Comparator.reverseOrder());

    List<LimitOrder> limitOrders =
        dydxStreamingAdapters.dydxOrderBookChanges(
            Order.OrderType.BID, CurrencyPair.ETH_USD, null, sideEntries, 10, false);

    assertThat(limitOrders.size()).isEqualTo(0);
    assertThat(sideEntries.size()).isEqualTo(0);
  }

  @Test
  void dydxOrderBookChanges() {
    SortedMap<BigDecimal, BigDecimal> sideEntries = new TreeMap<>(Comparator.reverseOrder());

    List<LimitOrder> limitOrders =
        dydxStreamingAdapters.dydxOrderBookChanges(
            Order.OrderType.BID,
            CurrencyPair.ETH_USD,
            new String[][] {{"1840", "10"}, {"1850", "1"}},
            sideEntries,
            10,
            false);

    assertThat(limitOrders.size()).isEqualTo(2);
    assertThat(limitOrders.get(0).getLimitPrice()).isEqualTo(new BigDecimal(1850));
    assertThat(limitOrders.get(0).getOriginalAmount()).isEqualTo(new BigDecimal(1));

    assertThat(sideEntries.size()).isEqualTo(2);
    assertThat(sideEntries.firstKey()).isEqualTo(new BigDecimal(1850));
    assertThat(sideEntries.get(sideEntries.firstKey())).isEqualTo(new BigDecimal(1));
  }
}
