package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerExecution;

public class BitflyerTradeServiceRaw extends BitflyerBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BitflyerExecution> getExecutions() throws IOException {
    return bitflyer.getExecutions();
  }

  public List<BitflyerExecution> getExecutions(String productCode) throws IOException {
    return bitflyer.getExecutions(productCode);
  }
}
