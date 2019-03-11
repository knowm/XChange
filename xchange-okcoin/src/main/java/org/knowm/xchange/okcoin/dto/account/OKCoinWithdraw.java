package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

/**
 * Withdraw result info
 *
 * @author Ondřej Novotný
 */
public class OKCoinWithdraw extends OkCoinErrorResult {
  private final String withdrawId;

  /*
   * withdraw_id: withdrawal request ID result: true means request successful
   */
  public OKCoinWithdraw(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("withdraw_id") final String withdrawId) {
    super(result, errorCode);
    this.withdrawId = withdrawId;
  }

  public String getWithdrawId() {
    return withdrawId;
  }

  @Override
  public String toString() {
    return "Withdraw [refid=" + withdrawId + "]";
  }
}
