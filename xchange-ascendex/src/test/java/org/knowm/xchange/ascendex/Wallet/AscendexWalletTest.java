package org.knowm.xchange.ascendex.Wallet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.enums.AscendexTransactionType;
import org.knowm.xchange.ascendex.dto.wallet.AscendDepositAddressesDto;
import org.knowm.xchange.ascendex.dto.wallet.AscendexWalletTransactionHistoryDto;

import org.knowm.xchange.ascendex.service.AscendexAccountServiceRaw;

import java.io.IOException;
import java.io.InputStream;

/**
 * Package:org.knowm.xchange.ascendex.wallet
 * Description:
 *
 * @date:2022/7/18 11:57
 * @author:wodepig
 */
public class AscendexWalletTest {
    private AscendexAccountServiceRaw serviceRaw;
    // @Before
    public void getAccountServiceRaw() throws IOException {
        InputStream is =
                org.knowm.xchange.ascendex.AscendexMarketDataJSONTest.class.getResourceAsStream(
                        "/org/knowm/xchange/ascendex/ascendexApiInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(is);
        ExchangeSpecification exSpec = new AscendexExchange().getDefaultExchangeSpecification();
        exSpec.setSslUri("https://asdx.me/");
        exSpec.setApiKey(tree.get("apiKey").asText());
        exSpec.setSecretKey(tree.get("secretKey").asText());
        exSpec.setExchangeSpecificParametersItem("account-group",tree.get("accountGroup").asInt());
        exSpec.setShouldLoadRemoteMetaData(false);
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
        serviceRaw = (AscendexAccountServiceRaw)exchange.getAccountService();

    }

    // @Test
    public void testAscendDepositAddresses()throws IOException{
        AscendDepositAddressesDto sdt = serviceRaw.getAscendDepositAddresses("USDT");
        Assert.assertTrue(sdt.getAddress().size()>2);
        AscendDepositAddressesDto usdtErc20 = serviceRaw.getAscendDepositAddresses("USDT", "erc20");
        Assert.assertEquals(1, usdtErc20.getAddress().size());
    }

    // @Test
    public void testAscendexWalletTransactionHistory()throws IOException{

        AscendexWalletTransactionHistoryDto allHistory = serviceRaw.getAscendexWalletTransactionHistory();
        Assert.assertTrue(allHistory.getData().size()>0);
        AscendexWalletTransactionHistoryDto transactionHistory = serviceRaw.getAscendexWalletTransactionHistory(
                "btc", AscendexTransactionType.withdrawal,1,5
        );
        Assert.assertNotNull(transactionHistory);
    }
}
