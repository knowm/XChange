package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.bitmarket.dto.BitMarketDtoTestSupport;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitMarketServiceTestSupport extends BitMarketDtoTestSupport {

  public Map<String,BigDecimal> createAvailable() {
    Map<String, BigDecimal> availableMock = new HashMap<>();
    availableMock.put("BTC", new BigDecimal("10.00000000"));
    availableMock.put("AUD", new BigDecimal("20.00000000"));
    availableMock.put("PLN", new BigDecimal("30.00000000"));

    return availableMock;
  }

  public Map<String,BigDecimal> createBlocked() {
    Map<String, BigDecimal> availableMock = new HashMap<>();
    availableMock.put("BTC", new BigDecimal("10.00000000"));
    availableMock.put("AUD", new BigDecimal("20.00000000"));
    availableMock.put("PLN", new BigDecimal("30.00000000"));

    return availableMock;
  }

  public Map<String, Map<String, List<BitMarketOrder>>> createOpenOrdersData() throws IOException {
    {
      BitMarketOrdersResponse response = parse("trade/example-orders-data", BitMarketOrdersResponse.class);
      return response.getData();
    }
  }
}
