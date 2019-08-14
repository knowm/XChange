package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Getter
@Setter
@ToString
public class EnigmaWithdrawal extends BaseResponse {

  @JsonProperty("withdrawal_type")
  private String withdrawalType;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("sent_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date sentAt;

  @JsonProperty("withdrawal_key")
  private String withdrawalKey;
}
