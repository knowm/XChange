package org.knowm.xchange.mexc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MEXCTradeServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetMEXCTradeServiceRawOrder() throws IOException {
    Exchange mexcExchange = createExchange();
    MEXCTradeServiceRaw mexcAccountServiceRaw = new MEXCTradeServiceRaw(mexcExchange);

    String orderDetails = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"id\": \"504feca6ba6349e39c82262caf0be3f4\",\n" +
            "            \"symbol\": \"MX_ETH\",\n" +
            "            \"price\": \"0.000901\",\n" +
            "            \"quantity\": \"300000\",\n" +
            "            \"state\": \"NEW\",\n" +
            "            \"type\": \"BID\",\n" +
            "            \"deal_quantity\": \"0\",\n" +
            "            \"deal_amount\": \"0\",\n" +
            "            \"create_time\": 1573117266000\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"72872b6ae721480ca4fe0f265d29dfee\",\n" +
            "            \"symbol\": \"MX_ETH\",\n" +
            "            \"price\": \"0.000907\",\n" +
            "            \"quantity\": \"300000\",\n" +
            "            \"state\": \"NEW\",\n" +
            "            \"type\": \"ASK\",\n" +
            "            \"deal_quantity\": \"0\",\n" +
            "            \"deal_amount\": \"0\",\n" +
            "            \"create_time\": 1573117267000\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    stubFor(
            get(urlPathEqualTo("/open/api/v2/order/query"))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(orderDetails)
                    )
    );
    MEXCResult<List<MEXCOrder>> order = mexcAccountServiceRaw.getOrders(Arrays.asList("1234567891011121314"));

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(orderDetails);

    List<MEXCOrder> orderResults = order.getData();
    JsonNode responseObjectResult = responseObject.get("data");
    assertThat(orderResults.size()).isEqualTo(2);

    MEXCOrder orderResult = orderResults.get(0);

    assertThat(responseObjectResult.get(0).get("id").textValue()).isEqualTo(orderResult.getId());
    assertThat(responseObjectResult.get(0).get("symbol").textValue()).isEqualTo(orderResult.getSymbol());
    assertThat(responseObjectResult.get(0).get("price").textValue()).isEqualTo(orderResult.getPrice());
    assertThat(responseObjectResult.get(0).get("quantity").textValue()).isEqualTo(orderResult.getQuantity());
    assertThat(responseObjectResult.get(0).get("state").textValue()).isEqualTo(orderResult.getState());
    assertThat(responseObjectResult.get(0).get("type").textValue()).isEqualTo(orderResult.getType());
    assertThat(responseObjectResult.get(0).get("deal_quantity").textValue()).isEqualTo(orderResult.getDealQuantity());
    assertThat(responseObjectResult.get(0).get("deal_amount").textValue()).isEqualTo(orderResult.getDealAmount());
    assertThat(responseObjectResult.get(0).get("create_time").longValue()).isEqualTo(orderResult.getCreateTime());


  }


//  @Test
//  public void testPlaceMEXCTradeServiceRawOrder() throws IOException {
//    Exchange mexcExchange = createExchange();
//    MEXCTradeServiceRawTradeServiceRaw mexcAccountServiceRaw = new MEXCTradeServiceRawTradeServiceRaw(mexcExchange);
//
//    String orderPlacementResponse = "{\n" +
//            "   \"ret_code\":0,\n" +
//            "   \"ret_msg\":\"\",\n" +
//            "   \"ext_code\":null,\n" +
//            "   \"ext_info\":null,\n" +
//            "   \"result\":{\n" +
//            "      \"accountId\":\"28649557\",\n" +
//            "      \"exchangeId\":\"301\",\n" +
//            "      \"symbol\":\"COINUSDT\",\n" +
//            "      \"symbolName\":\"COINUSDT\",\n" +
//            "      \"orderLinkId\":\"1655997749596563\",\n" +
//            "      \"orderId\":\"1184989442799045889\",\n" +
//            "      \"price\":\"0\",\n" +
//            "      \"origQty\":\"352\",\n" +
//            "      \"executedQty\":\"352\",\n" +
//            "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
//            "      \"avgPrice\":\"0.001619\",\n" +
//            "      \"status\":\"FILLED\",\n" +
//            "      \"timeInForce\":\"GTC\",\n" +
//            "      \"type\":\"MARKET\",\n" +
//            "      \"side\":\"SELL\",\n" +
//            "      \"stopPrice\":\"0.0\",\n" +
//            "      \"icebergQty\":\"0.0\",\n" +
//            "      \"time\":\"1655997749601\",\n" +
//            "      \"updateTime\":\"1655997749662\",\n" +
//            "      \"isWorking\":true,\n" +
//            "      \"locked\":\"0\"\n" +
//            "   }\n" +
//            "}";
//
//    stubFor(
//            post(urlPathEqualTo("/spot/v1/order"))
//                    .willReturn(
//                            aResponse()
//                                    .withStatus(200)
//                                    .withHeader("Content-Type", "application/json")
//                                    .withBody(orderPlacementResponse)
//                    )
//    );
//
//    MEXCTradeServiceRawResult<MEXCTradeServiceRawOrderRequest> order = mexcAccountServiceRaw.placeOrder(
//            "COINUSDT",
//            300,
//            "SELL",
//            "MARKET"
//    );
//
//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode responseObject = mapper.readTree(orderPlacementResponse);
//
//    MEXCTradeServiceRawOrderRequest orderRequestResult = order.getResult();
//    JsonNode responseObjectResult = responseObject.get("result");
//
//    assertThat(responseObjectResult.get("accountId").textValue()).isEqualTo(orderRequestResult.getAccountId());
//    assertThat(responseObjectResult.get("symbol").textValue()).isEqualTo(orderRequestResult.getSymbol());
//    assertThat(responseObjectResult.get("symbolName").textValue()).isEqualTo(orderRequestResult.getSymbolName());
//    assertThat(responseObjectResult.get("orderLinkId").textValue()).isEqualTo(orderRequestResult.getOrderLinkId());
//    assertThat(responseObjectResult.get("orderId").textValue()).isEqualTo(orderRequestResult.getOrderId());
//    assertThat(responseObjectResult.get("price").textValue()).isEqualTo(orderRequestResult.getPrice());
//    assertThat(responseObjectResult.get("origQty").textValue()).isEqualTo(orderRequestResult.getOrigQty());
//    assertThat(responseObjectResult.get("executedQty").textValue()).isEqualTo(orderRequestResult.getExecutedQty());
//    assertThat(responseObjectResult.get("status").textValue()).isEqualTo(orderRequestResult.getStatus());
//    assertThat(responseObjectResult.get("timeInForce").textValue()).isEqualTo(orderRequestResult.getTimeInForce());
//    assertThat(responseObjectResult.get("type").textValue()).isEqualTo(orderRequestResult.getType());
//    assertThat(responseObjectResult.get("side").textValue()).isEqualTo(orderRequestResult.getSide());
//
//    System.out.println(order);
//  }

}
