package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/** @author Andrea Fossi. */
public enum CexioPositionType {
  LONG,
  SHORT;

  @JsonCreator
  public static CexioPositionType forValue(String value) {
    if (StringUtils.equalsIgnoreCase("long", value)) return LONG;
    else if (StringUtils.equalsIgnoreCase("short", value)) return SHORT;
    else return null;
  }

  @JsonValue
  public String toValue() {
    return this.toString();
  }

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
