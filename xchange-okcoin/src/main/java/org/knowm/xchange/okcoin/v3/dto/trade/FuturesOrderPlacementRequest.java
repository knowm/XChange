package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class FuturesOrderPlacementRequest {
  /**
   * optional the order ID customized by yourself , The client_oid type should be comprised of
   * alphabets + numbers or only alphabets within 1 – 32 characters， both uppercase and lowercase
   * letters are supported
   */
  @JsonProperty("client_oid")
  private String clientOid;

  /** required, Contract ID,e.g. “TC-USD-180213” */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** required, 1:open long 2:open short 3:close long 4:close short */
  private FuturesSwapType type;

  /** required, Price of each contract */
  private BigDecimal price;

  /** required, The buying or selling quantityd */
  private BigDecimal size;

  /**
   * optional, Order at best counter party price? (0:no 1:yes) the parameter is defaulted as 0. If
   * it is set as 1, the price parameter will be ignored，When posting orders at best bid price,
   * order_type can only be 0 (regular order)
   */
  @JsonProperty("match_price")
  private FuturesSwapMatchPrice matchPrice;

  /** required, Leverage , 1-100x */
  private BigDecimal leverage;

  /**
   * optional, Fill in String for parameter，0: Normal limit order (Unfilled and 0 represent normal
   * limit order) 1: Post only 2: Fill Or Kill 3: Immediatel Or Cancel
   */
  @JsonProperty("order_type")
  private OrderPlacementType orderType;
}
