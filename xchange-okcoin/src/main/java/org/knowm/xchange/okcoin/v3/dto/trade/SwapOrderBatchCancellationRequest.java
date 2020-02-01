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
public class SwapOrderBatchCancellationRequest {

  /** Order ID list */
  private List<String> ids;

  /**
   * The client_oid type should be comprised of alphabets + numbers or only alphabets within 1 – 32
   * characters， both uppercase and lowercase letters are supported
   */
  @JsonProperty("client_oids")
  private List<String> clientOids;
}
