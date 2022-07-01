package org.knowm.xchange.mexc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;

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

      orderResult = orderResults.get(1);

      assertThat(responseObjectResult.get(1).get("id").textValue()).isEqualTo(orderResult.getId());
      assertThat(responseObjectResult.get(1).get("symbol").textValue()).isEqualTo(orderResult.getSymbol());
      assertThat(responseObjectResult.get(1).get("price").textValue()).isEqualTo(orderResult.getPrice());
      assertThat(responseObjectResult.get(1).get("quantity").textValue()).isEqualTo(orderResult.getQuantity());
      assertThat(responseObjectResult.get(1).get("state").textValue()).isEqualTo(orderResult.getState());
      assertThat(responseObjectResult.get(1).get("type").textValue()).isEqualTo(orderResult.getType());
      assertThat(responseObjectResult.get(1).get("deal_quantity").textValue()).isEqualTo(orderResult.getDealQuantity());
      assertThat(responseObjectResult.get(1).get("deal_amount").textValue()).isEqualTo(orderResult.getDealAmount());
      assertThat(responseObjectResult.get(1).get("create_time").longValue()).isEqualTo(orderResult.getCreateTime());

  }


  @Test
  public void testPlaceMEXCTradeServiceRawOrder() throws IOException {
    Exchange mexcExchange = createExchange();
    MEXCTradeServiceRaw mexcTradeServiceRaw = new MEXCTradeServiceRaw(mexcExchange);

    String orderPlacementResponse = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": \"c8663a12a2fc457fbfdd55307b463495\"\n" +
            "}";

    stubFor(
            post(urlPathEqualTo("/open/api/v2/order/place"))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(orderPlacementResponse)
                    )
    );

    MEXCResult<String> orderPlacementResult = mexcTradeServiceRaw.placeOrder(new MEXCOrderRequestPayload(
            "COINUSDT",
            "1.0",
            "1.0",
            "BID",
            "LIMIT_ORDER",
            null
    ));


    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(orderPlacementResponse);

    String orderId = orderPlacementResult.getData();
    JsonNode responseObjectResult = responseObject.get("data");

    assertThat(orderId).isEqualTo("c8663a12a2fc457fbfdd55307b463495");
    assertThat(responseObjectResult.textValue()).isEqualTo("c8663a12a2fc457fbfdd55307b463495");

  }

}
