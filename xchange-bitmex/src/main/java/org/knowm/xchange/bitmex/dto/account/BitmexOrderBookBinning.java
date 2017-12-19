package org.knowm.xchange.bitmex.dto.account;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BitmexOrderBookBinning {

  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

}