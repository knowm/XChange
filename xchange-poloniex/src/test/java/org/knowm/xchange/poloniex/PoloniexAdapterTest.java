package org.knowm.xchange.poloniex;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.poloniex.dto.trade.PoloniexUserTrade;

public class PoloniexAdapterTest {

  @Test
  public void testTradeHistory() throws IOException {

    final InputStream is =
        PoloniexUserTrade.class.getResourceAsStream(
            "/org/knowm/xchange/poloniex/dto/trade/adapter-trade-history.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tradeArray = mapper.getTypeFactory().constructArrayType(PoloniexUserTrade.class);

    PoloniexUserTrade[] tradeHistory = mapper.readValue(is, tradeArray);

    LimitOrder result = PoloniexAdapters.adaptUserTradesToOrderStatus("102", tradeHistory);

    Assert.assertEquals(new BigDecimal("0.0102693100000000"), result.getAveragePrice());
    Assert.assertEquals(new BigDecimal("0.03000000"), result.getCumulativeAmount());
    Assert.assertEquals(null, result.getOriginalAmount());
    Assert.assertEquals("102", result.getId());
  }
}
