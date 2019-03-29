package org.knowm.xchange.coindeal.dto.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoindealAccountDtoTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAccountBalanceTest() throws IOException {
        InputStream is =
                ClassLoader.getSystemClassLoader()
                        .getResourceAsStream("org/knowm/xchange/coindeal/dto/account/example-coindeal-balances.json");

        List<CoindealBalance> coindealBalances =
                mapper.readValue(is, new TypeReference<List<CoindealBalance>>(){});

        // verify that the example data was unmarshalled correctly
        assertThat(coindealBalances.get(0).getCurrency()).isEqualTo("currency");
        assertThat(coindealBalances.get(0).getAvailable()).isEqualTo("available");
        assertThat(coindealBalances.get(0).getReserved()).isEqualTo("reserved");
    }
}
