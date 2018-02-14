package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Object representing json returned from <code>GET /v1/me/getdeposits</code> or <code>GET /v1/me/getwithdrawals</code> 
 *  
 * <p>Example</p>
 * [
 *   {
 *     "id": 300,
 *     "order_id": "MDP20151014-101010-033333",
 *     "currency_code": "JPY",
 *     "amount": 10000,
 *     "status": "COMPLETED",
 *     "event_date": "2015-10-14T10:10:10.001"
 *   }
 * ]
 * @author bryant_harris
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerDepositOrWithdrawal extends BitflyerBaseHistoryResponse {

  @Override
  public String toString() {
    return "BitflyerCashDeposit [id=" + id + ", orderID=" + orderID + ", currencyCode=" + currencyCode + ", amount="
        + amount + ", status=" + status + ", eventDate=" + eventDate + "]";
  }     
}
