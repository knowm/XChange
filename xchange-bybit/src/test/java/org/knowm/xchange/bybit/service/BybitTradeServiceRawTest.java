package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.linear.BybitLinearOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.spot.BybitSpotOrderDetail;

public class BybitTradeServiceRawTest extends BaseWiremockTest {

  @Test
  public void testGetBybitLinearDetailOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String responseFilePath = "/getOrderDetailsLinear.json5";
    initGetStub("/v5/order/realtime", responseFilePath);
    String expectedOrderDetails =
        IOUtils.resourceToString(responseFilePath, StandardCharsets.UTF_8);

    BybitResult<BybitOrderDetails<BybitOrderDetail>> actualOrderDetails =
        bybitAccountServiceRaw.getBybitOrder(
            BybitCategory.LINEAR, "fd4300ae-7847-404e-b947-b46980a4d140");

    assertThat(actualOrderDetails.getResult().getList()).hasSize(1);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(expectedOrderDetails);

    BybitLinearOrderDetail actualOrderDetail =
        (BybitLinearOrderDetail) actualOrderDetails.getResult().getList().get(0);
    JsonNode responseObjectResult = responseObject.get("result");
    JsonNode listNode = responseObjectResult.get("list");
    JsonNode expectedOrderDetail = listNode.get(0);

