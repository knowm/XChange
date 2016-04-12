package org.knowm.xchange.btcchina.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaIcebergOrderObject {

  private final BTCChinaIcebergOrder icebergOrder;

  public BTCChinaIcebergOrderObject(@JsonProperty("iceberg_order") BTCChinaIcebergOrder icebergOrder) {

    this.icebergOrder = icebergOrder;
  }

  public BTCChinaIcebergOrder getIcebergOrder() {

    return icebergOrder;
  }
}