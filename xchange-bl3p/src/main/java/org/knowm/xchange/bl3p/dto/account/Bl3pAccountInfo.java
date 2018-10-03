package org.knowm.xchange.bl3p.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import org.knowm.xchange.bl3p.dto.Bl3pAmountObj;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pAccountInfo extends Bl3pResult<Bl3pAccountInfo.Bl3pAccountInfoData> {

  public static class Bl3pAccountInfoData {

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("trade_fee")
    private BigDecimal tradeFee;

    @JsonProperty("wallets")
    private Map<String, Bl3pAccountInfoWallet> wallets;

    public long getUserId() {
      return userId;
    }

    public BigDecimal getTradeFee() {
      return tradeFee;
    }

    public Map<String, Bl3pAccountInfoWallet> getWallets() {
      return wallets;
    }
  }

  public static class Bl3pAccountInfoWallet {

    @JsonProperty("available")
    private Bl3pAmountObj available;

    @JsonProperty("balance")
    private Bl3pAmountObj balance;

    public Bl3pAmountObj getAvailable() {
      return available;
    }

    public Bl3pAmountObj getBalance() {
      return balance;
    }
  }
}
