package info.bitrich.xchangestream.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.trade.BybitUserTradeDto;

@ToString
@Getter
@AllArgsConstructor
public class BybitUserTradeResponseDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("topic")
  private String topic;

  @JsonProperty("creationTime")
  private Date creationTime;

  @JsonProperty("data")
  private List<BybitUserTradeDto> data;

  /* No args constructor for use in serialization */
  public BybitUserTradeResponseDto() {
  }
}
