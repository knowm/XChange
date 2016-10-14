package org.knowm.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaIcebergOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetIcebergOrdersResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws IOException {

    BTCChinaGetIcebergOrdersResponse response = mapper.readValue(getClass().getResource("getIcebergOrders.json"),
        BTCChinaGetIcebergOrdersResponse.class);
    BTCChinaIcebergOrder[] io = response.getResult().getIcebergOrders();
    assertEquals(2, io.length);
    assertEquals(2, io[0].getId());
    assertEquals("ask", io[0].getType());
  }

}
