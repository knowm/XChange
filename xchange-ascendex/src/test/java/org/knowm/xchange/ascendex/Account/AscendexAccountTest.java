package org.knowm.xchange.ascendex.Account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.account.*;
import org.knowm.xchange.ascendex.service.AscendexAccountServiceRaw;

import java.io.IOException;
import java.io.InputStream;

/**
 * Package:org.knowm.xchange.ascendex.Account
 * Description:
 *
 * @date:2022/7/14 21:11
 * @author:wodepig
 */
public class AscendexAccountTest {

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
    public void testAscendexAccount()throws IOException {
        AscendexAccountInfoDto info = serviceRaw.getAscendexAccountInfo();
        Assert.assertTrue(info!=null&&info.getEmail()!=null);
    }

    @Test
    public void testVIPFeeSchedule()throws IOException {
        AscendexVIPFeeScheduleDto vipFeeSchedule = serviceRaw.getAscendexVIPFeeSchedule();
        Assert.assertTrue(vipFeeSchedule!=null&&vipFeeSchedule.getUserUID()!=null);

    }

    @Test
    public void testSymbolFee()throws IOException {
        AscendexSymbolFeeDto symbolFee = serviceRaw.getAscendexSymbolFee();
        Assert.assertTrue(symbolFee!=null&&symbolFee.getProductFee().size()>10);
    }
    @Test
    public void testRiskLimitInfo()throws IOException {
        AscendexRiskLimitInfoDto riskLimitInfo = serviceRaw.getAscendexRiskLimitInfo();
        Assert.assertTrue(riskLimitInfo!=null&&riskLimitInfo.getWebSocket()!=null);
    }
    @Test
    public void testExchangeLatencyInfoDto()throws IOException {
        long sendTime = System.currentTimeMillis();
        AscendexExchangeLatencyInfoDto exchangeLatencyInfoDto = serviceRaw.getAscendexExchangeLatencyInfo(sendTime);
        Assert.assertNotNull(exchangeLatencyInfoDto);
    }

}
