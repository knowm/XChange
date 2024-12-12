package org.knowm.xchange.bitmex.dto.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.InstrumentToStringConverter;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class FilterParam {

  @Singular
  @JsonProperty("orderID")
  private List<String> orderIds;

  @JsonProperty("symbol")
  @JsonSerialize(converter = InstrumentToStringConverter.class)
  private Instrument instrument;

  @JsonProperty("open")
  private Boolean isOpen;

  /**
   *
   * @return Value rendered as json
   */
  @Override
  public String toString() {
    return BitmexAdapters.asJsonString(this);
  }
}
