package info.bitrich.xchangestream.coincheck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoincheckSubscribeMessage {
  @JsonProperty private String type;
  @JsonProperty private String channel;
}
