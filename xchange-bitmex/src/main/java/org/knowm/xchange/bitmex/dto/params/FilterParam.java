package org.knowm.xchange.bitmex.dto.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.BitmexAdapters;

@Data
@Builder
@Jacksonized
public class FilterParam {

  @Singular
  @JsonProperty("orderID")
  private List<String> orderIds;

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
