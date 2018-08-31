package org.knowm.xchange.bitmex.dto.trade;

@SuppressWarnings("unused")
public enum BitmexContingencyType {
  OCO("OneCancelsTheOther"),
  OTO("OneTriggersTheOther"),
  OUOA("OneUpdatesTheOtherAbsolute"),
  OUOP("OneUpdatesTheOtherProportional");

  private String apiParameter;

  BitmexContingencyType(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  public String toApiParameter() {
    return apiParameter;
  }
}
