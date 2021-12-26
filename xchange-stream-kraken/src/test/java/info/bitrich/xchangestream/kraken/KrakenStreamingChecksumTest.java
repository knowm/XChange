package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.TreeSet;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class KrakenStreamingChecksumTest {
  private StringBuilder sb;

  private TreeSet<LimitOrder> asks =
      Sets.newTreeSet(
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05005"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05010"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05015"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05020"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05025"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05030"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05035"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05040"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05045"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05050"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05050"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05055"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05060"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05065"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build());
  private TreeSet<LimitOrder> bids =
      Sets.newTreeSet(
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.05000"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04995"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04990"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04980"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04975"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04970"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04965"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04960"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04955"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04950"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04945"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04940"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04935"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build(),
          new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USD)
              .limitPrice(new BigDecimal("0.04930"))
              .originalAmount(new BigDecimal("0.00000500"))
              .build());

  private final String expectedCrcString =
      "50055005010500501550050205005025500503050050355005040500504550050505005000500499550049905004980500497550049705004965500496050049555004950500";

  @Before
  public void setUp() {
    this.sb = new StringBuilder();
  }

  @Test
  public void testAddBigDecimal() {
    KrakenStreamingChecksum.addBigDecimalToCrcString(this.sb, new BigDecimal("0.05005"));
    assertThat(sb.toString()).isEqualTo("5005");
  }

  @Test
  public void testAddBigDecimalWithDecimalEndingInZero() {
    KrakenStreamingChecksum.addBigDecimalToCrcString(this.sb, new BigDecimal("0.05000"));
    assertThat(sb.toString()).isEqualTo("5000");
    this.setUp();
    KrakenStreamingChecksum.addBigDecimalToCrcString(this.sb, new BigDecimal("5000"));
    assertThat(sb.toString()).isEqualTo("5000");
  }

  @Test
  public void testAddOrderbook() {
    String crcString = KrakenStreamingChecksum.createCrcString(asks, bids);
    assertThat(crcString).isEqualTo(expectedCrcString);
  }

  @Test
  public void testCreateCrcLong() {
    long crcLong = KrakenStreamingChecksum.createCrcLong(expectedCrcString);
    assertThat(crcLong).isEqualTo(974947235L);
  }
}
