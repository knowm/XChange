package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;

@Data
@EqualsAndHashCode(callSuper = true)
public class BybitKlines extends BybitCategorizedPayload<BybitKline> {

  @JsonProperty("symbol")
  String symbol;
}
