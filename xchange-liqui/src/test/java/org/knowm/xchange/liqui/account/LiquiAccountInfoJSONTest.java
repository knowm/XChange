package org.knowm.xchange.liqui.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.liqui.dto.account.LiquiAccountFunds;
import org.knowm.xchange.liqui.dto.account.result.LiquiAccountInfoResult;
import org.knowm.xchange.liqui.marketdata.LiquiTickerJSONTest;

public class LiquiAccountInfoJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-getinfo-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiAccountInfoResult infoResult = mapper.readValue(is, LiquiAccountInfoResult.class);

    final LiquiAccountFunds resultFunds = infoResult.getResult().getFunds();
    final Map<Currency, BigDecimal> funds = resultFunds.getFunds();

    final BigDecimal btcFunds = funds.get(Currency.BTC);
    assertThat(btcFunds).isEqualTo(new BigDecimal("0.0"));

    final BigDecimal ltcFunds = funds.get(Currency.LTC);
    assertThat(ltcFunds).isEqualTo(new BigDecimal("1.123"));

    assertThat(funds.get(Currency.ETH)).isNull();

    assertThat(infoResult.isSuccess()).isTrue();
    assertThat(infoResult.getResult().getOpenOrders()).isEqualTo(0);
    assertThat(infoResult.getResult().getTransactionCount()).isEqualTo(0);

    assertThat(infoResult.getResult().getRights().isInfo()).isTrue();
    assertThat(infoResult.getResult().getRights().isTrade()).isTrue();
    assertThat(infoResult.getResult().getRights().isWithdraw()).isFalse();
  }
}
