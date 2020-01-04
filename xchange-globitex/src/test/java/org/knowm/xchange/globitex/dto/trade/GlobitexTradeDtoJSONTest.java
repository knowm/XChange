package org.knowm.xchange.globitex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Test;
import org.knowm.xchange.globitex.dto.marketdata.GlobitexMarketDataDtoJSONTest;

public class GlobitexTradeDtoJSONTest {

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void globitexUserTradesJsonTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/trade/globitex-usertrades-example.json");

    GlobitexUserTrades usertrades = mapper.readValue(is, GlobitexUserTrades.class);

    assertThat(usertrades.getUserTrades().size()).isEqualTo(2);
    assertThat(usertrades.getUserTrades().get(0).getTradeId()).isEqualTo(39);
    assertThat(usertrades.getUserTrades().get(0).getSymbol()).isEqualTo("XBTEUR");
    assertThat(usertrades.getUserTrades().get(0).getSide()).isEqualTo("sell");
    assertThat(usertrades.getUserTrades().get(0).getOriginalOrderId()).isEqualTo("114");
    assertThat(usertrades.getUserTrades().get(0).getClientOrderId()).isEqualTo("FTO18jd4ou41--25");
    assertThat(usertrades.getUserTrades().get(0).getQuantity()).isEqualTo("10");
    assertThat(usertrades.getUserTrades().get(0).getPrice()).isEqualTo("150");
    assertThat(usertrades.getUserTrades().get(0).getTimestamp()).isEqualTo(1395231854030L);
    assertThat(usertrades.getUserTrades().get(0).getFee()).isEqualTo("0.03");
    assertThat(usertrades.getUserTrades().get(0).isLiqProvided()).isEqualTo(false);
    assertThat(usertrades.getUserTrades().get(0).getFeeCurrency()).isEqualTo("EUR");
    assertThat(usertrades.getUserTrades().get(0).getAccount()).isEqualTo("ADE922A21");
  }

  @Test
  public void globitexActiveOrdersJsonTest() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/trade/globitex-activeorders-example.json");

    GlobitexActiveOrders activeOrders = mapper.readValue(is, GlobitexActiveOrders.class);

    assertThat(activeOrders.getOrders().size()).isEqualTo(3);
    assertThat(activeOrders.getOrders().get(0).getOrderId()).isEqualTo("7242835");
    assertThat(activeOrders.getOrders().get(0).getOrderStatus()).isEqualTo("new");
    assertThat(activeOrders.getOrders().get(0).getLastTimestamp()).isEqualTo(1495038022000L);
    assertThat(activeOrders.getOrders().get(0).getOrderPrice()).isEqualTo("2000.000");
    assertThat(activeOrders.getOrders().get(0).getOrderQuantity()).isEqualTo("1.00000");
    assertThat(activeOrders.getOrders().get(0).getAvgPrice()).isEqualTo("0");
    assertThat(activeOrders.getOrders().get(0).getType()).isEqualTo("limit");
    assertThat(activeOrders.getOrders().get(0).getTimeInForce()).isEqualTo("GTC");
    assertThat(activeOrders.getOrders().get(0).getClientOrderId()).isEqualTo("1495038022448");
    assertThat(activeOrders.getOrders().get(0).getSymbol()).isEqualTo("XBTEUR");
    assertThat(activeOrders.getOrders().get(0).getSide()).isEqualTo("sell");
    assertThat(activeOrders.getOrders().get(0).getAccount()).isEqualTo("ZAN034A01");
    assertThat(activeOrders.getOrders().get(0).getOrderSource()).isEqualTo("WEB");
    assertThat(activeOrders.getOrders().get(0).getLeavesQuantity()).isEqualTo("1.00000");
    assertThat(activeOrders.getOrders().get(0).getCumQuantity()).isEqualTo("0.00000");
    assertThat(activeOrders.getOrders().get(0).getExecQuantity()).isEqualTo("0.00000");
    assertThat(activeOrders.getOrders().get(1).getExpireTime()).isEqualTo(2241464400000L);
    assertThat(activeOrders.getOrders().get(2).getStopPrice()).isEqualTo("808.000");
  }

  @Test
  public void executionReportObjectTest() throws IOException {
    InputStream is =
        GlobitexMarketDataDtoJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/globitex/dto/trade/globitex-executionReport-example.json");

    GlobitexExecutionReport activeOrders = mapper.readValue(is, GlobitexExecutionReport.class);

    assertThat(activeOrders.getObject().getOrderId()).isEqualTo("58521038");
    assertThat(activeOrders.getObject().getClientOrderId()).isEqualTo("11111112");
    assertThat(activeOrders.getObject().getOrderStatus()).isEqualTo("canceled");
    assertThat(activeOrders.getObject().getSymbol()).isEqualTo("XBTEUR");
    assertThat(activeOrders.getObject().getSide()).isEqualTo("buy");
    assertThat(activeOrders.getObject().getPrice())
        .isEqualTo(new BigDecimal(0.1).setScale(1, RoundingMode.HALF_EVEN));
    assertThat(activeOrders.getObject().getQuantity()).isEqualTo(new BigDecimal(100));
    assertThat(activeOrders.getObject().getType()).isEqualTo("limit");
    assertThat(activeOrders.getObject().getTimeInForce()).isEqualTo("GTC");
    assertThat(activeOrders.getObject().getLastQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLeavesQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getCumQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getAveragePrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastQuantity()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getLastPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(activeOrders.getObject().getCreated()).isEqualTo(1497515137193L);
    assertThat(activeOrders.getObject().getLastTimestamp()).isEqualTo(1497515167420L);
    assertThat(activeOrders.getObject().getExecReportType()).isEqualTo("canceled");
    assertThat(activeOrders.getObject().getAccount()).isEqualTo("VER564A02");
    assertThat(activeOrders.getObject().getOrderSource()).isEqualTo("REST");
  }
}
