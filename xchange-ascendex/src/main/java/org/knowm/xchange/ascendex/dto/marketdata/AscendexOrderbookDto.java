package org.knowm.xchange.ascendex.dto.marketdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexOrderbookDto {

  private String m;

  private String symbol;

  private AscendexOrderbookData data;


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AscendexOrderbookData {
    private Long seqnum;

    private Date ts;

    private List<AscendexPublicOrder> asks;

    private List<AscendexPublicOrder> bids;

    public Date getTimestamp() {
      return ts;
    }
  }
}
