package info.bitrich.xchangestream.gateio.dto.response.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class GateioFuturesBalance {

  /**
   * The final balance amount after the change.
   */
  @JsonProperty("balance")
  private Number balance;

  /**
   * The amount by which the balance changed.
   */
  @JsonProperty("change")
  private Number change;

  /**
   * Additional information or comments related to the change.
   */
  @JsonProperty("text")
  private String text;

  /**
   * The timestamp of the change (in seconds).
   */
  @JsonProperty("time")
  private Integer time;

  /**
   * The timestamp of the change (in milliseconds).
   */
  @JsonProperty("time_ms")
  private Integer timeMs;

  /**
   * The type of balance change. Possible values include:
   * <ul>
   *   <li><b>dnw:</b> Deposit or withdrawal</li>
   *   <li><b>pnl:</b> Profit and loss from position reduction</li>
   *   <li><b>fee:</b> Trading fee</li>
   *   <li><b>refr:</b> Referral commission</li>
   *   <li><b>fund:</b> Funding fee</li>
   *   <li><b>point_dnw:</b> Point card deposit or withdrawal</li>
   *   <li><b>point_fee:</b> Point card trading fee</li>
   *   <li><b>point_refr:</b> Point card referral commission</li>
   *   <li><b>bonus_offset:</b> Bonus deduction</li>
   * </ul>
   */
  @JsonProperty("type")
  private String type;

  /**
   * The user ID associated with this balance change.
   */
  @JsonProperty("user")
  private String user;

  /**
   * The currency related to the balance change.
   */
  @JsonProperty("currency")
  private String currency;
}
