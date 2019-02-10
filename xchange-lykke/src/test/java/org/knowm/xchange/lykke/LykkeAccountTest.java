package org.knowm.xchange.lykke;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.lykke.dto.account.LykkeWallet;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LykkeAccountTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUnmarshal() throws IOException {
        // Read in the JSON from the example resources
        InputStream is =
                LykkeAssetsTest.class.getResourceAsStream(
                        "/org/knowm/xchange/lykke/example-lykkeWallet.json");
        LykkeWallet[] wallets = mapper.readValue(is, LykkeWallet[].class);

        assertThat(wallets[0].getAssetId()).isEqualTo("string");
        assertThat(wallets[0].getBalance()).isEqualTo(0);
        assertThat(wallets[0].getReserved()).isEqualTo(0);
        assertThat(wallets[1].getAssetId()).isEqualTo("string1");
        assertThat(wallets[1].getBalance()).isEqualTo(1);
        assertThat(wallets[1].getReserved()).isEqualTo(1);

    }

    @Test
    public void testAccountBalances() throws IOException{
        Exchange exchange = LykkeKeys.getExchange();
        System.out.println(exchange.getAccountService().getAccountInfo().getWallets());
    }
}
