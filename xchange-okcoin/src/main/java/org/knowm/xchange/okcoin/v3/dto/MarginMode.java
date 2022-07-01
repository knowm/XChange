package org.knowm.xchange.okcoin.v3.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MarginMode {
  crossed,
  fixed,
  empty // special case, for some wallets okex returns empty margin mode
;

  @JsonCreator
  public static MarginMode create(String s) {
    if (s == null) {
      return null;
    }
    if (s.isEmpty()) {
      return empty;
    }
    try {
      return MarginMode.valueOf(s);
    } catch (Exception e) {
      log.warn("Invalid string for MarginMode '{}'.", s, e);
      return null;
    }
  }
}
