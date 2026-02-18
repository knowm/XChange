package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexCurrencyMapping {

  private String source;

  private String target;
}
