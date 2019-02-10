package org.knowm.xchange.cobinhood.service.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CobinhoodMarketDataDtoTest {

    @Test
    public void cobinhoodCurrencyPairsTest() throws IOException {

        InputStream is = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("/org/knowm/xchange/cobinhood/dto/currencypair-example.json");

        ObjectMapper mapper = new ObjectMapper();
//        CobinhoodResponse cobinhoodResponse = mapper.readValue(is,new CobinhoodResponse<CobinhoodCurrencyPair>());
//        System.out.println(cobinhoodResponse.getId());


        //verify that the example data was unmarshalled correctly
//        assertThat(cobinhoodResponse.getId()).isEqualTo("ABT-BTC");
//        assertThat(coindealTradeHistory.get(0).getLast_price()).isEqualTo("0.000041");
//        assertThat(coindealTradeHistory.get(0).getLowest_ask()).isEqualTo("0.00006697");
    }
}
