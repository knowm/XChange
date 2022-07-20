package org.knowm.xchange.ascendex.Balance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.balance.*;
import org.knowm.xchange.ascendex.service.AscendexAccountServiceRaw;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.balance
 * Description:
 *
 * @date:2022/7/16 21:37
 * @author:wodepig
 */
public class AscendexBalanceTest {
    private AscendexAccountServiceRaw serviceRaw;
    @Before
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

    @Test
    public void testCashAccountBalance()throws IOException{
        List<AscendexCashAccountBalanceDto> cash = serviceRaw.getAscendexCashAccountBalance();
        List<AscendexCashAccountBalanceDto> cash1 = serviceRaw.getAscendexCashAccountBalance("TRX",true);
        Assert.assertNotNull(cash);
        Assert.assertNotNull(cash1);

    }

    @Test
    public void testMarginAccountBalance()throws IOException{
        List<AscendexMarginAccountBalanceDto> cash = serviceRaw.getAscendexMarginAccountBalance();
        List<AscendexMarginAccountBalanceDto> cash1 = serviceRaw.getAscendexMarginAccountBalance("TRX",false);
        Assert.assertNotNull(cash);

    }

    @Test
    public void testAscendexMarginRiskDto()throws IOException{
        AscendexMarginRiskDto ascendexMarginRiskDto = serviceRaw.getAscendexMarginRisk();
        Assert.assertNotNull(ascendexMarginRiskDto);
    }

    @Test
    public void testAscendexBalanceTransfer()throws IOException{
        AscendexBalanceTransferRequestPayload payload= new AscendexBalanceTransferRequestPayload(   "100",
                "ABCD",
                AccountCategory.cash,
                AccountCategory.margin);
        Boolean transfer = serviceRaw.getAscendexBalanceTransfer(payload);
        Assert.assertFalse(transfer);
    }

    @Test
    public void testAscendexBalanceSnapshot()throws IOException{
        AscendexBalanceSnapshotDto cashSnapshot = serviceRaw.getAscendexBalanceSnapshot("2022-07-16",AccountCategory.margin);
        Assert.assertNotNull(cashSnapshot);
    }

    @Test
    public void testAscendexOrderAndBalanceDetail()throws IOException{
        /**
         * TODO https://github.com/ascendex/ascendex-pro-api-demo/blob/4aac308813f0dabd5765f1ad1641537c595ba1d4/python/query_balance_and_order_fills.py
         */
        AscendexOrderAndBalanceDetailDto detailDto = serviceRaw.getAscendexOrderAndBalanceDetail("2022-07-18",AccountCategory.cash);
        Assert.assertNotNull(detailDto);
    }

}
