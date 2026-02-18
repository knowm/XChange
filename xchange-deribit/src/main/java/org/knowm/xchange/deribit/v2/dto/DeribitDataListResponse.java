package org.knowm.xchange.deribit.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class DeribitDataListResponse<T> {

  @JsonProperty("count")
  private Long count;

  @JsonProperty("data")
  List<T> data;
}
