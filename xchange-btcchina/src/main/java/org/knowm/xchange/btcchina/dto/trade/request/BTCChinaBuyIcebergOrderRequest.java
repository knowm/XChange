package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

public class BTCChinaBuyIcebergOrderRequest extends BTCChinaIcebergOrderRequest {

  private static final String METHOD_NAME = "buyIcebergOrder";

  public BTCChinaBuyIcebergOrderRequest(BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance, String market) {

    super(METHOD_NAME, price, amount, disclosedAmount, variance, market);
  }

}
