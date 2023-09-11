package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class BybitCategorizedPayload<V> {

  @JsonProperty("category")
  Category category;

  @JsonProperty("list")
  List<V> list;


  @Getter
  @AllArgsConstructor
  public enum Category {
    SPOT("spot"),
    LINEAR("linear"),
    INVERSE("inverse"),
    OPTION("option");

    @JsonValue
    private final String value;

  }

}
