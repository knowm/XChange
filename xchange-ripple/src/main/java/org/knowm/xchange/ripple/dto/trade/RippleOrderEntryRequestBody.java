package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.knowm.xchange.ripple.dto.RippleAmount;

@JsonPropertyOrder({"type", "taker_pays", "taker_gets"})
public class RippleOrderEntryRequestBody {

  @JsonProperty("type")
  private String type;

  @JsonProperty("taker_pays")
  private RippleAmount takerPays = new RippleAmount();

  @JsonProperty("taker_gets")
  private RippleAmount takerGets = new RippleAmount();

  public String getType() {
    return type;
  }

  public void setType(final String value) {
    type = value;
  }

  public RippleAmount getTakerPays() {
    return takerPays;
  }

  public void setTakerPays(final RippleAmount value) {
    takerPays = value;
  }

  public RippleAmount getTakerGets() {
    return takerGets;
  }

  public void setTakerGetsTotal(final RippleAmount value) {
    takerGets = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [type=%s, taker_pays=%s, taker_gets=%s]",
        getClass().getSimpleName(), type, takerPays, takerGets);
  }
}
