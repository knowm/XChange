package org.knowm.xchange.coinex.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.coinex.dto.CoinexResponse;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.coinex.dto.account.CoinexBalances;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinexBalancesTest {

    @Test
    public void testUnmarshal() throws IOException {
        // Read in the JSON from the example resources
        InputStream is =
               CoinexBalancesTest.class.getResourceAsStream(
                       "/org/knowm/xchange/coinex/account/example-balance-info.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();

        CoinexResponse<CoinexBalances> coinexBalances = mapper.readValue(is,
               new TypeReference<CoinexResponse<CoinexBalances>>() {});

        Map<String, CoinexBalanceInfo> balances = coinexBalances.getData().getBalances();

        System.out.println(balances.keySet());
        CoinexBalanceInfo balanceInfo = balances.get("BTC");
        assertThat(balanceInfo.getFrozen()).isEqualTo("7000.00");
        assertThat(balanceInfo.getAvailable()).isEqualTo("32590.16");
    }

    @Test
    public void getBalanceTest() throws IOException{

        System.out.println(CoinexKeys.getExchange().getAccountService().getAccountInfo().getWallet());
    }
}
