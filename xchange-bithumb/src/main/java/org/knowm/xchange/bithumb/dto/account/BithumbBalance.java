package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class BithumbBalance {

  private static final String PREFIX_TOTAL = "total_";
  private static final String PREFIX_FROZEN = "in_use_";
  private static final String PREFIX_AVAILABLE = "available_";

  private final BigDecimal totalKrw;
  private final BigDecimal inUseKrw;
  private final BigDecimal availableKrw;
  private final Map<String, String> additionalProperties = new HashMap<>();

  public BithumbBalance(
      @JsonProperty("total_krw") BigDecimal totalKrw,
      @JsonProperty("in_use_krw") BigDecimal inUseKrw,
      @JsonProperty("available_krw") BigDecimal availableKrw) {
    this.totalKrw = totalKrw;
    this.inUseKrw = inUseKrw;
    this.availableKrw = availableKrw;
  }

  public BigDecimal getTotalKrw() {
    return totalKrw;
  }

  public BigDecimal getInUseKrw() {
    return inUseKrw;
  }

  public BigDecimal getAvailableKrw() {
    return availableKrw;
  }

  public Map<String, String> getAdditionalProperties() {
    return additionalProperties;
  }

  public Set<String> getCurrencies() {
    return additionalProperties.keySet().stream()
        .filter(key -> key.startsWith(PREFIX_TOTAL))
        .map(key -> StringUtils.remove(key, PREFIX_TOTAL))
        .collect(Collectors.toSet());
  }

  public BigDecimal getAvailable(String currency) {
    return Optional.ofNullable(additionalProperties.get(PREFIX_AVAILABLE + currency))
        .map(BigDecimal::new)
        .orElse(BigDecimal.ZERO);
  }

  public BigDecimal getFrozen(String currency) {
    return Optional.ofNullable(additionalProperties.get(PREFIX_FROZEN + currency))
        .map(BigDecimal::new)
        .orElse(BigDecimal.ZERO);
  }

  public BigDecimal getTotal(String currency) {
    return Optional.ofNullable(additionalProperties.get(PREFIX_TOTAL + currency))
        .map(BigDecimal::new)
        .orElse(BigDecimal.ZERO);
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, String value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "BithumbBalance{"
        + "totalKrw="
        + totalKrw
        + ", inUseKrw="
        + inUseKrw
        + ", availableKrw="
        + availableKrw
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
