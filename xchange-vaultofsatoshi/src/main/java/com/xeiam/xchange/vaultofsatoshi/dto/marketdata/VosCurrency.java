package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a Currency Object from VaultOfSatoshi
 */
public final class VosCurrency {

  private final int precision;
  private final BigDecimal value;
  private final long value_int;

  public VosCurrency(@JsonProperty("precision") int precision, @JsonProperty("value") BigDecimal value, @JsonProperty("value_int") long value_int) {

    this.precision = precision;
    this.value = value;
    this.value_int = value_int;
  }
  
  public VosCurrency(BigDecimal value) {

	    this.precision = value.scale();
	    this.value = value;
	    this.value_int = value.unscaledValue().longValue();
	  }

  public int getPrecision() {

    return precision;
  }

  public BigDecimal getValue() {

    return value;
  }

  public long getValueInt() {

    return value_int;
  }

@Override
public String toString() {
	return "VosCurrency [precision=" + precision + ", value=" + value
			+ ", value_int=" + value_int + "]";
}

  
  
}
