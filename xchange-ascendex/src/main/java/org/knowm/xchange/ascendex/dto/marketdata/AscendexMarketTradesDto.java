package org.knowm.xchange.ascendex.dto.marketdata;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexMarketTradesDto{

  @JsonProperty("m")
  private String m;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("data")
  private List<AscendexMarketTradesData> data;


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AscendexMarketTradesData {
    @JsonProperty("seqnum")
    private String seqnum;

    @JsonProperty("p")
    private BigDecimal price;

    @JsonProperty("q")
    private BigDecimal quantity;

    @JsonProperty("ts")
    private Date timestamp;

    @JsonProperty("bm")
    private boolean isBuyerMaker;
  }
}
