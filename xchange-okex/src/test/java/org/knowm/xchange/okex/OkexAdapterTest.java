package org.knowm.xchange.okex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.okex.dto.OkexInstType.SPOT;
import static org.knowm.xchange.okex.dto.OkexInstType.SWAP;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;

public class OkexAdapterTest {
  @Test
  public void testAdaptTradingFee() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    InputStream is = OkexAdapterTest.class.getResourceAsStream("/getFeeRatesSpot.json5");
    OkexTradeFee okexSpotTradeFee =
        mapper
            .readValue(is, new TypeReference<OkexResponse<List<OkexTradeFee>>>() {})
            .getData()
            .get(0);
    assertThat(OkexAdapters.adaptTradingFee(okexSpotTradeFee, SPOT, new CurrencyPair("USDT/SGD")))
        .isEqualTo(new Fee(new BigDecimal("0.0005"), new BigDecimal("0.0007")));
    assertThat(OkexAdapters.adaptTradingFee(okexSpotTradeFee, SPOT, new CurrencyPair("OKB/BTC")))
        .isEqualTo(new Fee(new BigDecimal("0.0005"), new BigDecimal("0.0007")));
    assertThat(OkexAdapters.adaptTradingFee(okexSpotTradeFee, SPOT, new CurrencyPair("USDC/USDT")))
        .isEqualTo(new Fee(new BigDecimal("0.0008"), new BigDecimal("0.001")));
    assertThat(OkexAdapters.adaptTradingFee(okexSpotTradeFee, SPOT, new CurrencyPair("EUR/USDT")))
        .isEqualTo(new Fee(new BigDecimal("0.0008"), new BigDecimal("0.001")));

    is = OkexAdapterTest.class.getResourceAsStream("/getFeeRatesSwap.json5");
    OkexTradeFee okexSwapTradeFee =
        mapper
            .readValue(is, new TypeReference<OkexResponse<List<OkexTradeFee>>>() {})
            .getData()
            .get(0);
    assertThat(
            OkexAdapters.adaptTradingFee(
                okexSwapTradeFee, SWAP, new FuturesContract("BTC/USDT/SWAP")))
        .isEqualTo(new Fee(new BigDecimal("0.0002"), new BigDecimal("0.0005")));
    assertThat(
            OkexAdapters.adaptTradingFee(
                okexSwapTradeFee, SWAP, new FuturesContract("BTC/USDT/SWAP")))
        .isEqualTo(new Fee(new BigDecimal("0.0002"), new BigDecimal("0.0005")));
    assertThat(
            OkexAdapters.adaptTradingFee(
                okexSwapTradeFee, SWAP, new FuturesContract("USDC/USDT/SWAP")))
        .isEqualTo(new Fee(new BigDecimal("0.0002"), new BigDecimal("0.0005")));
    // currently no USD support in OKX swap
    //    assertThat(OkexAdapters.adaptTradingFee(okexSwapTradeFee,SWAP, new
    // FuturesContract("BTC/USD/SWAP")))
    //        .isEqualTo(new Fee(new BigDecimal("-0.0002"),new BigDecimal("-0.0005")));
  }
}
