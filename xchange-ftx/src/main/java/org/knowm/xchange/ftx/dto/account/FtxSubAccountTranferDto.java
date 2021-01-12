package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxSubAccountTranferDto {

  private final String id;

  private final String coin;

  private final BigDecimal size;

  private final Date time;

  private final String notes;

  private final String status;

  public FtxSubAccountTranferDto(
      @JsonProperty("id") String id,
      @JsonProperty("coin") String coin,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("time") Date time,
      @JsonProperty("notes") String notes,
      @JsonProperty("status") String status) {
    this.id = id;
    this.coin = coin;
    this.size = size;
    this.time = time;
    this.notes = notes;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public String getCoin() {
    return coin;
  }

  public BigDecimal getSize() {
    return size;
  }

  public Date getTime() {
    return time;
  }

  public String getNotes() {
    return notes;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "FtxSubAccountTranferDto{"
        + "id='"
        + id
        + '\''
        + ", coin='"
        + coin
        + '\''
        + ", size="
        + size
        + ", time="
        + time
        + ", notes='"
        + notes
        + '\''
        + ", status='"
        + status
        + '\''
        + '}';
  }
}
