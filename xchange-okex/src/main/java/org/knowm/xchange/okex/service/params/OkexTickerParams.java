/** */
package org.knowm.xchange.okex.service.params;

import org.knowm.xchange.service.marketdata.params.Params;

/** @author leeyazhou */
public class OkexTickerParams implements Params {
  private String instType;
  private String uly;
  private String instFamily;

  public String getInstType() {
    return instType;
  }

  public void setInstType(String instType) {
    this.instType = instType;
  }

  public String getUly() {
    return uly;
  }

  public void setUly(String uly) {
    this.uly = uly;
  }

  public String getInstFamily() {
    return instFamily;
  }

  public void setInstFamily(String instFamily) {
    this.instFamily = instFamily;
  }
}
