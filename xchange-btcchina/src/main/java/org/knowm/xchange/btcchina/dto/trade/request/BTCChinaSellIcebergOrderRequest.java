package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

public class BTCChinaSellIcebergOrderRequest extends BTCChinaIcebergOrderRequest {

  private static final String METHOD_NAME = "sellIcebergOrder";

  public BTCChinaSellIcebergOrderRequest(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance, String market) {

    super(METHOD_NAME, price, amount, disclosedAmount, variance, market);
  }

}
