package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RippleAccountOrdersBody extends RippleOrderEntryRequestBody {
  @JsonProperty("sequence")
  private long sequence;

  @JsonProperty("passive")
  private boolean passive;

  public long getSequence() {
    return sequence;
  }

  public void setSequence(final long value) {
    sequence = value;
  }

  public boolean getPassive() {
    return passive;
  }

  public void setPassive(final boolean value) {
    passive = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [type=%s, taker_pays=%s, taker_gets=%s, sequence=%d, passive=%b]",
        getClass().getSimpleName(), getType(), getTakerPays(), getTakerGets(), sequence, passive);
  }
}
