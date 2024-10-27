package org.knowm.xchange.bitget.dto.account.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;

@Data
@SuperBuilder
public class BitgetMainSubTransferHistoryParams {

  private Currency currency;

  private Role role;

  private String subAccountUid;

  private Instant startTime;

  private Instant endTime;

  private String clientOid;

  private Integer limit;

  private String endId;

  @Getter
  @AllArgsConstructor
  public static enum Role {
    INITIATOR("initiator"),

    @JsonProperty
    RECEIVER("receiver");

    @JsonValue private final String value;
  }
}