    assertThat(actualOrderDetail.getSymbol())
        .isEqualTo(expectedOrderDetail.get("symbol").textValue());
    assertThat(actualOrderDetail.getPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("price").asDouble());
    assertThat(actualOrderDetail.getQty().doubleValue())
        .isEqualTo(expectedOrderDetail.get("qty").asDouble());
    assertThat(actualOrderDetail.getSide().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("side").textValue());
    assertThat(actualOrderDetail.getIsLeverage())
        .isEqualTo(expectedOrderDetail.get("isLeverage").textValue());
    assertThat(actualOrderDetail.getPositionIdx())
        .isEqualTo(expectedOrderDetail.get("positionIdx").intValue());
    assertThat(actualOrderDetail.getOrderStatus().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("orderStatus").textValue());
    assertThat(actualOrderDetail.getCancelType())
        .isEqualTo(expectedOrderDetail.get("cancelType").textValue());
    assertThat(actualOrderDetail.getRejectReason())
        .isEqualTo(expectedOrderDetail.get("rejectReason").textValue());
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
        .isEqualTo(expectedOrderDetail.get("timeInForce").textValue());
    assertThat(actualOrderDetail.getOrderType().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("orderType").textValue());
    assertThat(actualOrderDetail.getStopOrderType())
        .isEqualTo(expectedOrderDetail.get("stopOrderType").textValue());
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
        .isEqualTo(expectedOrderDetail.get("triggerBy").textValue());
    assertThat(actualOrderDetail.getLastPriceOnCreated())
        .isEqualTo(expectedOrderDetail.get("lastPriceOnCreated").textValue());
    assertThat(actualOrderDetail.isReduceOnly())
        .isEqualTo(expectedOrderDetail.get("reduceOnly").booleanValue());
    assertThat(actualOrderDetail.isCloseOnTrigger())
        .isEqualTo(expectedOrderDetail.get("closeOnTrigger").booleanValue());
    assertThat(actualOrderDetail.getSmpType())
        .isEqualTo(expectedOrderDetail.get("smpType").textValue());
    assertThat(actualOrderDetail.getSmpGroup())
        .isEqualTo(expectedOrderDetail.get("smpGroup").intValue());
    assertThat(actualOrderDetail.getSmpOrderId())
        .isEqualTo(expectedOrderDetail.get("smpOrderId").textValue());
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
  public void testGetBybitSpotDetailOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);

    String responseFilePath = "/getOrderDetailsSpot.json5";
    initGetStub("/v5/order/realtime", responseFilePath);
    String expectedOrderDetails =
        IOUtils.resourceToString(responseFilePath, StandardCharsets.UTF_8);

    BybitResult<BybitOrderDetails<BybitOrderDetail>> actualOrderDetails =
        bybitAccountServiceRaw.getBybitOrder(
            BybitCategory.SPOT, "fd4300ae-7847-404e-b947-b46980a4d140");

    assertThat(actualOrderDetails.getResult().getList()).hasSize(1);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode responseObject = mapper.readTree(expectedOrderDetails);

    BybitSpotOrderDetail actualOrderDetail =
        (BybitSpotOrderDetail) actualOrderDetails.getResult().getList().get(0);
    JsonNode responseObjectResult = responseObject.get("result");
    JsonNode listNode = responseObjectResult.get("list");
    JsonNode expectedOrderDetail = listNode.get(0);

    assertThat(actualOrderDetail.getSymbol())
        .isEqualTo(expectedOrderDetail.get("symbol").textValue());
    assertThat(actualOrderDetail.getPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("price").asDouble());
    assertThat(actualOrderDetail.getQty().doubleValue())
        .isEqualTo(expectedOrderDetail.get("qty").asDouble());
    assertThat(actualOrderDetail.getSide().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("side").textValue());
    assertThat(actualOrderDetail.getIsLeverage())
        .isEqualTo(expectedOrderDetail.get("isLeverage").textValue());
    assertThat(actualOrderDetail.getPositionIdx())
        .isEqualTo(expectedOrderDetail.get("positionIdx").intValue());
    assertThat(actualOrderDetail.getOrderStatus())
        .isEqualTo(BybitOrderStatus.PARTIALLY_FILLED_CANCELED);
    assertThat(actualOrderDetail.getCancelType())
        .isEqualTo(expectedOrderDetail.get("cancelType").textValue());
    assertThat(actualOrderDetail.getRejectReason())
        .isEqualTo(expectedOrderDetail.get("rejectReason").textValue());
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
        .isEqualTo(expectedOrderDetail.get("timeInForce").textValue());
    assertThat(actualOrderDetail.getOrderType().name())
        .isEqualToIgnoringCase(expectedOrderDetail.get("orderType").textValue());
    assertThat(actualOrderDetail.getStopOrderType())
        .isEqualTo(expectedOrderDetail.get("stopOrderType").textValue());
    assertThat(actualOrderDetail.getOrderIv())
        .isEqualTo(expectedOrderDetail.get("orderIv").textValue());
    assertThat(actualOrderDetail.getTriggerPrice().doubleValue())
        .isEqualTo(expectedOrderDetail.get("triggerPrice").asDouble());
    assertThat(actualOrderDetail.getTpTriggerBy())
        .isEqualTo(expectedOrderDetail.get("tpTriggerBy").textValue());
    assertThat(actualOrderDetail.getSlTriggerBy())
        .isEqualTo(expectedOrderDetail.get("slTriggerBy").textValue());
    assertThat(actualOrderDetail.getTriggerDirection())
        .isEqualTo(expectedOrderDetail.get("triggerDirection").intValue());
    assertThat(actualOrderDetail.getTriggerBy())
        .isEqualTo(expectedOrderDetail.get("triggerBy").textValue());
    assertThat(actualOrderDetail.getLastPriceOnCreated()).isNull();
    assertThat(actualOrderDetail.isReduceOnly())
        .isEqualTo(expectedOrderDetail.get("reduceOnly").booleanValue());
    assertThat(actualOrderDetail.isCloseOnTrigger())
        .isEqualTo(expectedOrderDetail.get("closeOnTrigger").booleanValue());
    assertThat(actualOrderDetail.getSmpType())
        .isEqualTo(expectedOrderDetail.get("smpType").textValue());
    assertThat(actualOrderDetail.getSmpGroup())
        .isEqualTo(expectedOrderDetail.get("smpGroup").intValue());
    assertThat(actualOrderDetail.getSmpOrderId())
        .isEqualTo(expectedOrderDetail.get("smpOrderId").textValue());
    assertThat(actualOrderDetail.getPlaceType())
        .isEqualTo(expectedOrderDetail.get("placeType").textValue());
    assertThat(actualOrderDetail.getCreatedTime().getTime())
        .isEqualTo(expectedOrderDetail.get("createdTime").asLong());
    assertThat(actualOrderDetail.getUpdatedTime().getTime())
        .isEqualTo(expectedOrderDetail.get("updatedTime").asLong());
  }

  @Test
  public void testPlaceBybitMarketOrder() throws IOException {
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
        bybitAccountServiceRaw.placeMarketOrder(
            BybitCategory.SPOT, "BTCUSDT", BybitSide.BUY, BigDecimal.valueOf(0.1));

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

  @Test
  public void testPlaceBybitLimitOrder() throws IOException {
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
        bybitAccountServiceRaw.placeLimitOrder(
            BybitCategory.SPOT,
            "BTCUSDT",
            BybitSide.BUY,
            BigDecimal.valueOf(0.1),
            BigDecimal.valueOf(1000));

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
