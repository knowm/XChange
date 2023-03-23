package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.ws.rs.core.Response.Status;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

public class BybitTradeServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String orderDetails = "{\n" +
        "   \"ret_code\":0,\n" +
        "   \"ret_msg\":\"\",\n" +
        "   \"ext_code\":null,\n" +
        "   \"ext_info\":null,\n" +
        "   \"result\":{\n" +
        "      \"accountId\":\"123456789\",\n" +
        "      \"exchangeId\":\"301\",\n" +
        "      \"symbol\":\"COINUSDT\",\n" +
        "      \"symbolName\":\"COINUSDT\",\n" +
        "      \"orderLinkId\":\"1234567891011121\",\n" +
        "      \"orderId\":\"1234567891011121314\",\n" +
        "      \"price\":\"0\",\n" +
        "      \"origQty\":\"352\",\n" +
        "      \"executedQty\":\"352\",\n" +
        "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
        "      \"avgPrice\":\"0.001619\",\n" +
        "      \"status\":\"FILLED\",\n" +
        "      \"timeInForce\":\"GTC\",\n" +
        "      \"type\":\"MARKET\",\n" +
        "      \"side\":\"SELL\",\n" +
        "      \"stopPrice\":\"0.0\",\n" +
        "      \"icebergQty\":\"0.0\",\n" +
        "      \"time\":\"1655997749601\",\n" +
        "      \"updateTime\":\"1655997749662\",\n" +
        "      \"isWorking\":true,\n" +
        "      \"locked\":\"0\"\n" +
        "   }\n" +
        "}";

    stubFor(
        get(urlPathEqualTo("/spot/v1/order"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderDetails)
            )
    );
    BybitResult<BybitOrderDetails> order = bybitAccountServiceRaw.getBybitOrder("1234567891011121314");

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(orderDetails);

    BybitOrderDetails orderResult = order.getResult();
    JsonNode responseObjectResult = responseObject.get("result");

    assertThat(responseObjectResult.get("accountId").textValue()).isEqualTo(orderResult.getAccountId());
    assertThat(responseObjectResult.get("exchangeId").textValue()).isEqualTo(orderResult.getExchangeId());
    assertThat(responseObjectResult.get("symbol").textValue()).isEqualTo(orderResult.getSymbol());
    assertThat(responseObjectResult.get("symbolName").textValue()).isEqualTo(orderResult.getSymbolName());
    assertThat(responseObjectResult.get("orderLinkId").textValue()).isEqualTo(orderResult.getOrderLinkId());
    assertThat(responseObjectResult.get("orderId").textValue()).isEqualTo(orderResult.getOrderId());
    assertThat(responseObjectResult.get("price").textValue()).isEqualTo(orderResult.getPrice());
    assertThat(responseObjectResult.get("origQty").textValue()).isEqualTo(orderResult.getOrigQty());
    assertThat(responseObjectResult.get("executedQty").textValue()).isEqualTo(orderResult.getExecutedQty());
    assertThat(responseObjectResult.get("cummulativeQuoteQty").textValue()).isEqualTo(
        orderResult.getCummulativeQuoteQty());
    assertThat(responseObjectResult.get("avgPrice").textValue()).isEqualTo(orderResult.getAvgPrice());
    assertThat(responseObjectResult.get("status").textValue()).isEqualTo(orderResult.getStatus());
    assertThat(responseObjectResult.get("timeInForce").textValue()).isEqualTo(orderResult.getTimeInForce());
    assertThat(responseObjectResult.get("type").textValue()).isEqualTo(orderResult.getType());
    assertThat(responseObjectResult.get("side").textValue()).isEqualTo(orderResult.getSide());
    assertThat(responseObjectResult.get("stopPrice").textValue()).isEqualTo(orderResult.getStopPrice());
    assertThat(responseObjectResult.get("icebergQty").textValue()).isEqualTo(orderResult.getIcebergQty());
    assertThat(responseObjectResult.get("time").textValue()).isEqualTo(orderResult.getTime());
    assertThat(responseObjectResult.get("updateTime").textValue()).isEqualTo(orderResult.getUpdateTime());
    assertThat(responseObjectResult.get("isWorking").booleanValue()).isEqualTo(orderResult.isWorking());
    assertThat(responseObjectResult.get("locked").textValue()).isEqualTo(orderResult.getLocked());
  }


  @Test
  public void testPlaceBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String orderPlacementResponse = "{\n" +
        "   \"ret_code\":0,\n" +
        "   \"ret_msg\":\"\",\n" +
        "   \"ext_code\":null,\n" +
        "   \"ext_info\":null,\n" +
        "   \"result\":{\n" +
        "      \"accountId\":\"28649557\",\n" +
        "      \"exchangeId\":\"301\",\n" +
        "      \"symbol\":\"COINUSDT\",\n" +
        "      \"symbolName\":\"COINUSDT\",\n" +
        "      \"orderLinkId\":\"1655997749596563\",\n" +
        "      \"orderId\":\"1184989442799045889\",\n" +
        "      \"price\":\"0\",\n" +
        "      \"origQty\":\"352\",\n" +
        "      \"executedQty\":\"352\",\n" +
        "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
        "      \"avgPrice\":\"0.001619\",\n" +
        "      \"status\":\"FILLED\",\n" +
        "      \"timeInForce\":\"GTC\",\n" +
        "      \"type\":\"MARKET\",\n" +
        "      \"side\":\"SELL\",\n" +
        "      \"stopPrice\":\"0.0\",\n" +
        "      \"icebergQty\":\"0.0\",\n" +
        "      \"time\":\"1655997749601\",\n" +
        "      \"updateTime\":\"1655997749662\",\n" +
        "      \"isWorking\":true,\n" +
        "      \"locked\":\"0\"\n" +
        "   }\n" +
        "}";

    stubFor(
        post(urlPathEqualTo("/spot/v1/order"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderPlacementResponse)
            )
    );

    BybitResult<BybitOrderRequest> order = bybitAccountServiceRaw.placeOrder(
        "COINUSDT",
        300,
        "SELL",
        "MARKET"
    );

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(orderPlacementResponse);

    BybitOrderRequest orderRequestResult = order.getResult();
    JsonNode responseObjectResult = responseObject.get("result");

    assertThat(responseObjectResult.get("accountId").textValue()).isEqualTo(orderRequestResult.getAccountId());
    assertThat(responseObjectResult.get("symbol").textValue()).isEqualTo(orderRequestResult.getSymbol());
    assertThat(responseObjectResult.get("symbolName").textValue()).isEqualTo(orderRequestResult.getSymbolName());
    assertThat(responseObjectResult.get("orderLinkId").textValue()).isEqualTo(orderRequestResult.getOrderLinkId());
    assertThat(responseObjectResult.get("orderId").textValue()).isEqualTo(orderRequestResult.getOrderId());
    assertThat(responseObjectResult.get("price").textValue()).isEqualTo(orderRequestResult.getPrice());
    assertThat(responseObjectResult.get("origQty").textValue()).isEqualTo(orderRequestResult.getOrigQty());
    assertThat(responseObjectResult.get("executedQty").textValue()).isEqualTo(orderRequestResult.getExecutedQty());
    assertThat(responseObjectResult.get("status").textValue()).isEqualTo(orderRequestResult.getStatus());
    assertThat(responseObjectResult.get("timeInForce").textValue()).isEqualTo(orderRequestResult.getTimeInForce());
    assertThat(responseObjectResult.get("type").textValue()).isEqualTo(orderRequestResult.getType());
    assertThat(responseObjectResult.get("side").textValue()).isEqualTo(orderRequestResult.getSide());

    System.out.println(order);
  }

}
