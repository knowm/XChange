package org.knowm.xchange.abucoins.dto.account;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>POST /withdrawals/crypto</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * {
 *     "status": 0,
 *     "message": "Your transaction is pending. Please confirm it via email.",
 *     "payoutId": "65",
 *     "balance": [
 *         {
 *             "type": "PLN",
 *             "balance": "2999990.00000000",
 *             "locked": "0.00000000"
 *         },
 *         {
 *             "type": "BTC",
 *             "balance": "13.38589212",
 *             "locked": "0.00014593"
 *         }
 *     ]
 * }
 * </pre></code>
 */
public class AbucoinsCryptoWithdrawal {
  long status;
  String message;
  String payoutId;
  Balance[] balance;
        
  public AbucoinsCryptoWithdrawal(@JsonProperty("status") long status,
                                  @JsonProperty("message") String message,
                                  @JsonProperty("payoutId") String payoutId,
                                  @JsonProperty("balance")  Balance[] balance) {
    this.status = status;
    this.message = message;
    this.payoutId = payoutId;
    this.balance = balance;
  }

  public long getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getPayoutId() {
    return payoutId;
  }

  public Balance[] getBalance() {
    return balance;
  }
        
  @Override
  public String toString() {
    return "AbucoinsCryptoWithdrawal [status=" + status + ", message=" + message + ", payoutId=" + payoutId
        + ", balance=" + Arrays.toString(balance) + "]";
  }

  static class Balance {
    String type;
    BigDecimal balance;
    BigDecimal locked;
                
    public Balance(@JsonProperty("type") String type,
                   @JsonProperty("balance") BigDecimal balance,
                   @JsonProperty("locked") BigDecimal locked) {
      this.type = type;
      this.balance = balance;
      this.locked = locked;
    }

    public String getType() {
      return type;
    }

    public BigDecimal getBalance() {
      return balance;
    }

    public BigDecimal getLocked() {
      return locked;
    }
      
    @Override
    public String toString() {
      return "Balance [type=" + type + ", balance=" + balance + ", locked=" + locked + "]";
    }
  }
}
