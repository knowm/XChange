package org.knowm.xchange.kucoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test Marketdata JSON parsing */
public class KucoinMarketdataUnmarshalTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testCoinUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KucoinMarketdataUnmarshalTest.class.getResourceAsStream(
            "/org/knowm/xchange/kucoin/dto/marketdata/example-coin.json");
    KucoinCoin coin = mapper.readValue(is, KucoinCoin.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coin.getWithdrawMinFee()).isEqualTo(BigDecimal.valueOf(0.05));
    assertThat(coin.getWithdrawMinAmount()).isEqualTo(BigDecimal.valueOf(0.1));
    assertThat(coin.getWithdrawFeeRate()).isEqualTo(BigDecimal.valueOf(0.001));
    assertThat(coin.getConfirmationCount()).isEqualTo(12);
    assertThat(coin.getWithdrawRemark()).isEqualTo("");
    assertThat(coin.getInfoUrl()).isNull();
    assertThat(coin.getName()).isEqualTo("RaiBlocks");
    assertThat(coin.getTradePrecision()).isEqualTo(6);
    assertThat(coin.getDepositRemark()).isEqualTo("");
    assertThat(coin.getEnableWithdraw()).isTrue();
    assertThat(coin.getEnableDeposit()).isTrue();
    assertThat(coin.getCoin()).isEqualTo("XRB");
  }

  @Test
  public void testTickerUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KucoinMarketdataUnmarshalTest.class.getResourceAsStream(
            "/org/knowm/xchange/kucoin/dto/marketdata/example-ticker.json");
    KucoinTicker ticker = mapper.readValue(is, KucoinTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getCoinType()).isEqualTo("XRB");
    assertThat(ticker.getTrading()).isTrue();
    assertThat(ticker.getSymbol()).isEqualTo("XRB-BTC");
    assertThat(ticker.getLastDealPrice()).isEqualTo(BigDecimal.valueOf(0.0015725));
    assertThat(ticker.getBuy()).isEqualTo(BigDecimal.valueOf(0.00157239));
    assertThat(ticker.getSell()).isEqualTo(BigDecimal.valueOf(0.0015725));
    assertThat(ticker.getChange()).isEqualTo(BigDecimal.valueOf(4.1663E-4));
    assertThat(ticker.getCoinTypePair()).isEqualTo("BTC");
    assertThat(ticker.getSort()).isEqualTo(0);
    assertThat(ticker.getFeeRate()).isEqualTo(BigDecimal.valueOf(0.001));
    assertThat(ticker.getVolValue()).isEqualTo(BigDecimal.valueOf(1403.39395495));
    assertThat(ticker.getHigh()).isEqualTo(BigDecimal.valueOf(0.001646));
    assertThat(ticker.getDatetime()).isEqualTo(1517010896000L);
    assertThat(ticker.getVol()).isEqualTo(BigDecimal.valueOf(1005560.929112));
    assertThat(ticker.getLow()).isEqualTo(BigDecimal.valueOf(0.00114983));
    assertThat(ticker.getChangeRate()).isEqualTo(BigDecimal.valueOf(0.3604));
  }
}
