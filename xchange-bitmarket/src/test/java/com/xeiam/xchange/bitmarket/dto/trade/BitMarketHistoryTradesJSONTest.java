package com.xeiam.xchange.bitmarket.dto.trade;

import com.xeiam.xchange.bitmarket.dto.BitMarketDtoTestSupport;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author kfonal
 */
public class BitMarketHistoryTradesJSONTest extends BitMarketDtoTestSupport {

  @Test
  public void testUnmarshal() throws IOException {
    // when
    BitMarketHistoryTradesResponse response = parse("trade/example-history-trades-data", BitMarketHistoryTradesResponse.class);

    // then
    BitMarketHistoryTrades trades = response.getData();
    assertThat(response.getSuccess()).isTrue();
    assertThat(trades.getTotal()).isEqualTo(5);
    assertThat(trades.getTrades().size()).isEqualTo(5);

    BitMarketHistoryTrade trade = trades.getTrades().get(0);

    assertThat(trade.getAmountCrypto()).isEqualTo(new BigDecimal("1.08260046"));
    assertThat(trade.getCurrencyCrypto()).isEqualTo("BTC");
    assertThat(trade.getRate()).isEqualTo(new BigDecimal("877.0000"));
    assertThat(trade.getType()).isEqualTo("sell");
    assertThat(trade.getId()).isEqualTo(389406);
  }
}
