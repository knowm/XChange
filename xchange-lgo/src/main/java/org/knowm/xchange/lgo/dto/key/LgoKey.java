package org.knowm.xchange.lgo.dto.key;

import java.time.Instant;

public final class LgoKey {

  private final String id;
  private final Instant issuedAt;
  private final Instant disabledAt;
  private String value;

  public LgoKey(String id, Instant issuedAt, Instant disabledAt) {
    this.id = id;
    this.issuedAt = issuedAt;
    this.disabledAt = disabledAt;
  }

  public String getId() {
    return id;
  }

  public Instant getEnabledAt() {
    return issuedAt;
  }

  public Instant getDisabledAt() {
    return disabledAt;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
