package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class BybitCategorizedPayload<T> {

  @JsonProperty("category")
  BybitCategory category;

  @JsonProperty("list")
  List<T> list;
}
