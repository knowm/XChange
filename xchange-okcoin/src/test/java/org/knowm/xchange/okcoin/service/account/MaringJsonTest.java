package org.knowm.xchange.okcoin.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.okcoin.v3.dto.account.MarginAccountResponse;
import org.knowm.xchange.okcoin.v3.dto.account.MarginAccountSettingsRecord;

/** @author timmolter */
public class MaringJsonTest {

  @Test
  public void account() throws Exception {
    MarginAccountResponse[] res =
        readJson("example-maring-account.json", MarginAccountResponse[].class);
    assertThat(res[0].getCurrencyInfo().get("currency:BTC").getAvailable()).isEqualTo("0.021348");
    assertThat(res[0].getMaintMarginRatio()).isEqualTo("0.05");
  }

  @Test
  public void accountSettings() throws Exception {
    MarginAccountSettingsRecord[] res =
        readJson("example-maring-account-settings.json", MarginAccountSettingsRecord[].class);
    assertThat(res[0].getCurrencyInfo().get("currency:BTC").getAvailable()).isEqualTo("0.09995502");
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
