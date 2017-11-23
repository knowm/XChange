package org.knowm.xchange.ripple.dto.account;

import org.knowm.xchange.ripple.dto.RippleCommon;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleAccountSettings extends RippleCommon {

  @JsonProperty("settings")
  private RippleSettings settings = new RippleSettings();

  public RippleSettings getSettings() {
    return settings;
  }

  public void setSettings(final RippleSettings value) {
    settings = value;
  }

  @Override
  public String toString() {
    return String.format("%s [settings=%s]", getClass().getSimpleName(), settings);
  }
}
