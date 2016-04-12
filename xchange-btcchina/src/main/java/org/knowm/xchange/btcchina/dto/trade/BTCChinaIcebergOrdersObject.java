package org.knowm.xchange.btcchina.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaIcebergOrdersObject {

  private final BTCChinaIcebergOrder[] icebergOrders;

  public BTCChinaIcebergOrdersObject(@JsonProperty("iceberg_orders") BTCChinaIcebergOrder[] icebergOrders) {

    this.icebergOrders = icebergOrders;
  }

  public BTCChinaIcebergOrder[] getIcebergOrders() {

    return icebergOrders;
  }

}
