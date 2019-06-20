package org.knowm.xchange.cobinhood.service.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodCurrencyPair;

public class CobinhoodMarketDataDtoTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void cobinhoodCurrencyPairsTest() throws IOException {

    InputStream is =
        CobinhoodMarketDataDtoTest.class.getResourceAsStream(
            "/org/knowm/xchange/cobinhood/dto/currencypair-example.json");

    CobinhoodResponse<List<CobinhoodCurrencyPair>> cobinhoodResponse =
        mapper.readValue(
            is, new TypeReference<CobinhoodResponse<List<CobinhoodCurrencyPair>>>() {});

    //        verify that the example data was unmarshalled correctly
    assertThat(cobinhoodResponse.getResult().get(0).getId()).isEqualTo("BCHSV-USDT");
    assertThat(cobinhoodResponse.getResult().get(0).getBaseMaxSize()).isEqualTo("4590.393");
    assertThat(cobinhoodResponse.getResult().get(0).getTakerFee()).isEqualTo("0");
  }
}
