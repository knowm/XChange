package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
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
    try {
      return bitflyer.getExecutions();
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerExecution> getExecutions(String productCode) throws IOException {
    try {
      return bitflyer.getExecutions(productCode);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }
}
