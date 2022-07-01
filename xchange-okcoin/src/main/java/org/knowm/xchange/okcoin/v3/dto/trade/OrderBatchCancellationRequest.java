package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderBatchCancellationRequest {

  /**
   * required by providing this parameter, the corresponding order of a designated trading pair will
   * be cancelled. If not providing this parameter, it will be back to error code.
   */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** order ID. You may cancel up to 4 orders of a trading pair */
  @JsonProperty("order_ids")
  private List<String> orderIds;

  /**
   * The client_oid type should be comprised of alphabets + numbers or only alphabets within 1 – 32
   * characters， both uppercase and lowercase letters are supported
   */
  @JsonProperty("client_oids")
  private List<String> clientOids;
}
