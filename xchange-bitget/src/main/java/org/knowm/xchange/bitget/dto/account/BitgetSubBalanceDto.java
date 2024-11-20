package org.knowm.xchange.bitget.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetSubBalanceDto {

  @JsonProperty("userId")
  private String userId;

  @JsonProperty("assetsList")
  private List<BitgetBalanceDto> balances;
}
