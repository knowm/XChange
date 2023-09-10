package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.math.BigDecimal;
import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;

public class BybitTradeServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String expectedOrderDetails =
        "{\n"
            + "    \"retCode\": 0,\n"
            + "    \"retMsg\": \"OK\",\n"
            + "    \"result\": {\n"
            + "        \"list\": [\n"
            + "            {\n"
            + "                \"orderId\": \"fd4300ae-7847-404e-b947-b46980a4d140\",\n"
            + "                \"orderLinkId\": \"test-000005\",\n"
            + "                \"blockTradeId\": \"\",\n"
            + "                \"symbol\": \"ETHUSDT\",\n"
            + "                \"price\": \"1600.00\",\n"
            + "                \"qty\": \"0.10\",\n"
            + "                \"side\": \"Buy\",\n"
            + "                \"isLeverage\": \"\",\n"
            + "                \"positionIdx\": 1,\n"
            + "                \"orderStatus\": \"New\",\n"
            + "                \"cancelType\": \"UNKNOWN\",\n"
            + "                \"rejectReason\": \"EC_NoError\",\n"
            + "                \"avgPrice\": \"0\",\n"
            + "                \"leavesQty\": \"0.10\",\n"
            + "                \"leavesValue\": \"160\",\n"
            + "                \"cumExecQty\": \"0.00\",\n"
            + "                \"cumExecValue\": \"0\",\n"
            + "                \"cumExecFee\": \"0\",\n"
            + "                \"timeInForce\": \"GTC\",\n"
            + "                \"orderType\": \"Limit\",\n"
            + "                \"stopOrderType\": \"UNKNOWN\",\n"
            + "                \"orderIv\": \"\",\n"
            + "                \"triggerPrice\": \"0.00\",\n"
            + "                \"takeProfit\": \"2500.00\",\n"
            + "                \"stopLoss\": \"1500.00\",\n"
            + "                \"tpTriggerBy\": \"LastPrice\",\n"
            + "                \"slTriggerBy\": \"LastPrice\",\n"
            + "                \"triggerDirection\": 0,\n"
            + "                \"triggerBy\": \"UNKNOWN\",\n"
            + "                \"lastPriceOnCreated\": \"\",\n"
            + "                \"reduceOnly\": false,\n"
            + "                \"closeOnTrigger\": false,\n"
            + "                \"smpType\": \"None\",\n"
            + "                \"smpGroup\": 0,\n"
            + "                \"smpOrderId\": \"\",\n"
            + "                \"tpslMode\": \"Full\",\n"
            + "                \"tpLimitPrice\": \"\",\n"
            + "                \"slLimitPrice\": \"\",\n"
            + "                \"placeType\": \"\",\n"
            + "                \"createdTime\": \"1684738540559\",\n"
            + "                \"updatedTime\": \"1684738540561\"\n"
            + "            }\n"
            + "        ],\n"
            + "        \"nextPageCursor\": \"page_args%3Dfd4300ae-7847-404e-b947-b46980a4d140%26symbol%3D6%26\",\n"
            + "        \"category\": \"linear\"\n"
            + "    },\n"
            + "    \"retExtInfo\": {},\n"
            + "    \"time\": 1684765770483\n"
            + "}";

    stubFor(
        get(urlPathEqualTo("/v5/order/realtime"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(expectedOrderDetails)));
    BybitResult<BybitOrderDetails> actualOrderDetails =
        bybitAccountServiceRaw.getBybitOrder(
            BybitCategory.SPOT, "fd4300ae-7847-404e-b947-b46980a4d140");

    assertThat(actualOrderDetails.getResult().getList()).hasSize(1);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(expectedOrderDetails);

    BybitOrderDetail actualOrderDetail = actualOrderDetails.getResult().getList().get(0);
    JsonNode responseObjectResult = responseObject.get("result");
    JsonNode listNode = responseObjectResult.get("list");
    JsonNode expectedOrderDetail = listNode.get(0);

    assertThat(actualOrderDetail.getSymbol())
        .isEqualTo(expectedOrderDetail.get("symbol").textValue())
        ;
    assertThat(actualOrderDetail.getPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("price").asDouble());
    assertThat(actualOrderDetail.getQty().doubleValue())
        .isEqualTo(expectedOrderDetail.get("qty").asDouble());
    assertThat(actualOrderDetail.getSide().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("side").textValue())
        ;
    assertThat(actualOrderDetail.isLeverage())
        .isEqualTo(expectedOrderDetail.get("isLeverage").booleanValue());
    assertThat(actualOrderDetail.getPositionIdx())
        .isEqualTo(expectedOrderDetail.get("positionIdx").intValue());
    assertThat(actualOrderDetail.getOrderStatus().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("orderStatus").textValue());
    assertThat(actualOrderDetail.getCancelType())
        .isEqualTo(expectedOrderDetail.get("cancelType").textValue())
        ;
    assertThat(actualOrderDetail.getRejectReason())
        .isEqualTo(expectedOrderDetail.get("rejectReason").textValue())
        ;
    assertThat(actualOrderDetail.getAvgPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("avgPrice").asDouble());
    assertThat(actualOrderDetail.getLeavesQty().doubleValue())
        .isEqualTo(expectedOrderDetail.get("leavesQty").asDouble());
    assertThat(actualOrderDetail.getLeavesValue().doubleValue())
        .isEqualTo(expectedOrderDetail.get("leavesValue").asDouble());
    assertThat(actualOrderDetail.getCumExecQty().doubleValue())
        .isEqualTo(expectedOrderDetail.get("cumExecQty").asDouble());
    assertThat(actualOrderDetail.getCumExecValue().doubleValue())
        .isEqualTo(expectedOrderDetail.get("cumExecValue").asDouble());
    assertThat(actualOrderDetail.getCumExecFee().doubleValue())
        .isEqualTo(expectedOrderDetail.get("cumExecFee").asDouble());
    assertThat(actualOrderDetail.getTimeInForce())
        .isEqualTo(expectedOrderDetail.get("timeInForce").textValue())
        ;
    assertThat(actualOrderDetail.getOrderType().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("orderType").textValue())
        ;
    assertThat(actualOrderDetail.getStopOrderType())
        .isEqualTo(expectedOrderDetail.get("stopOrderType").textValue())
        ;
    assertThat(actualOrderDetail.getOrderIv())
        .isEqualTo(expectedOrderDetail.get("orderIv").textValue());
    assertThat(actualOrderDetail.getTriggerPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("triggerPrice").asDouble());
    assertThat(actualOrderDetail.getTakeProfit().doubleValue())
        .isEqualTo(expectedOrderDetail.get("takeProfit").asDouble());
    assertThat(actualOrderDetail.getStopLoss().doubleValue())
        .isEqualTo(expectedOrderDetail.get("stopLoss").asDouble());
    assertThat(actualOrderDetail.getTpTriggerBy())
        .isEqualTo(expectedOrderDetail.get("tpTriggerBy").textValue());
    assertThat(actualOrderDetail.getSlTriggerBy())
        .isEqualTo(expectedOrderDetail.get("slTriggerBy").textValue());
    assertThat(actualOrderDetail.getTriggerDirection())
        .isEqualTo(expectedOrderDetail.get("triggerDirection").intValue());
    assertThat(actualOrderDetail.getTriggerBy())
        .isEqualTo(expectedOrderDetail.get("triggerBy").textValue())
        ;
    assertThat(actualOrderDetail.getLastPriceOnCreated())
        .isEqualTo(expectedOrderDetail.get("lastPriceOnCreated").textValue())
        ;
    assertThat(actualOrderDetail.isReduceOnly())
        .isEqualTo(expectedOrderDetail.get("reduceOnly").booleanValue());
    assertThat(actualOrderDetail.isCloseOnTrigger())
        .isEqualTo(expectedOrderDetail.get("closeOnTrigger").booleanValue());
    assertThat(actualOrderDetail.getSmpType())
        .isEqualTo(expectedOrderDetail.get("smpType").textValue());
    assertThat(actualOrderDetail.getSmpGroup())
        .isEqualTo(expectedOrderDetail.get("smpGroup").intValue());
    assertThat(actualOrderDetail.getSmpOrderId())
        .isEqualTo(expectedOrderDetail.get("smpOrderId").textValue())
        ;
    assertThat(actualOrderDetail.getTpslMode())
        .isEqualTo(expectedOrderDetail.get("tpslMode").textValue());
    assertThat(actualOrderDetail.getTpLimitPrice())
        .isEqualTo(expectedOrderDetail.get("tpLimitPrice").textValue());
    assertThat(actualOrderDetail.getSlLimitPrice())
        .isEqualTo(expectedOrderDetail.get("slLimitPrice").textValue());
    assertThat(actualOrderDetail.getPlaceType())
        .isEqualTo(expectedOrderDetail.get("placeType").textValue());
    assertThat(actualOrderDetail.getCreatedTime().getTime())
        .isEqualTo(expectedOrderDetail.get("createdTime").asLong());
    assertThat(actualOrderDetail.getUpdatedTime().getTime())
        .isEqualTo(expectedOrderDetail.get("updatedTime").asLong());
  }

  @Test
  public void testPlaceBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String orderPlacementResponse =
        "{\n"
            + "    \"retCode\": 0,\n"
            + "    \"retMsg\": \"OK\",\n"
            + "    \"result\": {\n"
            + "        \"orderId\": \"1321003749386327552\",\n"
            + "        \"orderLinkId\": \"spot-test-postonly\"\n"
            + "    },\n"
            + "    \"retExtInfo\": {},\n"
            + "    \"time\": 1672211918471\n"
            + "}";

    stubFor(
        post(urlPathEqualTo("/v5/order/create"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderPlacementResponse)));

    BybitResult<BybitOrderResponse> order =
        bybitAccountServiceRaw.placeOrder(
            BybitCategory.SPOT,
            "BTCUSDT",
            BybitSide.BUY,
            BybitOrderType.LIMIT,
            BigDecimal.valueOf(0.1));

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(orderPlacementResponse);

    BybitOrderResponse orderRequestResult = order.getResult();
    JsonNode responseObjectResult = responseObject.get("result");

    assertThat(responseObjectResult.get("orderLinkId").textValue())
        .isEqualTo(orderRequestResult.getOrderLinkId());
    assertThat(responseObjectResult.get("orderId").textValue())

        .isEqualTo(orderRequestResult.getOrderId());

    System.out.println(order);
  }
}
