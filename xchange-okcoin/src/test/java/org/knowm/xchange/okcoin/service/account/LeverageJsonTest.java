package org.knowm.xchange.okcoin.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.okcoin.v3.dto.MarginMode;
import org.knowm.xchange.okcoin.v3.dto.account.FuturesLeverageResponse;
import org.knowm.xchange.okcoin.v3.dto.account.FuturesLeverageResponse.FixedLeverage;

/** @author timmolter */
public class LeverageJsonTest {

  @Test
  public void crossedLeverage() throws Exception {
    FuturesLeverageResponse res =
        readJson("example-leverage-crossed.json", FuturesLeverageResponse.class);
    assertThat(res.getMarginMode()).isEqualTo(MarginMode.crossed);
    assertThat(res.getCurrency()).isEqualTo("BTC");
    assertThat(res.getLeverage()).isEqualTo(BigDecimal.TEN);
    assertThat(res.getFixedLeverages()).isEmpty();
  }

  @Test
  public void fixedLeverage() throws Exception {
    FuturesLeverageResponse res =
        readJson("example-leverage-fixed.json", FuturesLeverageResponse.class);

    assertThat(res.getMarginMode()).isEqualTo(MarginMode.fixed);
    assertThat(res.getCurrency()).isNull();
    assertThat(res.getLeverage()).isNull();
    Map<String, FixedLeverage> fixedLeverages = res.getFixedLeverages();
    assertThat(fixedLeverages).hasSize(3);
    assertThat(fixedLeverages.get("BTC-USD-190329").getLongLeverage())
        .isEqualTo(new BigDecimal(100));
  }

  private static <R> R readJson(String file, Class<R> clz)
      throws IOException, JsonParseException, JsonMappingException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        OkCoinAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/okcoin/dto/account/" + file);
    R res = mapper.readValue(is, clz);
    return res;
  }
}
