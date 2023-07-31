package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexBarHistDto {
  @JsonProperty("m")
  private  String m;
  @JsonProperty("s")
  private  String symbol;
  @JsonProperty("data")
  private  AscendexBarDto bar;

}
