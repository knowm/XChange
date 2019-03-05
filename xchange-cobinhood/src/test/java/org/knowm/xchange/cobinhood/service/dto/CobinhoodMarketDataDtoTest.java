package org.knowm.xchange.cobinhood.service.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodCurrencyPair;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CobinhoodMarketDataDtoTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void cobinhoodCurrencyPairsTest() throws IOException {

        InputStream is = CobinhoodMarketDataDtoTest.class
                .getResourceAsStream("/org/knowm/xchange/cobinhood/dto/currencypair-example.json");

        CobinhoodResponse<List<CobinhoodCurrencyPair>> cobinhoodResponse = mapper.readValue(is,new TypeReference<CobinhoodResponse<List<CobinhoodCurrencyPair>>>(){});


//        verify that the example data was unmarshalled correctly
        assertThat(cobinhoodResponse.getResult().get(0).getId()).isEqualTo("ABT-BTC");
        assertThat(cobinhoodResponse.getResult().get(0).getLast_price()).isEqualTo("0.000041");
        assertThat(cobinhoodResponse.getResult().get(0).getLowest_ask()).isEqualTo("0.00006697");
    }
}
