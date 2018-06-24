package org.knowm.xchange.bitbay.v3.dto.trade;

import java.util.List;

public class BitbayBalancesHistoryQuery {

  private String limit;
  private String offset;
  private List<String> types;

  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }
}
