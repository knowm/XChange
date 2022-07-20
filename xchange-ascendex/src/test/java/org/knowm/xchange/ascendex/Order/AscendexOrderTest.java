package org.knowm.xchange.ascendex.Order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.ascendex.AscendexExchange;
import org.knowm.xchange.ascendex.dto.enums.*;
import org.knowm.xchange.ascendex.dto.trade.*;
import org.knowm.xchange.ascendex.service.AscendexAccountServiceRaw;
import org.knowm.xchange.ascendex.service.AscendexTradeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.Order
 * Description:
 *
 * @date:2022/7/20 12:27
 * @author:wodepig
 */
public class AscendexOrderTest {
    private AscendexTradeService serviceRaw;
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
        serviceRaw = (AscendexTradeService)exchange.getTradeService();

    }
    // @Test
    public void testPlaceOrder()throws IOException{
     /*   AscendexPlaceOrderRequestPayload paylod = new AscendexPlaceOrderRequestPayload(
                "TRX/USDT",new Date().getTime(),"100", AscendexOrderType.limit, AscendexSide.buy,
                null,"0.07",null,false, AscendexTimeInForce.GTC, AscendexRespInst.ACK
        );*/
         AscendexPlaceOrderRequestPayload paylod = new AscendexPlaceOrderRequestPayload("TRX/USDT",
                 "100","0.06",AscendexOrderType.limit, AscendexSide.sell);
        AscendexOrderResponse ascendexOrderResponse = serviceRaw.placeAscendexOrder(paylod);
        Assert.assertNotNull(ascendexOrderResponse);
    }
    // @Test
    public void testCancelOrder()throws IOException{
        AscendexCancelOrderRequestPayload payload = new AscendexCancelOrderRequestPayload(
                "a1821a8c3afbU2171064316LjoPeBWda",
                "TRX/USDT",
                new Date().getTime(),
                AccountCategory.cash
        );
        AscendexOrderResponse ascendexOrderResponse = serviceRaw.cancelAscendexOrder(payload);
        Assert.assertNotNull(ascendexOrderResponse);

    }

    // @Test
    public void testCancelOrderAll()throws IOException{
        AscendexOrderResponse ascendexOrderResponse = serviceRaw.cancelAllAscendexOrdersBySymbol(AccountCategory.cash,null);
        Assert.assertNotNull(ascendexOrderResponse);

    }

    // @Test
    public void testGetOrderById()throws  IOException   {
        AscendexOpenOrdersResponse ascendexOrderById = serviceRaw.getAscendexOrderById(AccountCategory.cash,"r1821b163db0U2171064316WI9qpS5zs");
        Assert.assertNotNull(ascendexOrderById);
    }
    // @Test
    public void testOpenOrders()throws  IOException   {
        List<AscendexOpenOrdersResponse> ascendexOpenOrders = serviceRaw.getAscendexOpenOrders(AccountCategory.cash,"TRX/USDT");
        Assert.assertNotNull(ascendexOpenOrders);
    }

    // @Test
    public void testOrdersHistory()throws  IOException   {
        List<AscendexOpenOrdersResponse> ascendexUserTrades = serviceRaw.getAscendexUserTrades(AccountCategory.cash,"TRX/USDT",1,true);
        Assert.assertNotNull(ascendexUserTrades);
    }

    // @Test
    public void testOrdersHistoryV2()throws  IOException   {
        List<AscendexHistoryOrderResponse> cash = serviceRaw.getAscendexOrdersHistoryV2(AccountCategory.cash);
        Assert.assertNotNull(cash);
        List<AscendexHistoryOrderResponse> cashTrx = serviceRaw.getAscendexOrdersHistoryV2(AccountCategory.cash,
                "TRx/usdt",
                1655722674000L,
                1658314674000L,
                1L,
                10
                );
        Assert.assertNotNull(cashTrx);
    };

}
