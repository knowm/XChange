package org.knowm.xchange.lykke;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.lykke.dto.trade.LykkeOrder;

public class LykkeOrderTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void orderbookUnmarshalTest() throws IOException {
    InputStream is =
        LykkeAssetsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lykke/example-lykkeOrder.json");
    LykkeOrder[] lykkeOrderRespons = mapper.readValue(is, LykkeOrder[].class);

    assertThat(lykkeOrderRespons[0].getId()).isEqualTo("string");
    assertThat(lykkeOrderRespons[0].getStatus()).isEqualTo("string");
    assertThat(lykkeOrderRespons[0].getAssetPairId()).isEqualTo("string");
    assertThat(lykkeOrderRespons[0].getVolume()).isEqualTo(0);
    assertThat(lykkeOrderRespons[0].getPrice()).isEqualTo(0);
    assertThat(lykkeOrderRespons[0].getRemainingVolume()).isEqualTo(0);
    assertThat(lykkeOrderRespons[0].getLastMatchTime()).isEqualTo("2018-07-19T21:19:48.544Z");
    assertThat(lykkeOrderRespons[0].getCreatedAt()).isEqualTo("2018-07-19T21:19:48.544Z");
  }
}
