package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GateioFuturesAccountBookRecord {

  /**
   * The time of the change.
   */
  @JsonProperty("time")
  private Double time;

  /**
   * The amount of the change.
   */
  @JsonProperty("change")
  private String change;

  /**
   * The account balance after the change.
   */
  @JsonProperty("balance")
  private String balance;

  /**
   * The type of change.
   * <p>Possible values:
   * <ul>
   *     <li>dnw: Deposit and withdrawal</li>
   *     <li>pnl: Position profit and loss</li>
   *     <li>fee: Trading fee</li>
   *     <li>refr: Referrer rebate</li>
   *     <li>fund: Funding fee</li>
   *     <li>point_dnw: Point card deposit and withdrawal</li>
   *     <li>point_fee: Point card trading fee</li>
   *     <li>point_refr: Point card referrer rebate</li>
   *     <li>bonus_offset: Bonus deduction</li>
   * </ul>
   * </p>
   */
  @JsonProperty("type")
  private String type;

  /**
   * Additional remarks or comments.
   */
  @JsonProperty("text")
  private String text;

  /**
   * The contract identifier. Available only for data after 2023-10-30.
   */
  @JsonProperty("contract")
  private String contract;

  /**
   * The trade ID associated with this change.
   */
  @JsonProperty("trade_id")
  private String tradeId;

  /**
   * The ID of the account change record.
   */
  @JsonProperty("id")
  private String id;


}
