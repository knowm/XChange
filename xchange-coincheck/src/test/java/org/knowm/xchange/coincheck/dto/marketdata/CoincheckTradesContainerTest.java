package org.knowm.xchange.coincheck.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.coincheck.CoincheckTestUtil;

/** Tests CoincheckOrderBook JSON parsing */
public class CoincheckTradesContainerTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources.
    CoincheckTradesContainer container =
        CoincheckTestUtil.load(
            "dto/marketdata/example-trades-data.json", CoincheckTradesContainer.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(container.isSuccess()).isTrue();

    assertThat(container.getPagination()).isNotNull();
    assertThat(container.getPagination().getLimit()).isEqualTo(10);
    assertThat(container.getPagination().getOrder()).isEqualTo("desc");
    assertThat(container.getPagination().getStartingAfter()).isNull();
    assertThat(container.getPagination().getEndingBefore()).isNull();

    List<CoincheckTrade> trades = container.getData();
    assertThat(trades).isNotNull();
    assertThat(trades.size()).isEqualTo(10);

    CoincheckTrade trade = trades.get(0);
    assertThat(trade).isNotNull();
    assertThat(trade.getId()).isEqualTo(211288499);
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("0.015"));
    assertThat(trade.getRate()).isEqualTo(new BigDecimal("5053021.0"));
    assertThat(trade.getPair()).isEqualTo("btc_jpy");
    assertThat(trade.getOrderType()).isEqualTo("buy");
    assertThat(trade.getCreatedAt()).isEqualTo(Instant.parse("2022-02-07T16:01:32.000Z"));
  }
}
